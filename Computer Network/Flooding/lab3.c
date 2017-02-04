#include <cnet.h>
#include <stdlib.h>
#include <limits.h>
#include <string.h>
#include "nl_table.h"

#include "dll_basic.h"

#define	MAXHOPS		1

/*  This file implements a better flooding algorithm exhibiting slightly
    more "intelligence" than the naive algorithm in flooding1.c
    These additions, implemented using flood2(), include:

    1) data packets are initially sent on all links.
    2) packets are forwarded on all links except the one on which they arrived.
    3) acknowledgement packets are initially sent on the link on which their
       data packet arrived.

    This algorithm exhibits better efficiency than flooding1.c .  Over the
    8 nodes in the AUSTRALIA.MAP file, the efficiency is typically about 8%.
 */

typedef enum    	{ NL_DATA, NL_ACK }   NL_PACKETKIND;

typedef struct {
    CnetAddr		src;
    CnetAddr		dest;
    NL_PACKETKIND	kind;      	/* only ever NL_DATA or NL_ACK */
    int			seqno;		/* 0, 1, 2, ... */
    int			hopcount;
    size_t		length;       	/* the length of the msg portion only */
    char		msg[MAX_MESSAGE_SIZE];
} NL_PACKET;

typedef struct {
    CnetAddr		dest;		/* Destination Address */
    NL_PACKET		pkt;            /* Packet	*/
    CnetTimerID		timeID;		/* TimerID      */
} NLTABLE1;



static  CnetTimerID	lasttimer		= NULLTIMER;
static	NLTABLE1	*NL_table1	= NULL;
static  int	NL_table_size_1	= 0;


#define PACKET_HEADER_SIZE  (sizeof(NL_PACKET) - MAX_MESSAGE_SIZE)
#define PACKET_SIZE(p)	    (PACKET_HEADER_SIZE + p.length)


/* ----------------------------------------------------------------------- */

/* UPDATING THE TABLE */
static void updateTable(NL_PACKET packet, CnetAddr dest, CnetTimerID timerID){

     bool new = true;
     for(int i=0 ; i<NL_table_size_1 ; ++i){
	if (NL_table1[i].dest == dest){
		memcpy ( &NL_table1[i].pkt, &packet, sizeof(NL_PACKET) );
		NL_table1[i].timeID	= timerID;
		new = false;
		break;
	 

	}//end if
     }//end for loop
     if (new == true){
	NL_table1	= realloc(NL_table1, (NL_table_size_1+1)*sizeof(NLTABLE1));
	memset(&NL_table1[NL_table_size_1], 0, sizeof(NLTABLE1));
	NL_table1[NL_table_size_1].dest	= dest;
	memcpy ( &NL_table1[NL_table_size_1].pkt, &packet, sizeof(NL_PACKET) );
	
	NL_table1[NL_table_size_1].timeID	= timerID;
	NL_table_size_1++;
	}//end if 

}//end update table

static int getPkt (CnetTimerID timerID){

    for(int i=0 ; i<NL_table_size_1 ; i++){
	if(NL_table1[i].timeID == timerID){
	    return i;
		}//end if
	}//end for loop
  	return -1;
}//end getPkt

static void stopTimerID (CnetAddr dest){
	for(int i=0 ; i<NL_table_size_1 ; i++){
		if(NL_table1[i].dest == dest){
			CNET_stop_timer(NL_table1[i].timeID);
		}//end if 
	}//end for
}//end stopTimerID

/*
Save to table
Find via destination
Find via TimerID
*/
/* ----------------------------------------------------------------------- */


/* ----------------------------------------------------------------------- */

/*  flood2() IS A BASIC ROUTING STRATEGY WHICH TRANSMITS THE OUTGOING PACKET
    ON EVERY LINK SPECIFIED IN THE BITMAP NAMED links_wanted.
 */
static void flood2(char *packet, size_t length, int links_wanted)
{
    int	   link;

    for(link=1 ; link<=nodeinfo.nlinks ; ++link)
	if( links_wanted & (1<<link) )
	    CHECK(down_to_datalink(link, packet, length));
}

/*  down_to_network() RECEIVES NEW MESSAGES FROM THE APPLICATION LAYER AND
    PREPARES THEM FOR TRANSMISSION TO OTHER NODES.
 */
static EVENT_HANDLER(down_to_network)
{
    NL_PACKET	p;

    p.length	= sizeof(p.msg);
    CHECK(CNET_read_application(&p.dest, p.msg, &p.length));
    CHECK(CNET_disable_application(p.dest));

    p.src	= nodeinfo.address;
    p.kind	= NL_DATA;
    p.hopcount	= 0;
    p.seqno	= NL_nextpackettosend(p.dest);
  
    CnetTime timeout = sizeof(p)*((CnetTime)8000000 / linkinfo[1].bandwidth) +
				linkinfo[1].propagationdelay;

    lasttimer = CNET_start_timer(EV_TIMER1, 3 * timeout, 0);
    updateTable(p, p.dest, lasttimer); 
    flood2((char *)&p, PACKET_SIZE(p), ALL_LINKS);
}

/*  up_to_network() IS CALLED FROM THE DATA LINK LAYER (BELOW) TO ACCEPT
    A PACKET FOR THIS NODE, OR TO RE-ROUTE IT TO THE INTENDED DESTINATION.
 */
