#include <cnet.h>
#include <stdlib.h>
#include <string.h>

/*  This is an implementation of a stop-and-wait data link protocol.
    It is based on Tanenbaum's `protocol 4', 2nd edition, p227
    (or his 3rd edition, p205).
    This protocol employs only data and acknowledgement frames -
    piggybacking and negative acknowledgements are not used.

    It is currently written so that only one node (number 0) will
    generate and transmit messages and the other (number 1) will receive
    them. This restriction seems to best demonstrate the protocol to
    those unfamiliar with it.
    The restriction can easily be removed by "commenting out" the line

	    if(nodeinfo.nodenumber == 0)

    in reboot_node(). Both nodes will then transmit and receive (why?).

    Note that this file only provides a reliable data-link layer for a
    network of 2 nodes.
 */

typedef enum    { DL_DATA, DL_ACK }   FRAMEKIND;

typedef struct {
    char        data[MAX_MESSAGE_SIZE];
} MSG;

typedef struct {
    FRAMEKIND    kind;      	// only ever DL_DATA or DL_ACK
    size_t	 len;       	// the length of the msg field only
    int          checksum;  	// checksum of the whole frame
    int          seq;       	// only ever 0 or 1
    MSG          msg;
} FRAME;

#define FRAME_HEADER_SIZE  (sizeof(FRAME) - sizeof(MSG))
#define FRAME_SIZE(f)      (FRAME_HEADER_SIZE + f.len)

//===New Variables===//
static  MSG             *buffermsg;	
static  size_t          lastBufferLength        = 0;       
//===================// 
static  MSG       	*lastmsg;
static  size_t		lastlength		= 0;
static  CnetTimerID	lasttimer		= NULLTIMER;
static  int             BufferCount             = 0;
static  int       	ackexpected		= 0;
static	int		nextframetosend		= 0;
static	int		frameexpected		= 0;


static void transmit_frame(MSG *msg, FRAMEKIND kind, size_t length, int seqno)
{
    FRAME       f;
    int link = 1;	
    f.kind      = kind;
    f.seq       = seqno;
    f.checksum  = 0;
    f.len       = length;

    switch (kind) {

    case DL_ACK :{
        printf("ACK transmitted, seq=%d\n", seqno);
	length      = FRAME_SIZE(f);
        f.checksum  = CNET_ccitt((unsigned char *)&f, (int)length);
	CNET_write_physical(link, &f, &length);
	break;}

    case DL_DATA: {
	CnetTime	timeout;
	//int outLink= (link == 2)? nodeinfo.nlinks : 2;
        printf(" DATA transmitted, seq=%d\n", seqno);
	printf(" DATA = LENGTH =, len=%d\n", f.len);
        memcpy(&f.msg, msg, (int)length);

	timeout = 2*FRAME_SIZE(f)*((CnetTime)8000000 / linkinfo[link].bandwidth) +
				linkinfo[link].propagationdelay;

        lasttimer = CNET_start_timer(EV_TIMER1, 3 * timeout, 0);
	length      = FRAME_SIZE(f);
        f.checksum  = CNET_ccitt((unsigned char *)&f, (int)length);
        CHECK(CNET_write_physical(link, &f, &length));
	CNET_write_physical(link, &f, &length);
	break;
      }
    }
}

static void transmit_frame2(MSG *msg, FRAMEKIND kind, size_t length, int seqno)
{
    FRAME       f;
    int link = 2;	
    f.kind      = kind;
    f.seq       = seqno;
    f.checksum  = 0;
    f.len       = length;


	CnetTime	timeout;
	//int outLink= (link == 2)? nodeinfo.nlinks : 2;
        printf(" DATA transmitted, seq=%d\n", seqno);
	printf("\t\t\t\t DATA=%d\n", f);	
        memcpy(&f.msg, msg, (int)length);

	timeout = 2*FRAME_SIZE(f)*((CnetTime)8000000 / linkinfo[link].bandwidth) +
				linkinfo[link].propagationdelay;

        lasttimer = CNET_start_timer(EV_TIMER1, 3 * timeout, 0);
	length      = FRAME_SIZE(f);
        f.checksum  = CNET_ccitt((unsigned char *)&f, (int)length);
        CHECK(CNET_write_physical(link, &f, &length));
	CNET_write_physical(link, &f, &length);
	
   
}


static EVENT_HANDLER(application_ready)
{
    CnetAddr destaddr;

    lastlength  = sizeof(MSG);
    CHECK(CNET_read_application(&destaddr, lastmsg, &lastlength));
    CNET_disable_application(ALLNODES);

    printf("down from application, seq=%d\n", nextframetosend);
    transmit_frame(lastmsg, DL_DATA, lastlength, nextframetosend);
    nextframetosend = 1-nextframetosend;
}