int up_to_network(char *packet, size_t length, int arrived_on)
{
    NL_PACKET	*p = (NL_PACKET *)packet;

    ++p->hopcount;			/* took 1 hop to get here */
/*  IS THIS PACKET IS FOR ME? */
    if(p->dest == nodeinfo.address) {
	switch (p->kind) {
	case NL_DATA:
	    if(p->seqno == NL_packetexpected(p->src)) {
		CnetAddr	tmpaddr;

		length		= p->length;
		CHECK(CNET_write_application(p->msg, &length));
		inc_NL_packetexpected(p->src);

		tmpaddr		= p->src; /* swap src and dest addresses */
		p->src		= p->dest;
		p->dest		= tmpaddr;

		p->kind		= NL_ACK;
		p->hopcount	= 0;
		p->length	= 0;
		/* send the NL_ACK via the link on which the NL_DATA arrived */
		flood2(packet, PACKET_HEADER_SIZE, (1<<arrived_on) );
	    }
	   if(p->seqno == NL_packetexpected(p->src)-1) {
		CnetAddr	tmpaddr;
		length		= p->length;
		tmpaddr		= p->src; /* swap src and dest addresses */
		p->src		= p->dest;
		p->dest		= tmpaddr;

		p->kind		= NL_ACK;
		p->hopcount	= 0;
		p->length	= 0;
		/* send the NL_ACK via the link on which the NL_DATA arrived */
		flood2(packet, PACKET_HEADER_SIZE, (1<<arrived_on) );
	    }
	    break;
	case NL_ACK:
	    if(p->seqno == NL_ackexpected(p->src)) {
		inc_NL_ackexpected(p->src);
		CHECK(CNET_enable_application(p->src));
		stopTimerID (p->src);
	    }
	    break;
	}
    }
/* THIS PACKET IS FOR SOMEONE ELSE */
    else {
	if(p->hopcount < MAXHOPS) 		/* if not too many hops... */
	    /* retransmit on all links *except* the one on which it arrived */
	    flood2(packet, length, ALL_LINKS & ~(1<<arrived_on) );
	else
	    /* silently drop */;
    }
    return(0);
}

static EVENT_HANDLER(timeouts)
{
    int i = getPkt (timer);
    stopTimerID (NL_table1[i].dest);
	CnetTime timeout = sizeof(NL_table1[i].pkt)*((CnetTime)8000000 / linkinfo[1].bandwidth) +
				linkinfo[1].propagationdelay;

    NL_PACKET p = NL_table1[i].pkt;
    lasttimer = CNET_start_timer(EV_TIMER1, 3 * timeout, 0);
    flood2((char *)&p, PACKET_SIZE(p), ALL_LINKS);
    updateTable(p, p.dest, lasttimer); 

}

static EVENT_HANDLER(TIMERS_INFO){

    CNET_clear();
    printf("\n The node name is %s\n", nodeinfo.nodename);		
    printf("\n The node address is %d\n", nodeinfo.address);
    printf("\n Content of count_toobusy %d\n", count_toobusy);
    printf("\n%12s %12s %12s %12s %12s %12s",
	    "source", "destination", "kind", "sequence #", "length", "TimerID");
        printf("\n");
    NL_PACKET p;

    for(int t=0 ; t<NL_table_size_1 ; t++){
	    p = NL_table1[t].pkt;
	    printf("%12d %12d %12d %12d %12d %12d\n",
		    p.src , p.dest,
		    p.kind, p.seqno, p.length, NL_table1[t].timeID);
	}

}

static EVENT_HANDLER(periodic){

    CNET_clear();
    debug();
    printf("\n The node name is %s\n", nodeinfo.nodename);		
    printf("\n The node address is %d\n", nodeinfo.address);
    printf("\n Content of count_toobusy %d\n", count_toobusy);
    printf("\n%12s %12s %12s %12s %12s %12s",
	    "source", "destination", "kind", "sequence #", "length", "TimerID");
        printf("\n");
    NL_PACKET p;

    for(int t=0 ; t<NL_table_size_1 ; t++){
	    p = NL_table1[t].pkt;
	    printf("%12d %12d %12d %12d %12d %12d\n",
		    p.src , p.dest,
		    p.kind, p.seqno, p.length, NL_table1[t].timeID);
	}
}

EVENT_HANDLER(reboot_node)
{
    if(nodeinfo.nlinks >= 32) {
	fprintf(stderr,"flood2 flooding will not work here\n");
	exit(1);
    }
    NL_table1		= calloc(1, sizeof(NLTABLE1));
    NL_table_size_1	= 0;


    reboot_DLL();
    reboot_NL_table();
 
    CHECK(CNET_set_handler(EV_PERIODIC,periodic, 0));
    CHECK(CNET_set_handler(EV_DEBUG1, TIMERS_INFO, 0));
    CHECK(CNET_set_debug_string(EV_DEBUG1, "TIMERS info"));
    CHECK(CNET_set_handler( EV_TIMER1,           timeouts, 0));
    CHECK(CNET_set_handler(EV_APPLICATIONREADY, down_to_network, 0));
    CNET_enable_application(ALLNODES);
}