static EVENT_HANDLER(physical_ready)
{
    FRAME        f;
    size_t	 len;
    int          link, checksum;

    len         = sizeof(FRAME);
    CHECK(CNET_read_physical(&link, &f, &len));
//========Router Code======//
   if(nodeinfo.nodetype == NT_ROUTER){
    switch (f.kind) {
    case DL_ACK :
        if(f.seq == ackexpected) {
            printf("\t\t\t\tACK received, seq=%d\n", f.seq);
            CNET_stop_timer(lasttimer);
            ackexpected = 1-ackexpected;
            CNET_enable_application(ALLNODES);
	    BufferCount = 0;		
        }
	break;

    case DL_DATA :
        if(f.seq == frameexpected && BufferCount == 0) {
		buffermsg = &f.msg;
	   	printf("\t\t\t\t DATA IS HERE received, seq=%d\n", f.seq);
		printf("\t\t\t\t STUFF, stuff=%d\n", f.msg);
		printf("\t\t\t\t LENGTH=%d\n", f.len);	
		lastBufferLength = f.len; 
		frameexpected = 1-frameexpected;
		BufferCount = 1;
		transmit_frame2(buffermsg, DL_DATA, lastBufferLength, f.seq); 
		transmit_frame(NULL, DL_ACK, 0, f.seq); 
	} else {
            	printf("ignored\n");
	
	}//end if 
	
    }
	return;
    }//end Router if
//==============//
    checksum    = f.checksum;
    f.checksum  = 0;
    if(CNET_ccitt((unsigned char *)&f, (int)len) != checksum) {
        printf("\t\t\t\tBAD checksum - frame ignored\n");
        return;           // bad checksum, ignore frame
    }
	//Double check checksum again (simulate 2 nodes)
    /*checksum    = f.checksum;
    f.checksum  = 0;
    if(CNET_ccitt((unsigned char *)&f, (int)len) != checksum) {
        printf("\t\t\t\tBAD checksum - frame ignored\n");
        return;           // bad checksum, ignore frame
    }*/

    switch (f.kind) {
    case DL_ACK :{
        if(f.seq == ackexpected) {
            printf("\t\t\t\tACK received, seq=%d\n", f.seq);
            CNET_stop_timer(lasttimer);
            ackexpected = 1-ackexpected;
            CNET_enable_application(ALLNODES);
        }
	break;}

    case DL_DATA :{
        printf("\t\t\t\tDATA received, seq=%d, ", f.seq);
        if(f.seq == frameexpected) {
            printf("up to application\n");
	    transmit_frame(NULL, DL_ACK, 0, f.seq);	
            len = f.len;
	    printf("\t\t\t\t f.msg=%d" , f.msg);
	    printf("\t\t\t\t f.len=%d" , len);
            CHECK(CNET_write_application(&f.msg, &len));
	    //CNET_write_application(&f.msg, &len);
            frameexpected = 1-frameexpected;
        }
        else {
            printf("ignored\n");}
        
	break;
    }
    }
}


static EVENT_HANDLER(timeouts)
{
    printf("timeout, seq=%d\n", ackexpected);
    //CHECK(CNET_stop_timer(lasttimer));
    if(nodeinfo.nodetype == NT_ROUTER){
	    transmit_frame2(buffermsg, DL_DATA, lastBufferLength, ackexpected);
		return;
	}
    transmit_frame(lastmsg, DL_DATA, lastlength, ackexpected);
}

static EVENT_HANDLER(showstate)
{
    printf(
    "\n\tackexpected\t= %d\n\tnextframetosend\t= %d\n\tframeexpected\t= %d\n",
		    ackexpected, nextframetosend, frameexpected);
}

EVENT_HANDLER(reboot_node)
{
    //if(nodeinfo.nodenumber > 1) {
	//fprintf(stderr,"This is not a 2-node network!\n");
	//exit(1);
    //}
    buffermsg	= calloc(1, sizeof(MSG));
    lastmsg	= calloc(1, sizeof(MSG));
    if(nodeinfo.nodetype == NT_HOST) 
	CHECK(CNET_set_handler( EV_APPLICATIONREADY, application_ready, 0));
    CHECK(CNET_set_handler( EV_PHYSICALREADY,    physical_ready, 0));
    CHECK(CNET_set_handler( EV_TIMER1,           timeouts, 0));
    CHECK(CNET_set_handler( EV_DEBUG0,           showstate, 0));

    CHECK(CNET_set_debug_string( EV_DEBUG0, "State"));

    if(nodeinfo.nodenumber == 0)
	CNET_enable_application(ALLNODES);
}
