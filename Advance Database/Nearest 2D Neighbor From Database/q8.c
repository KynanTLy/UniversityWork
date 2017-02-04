#include <stdio.h>
#include <sqlite3.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include<stdbool.h>
#include<limits.h>
#include<float.h>

/*
	//BUBBLE SORT (LINK 1)
	http://faculty.salina.k-state.edu/tim/CMST302/study_guide/topic7/bubble.html

	//How to use char tokens
	http://stackoverflow.com/questions/3889992/how-does-strtok-split-the-string-into-tokens-in-c

	//Link list
	https://www.tutorialspoint.com/data_structures_algorithms/linked_list_program_in_c.htm
	http://www.thegeekstuff.com/2012/08/c-linked-list-example
*/
//===========GLOBAL VARIBLES=============//

typedef struct MBR
{
   	unsigned long ID;
   	float  x1;
	float  y1;
	float  x2;
	float  y2;
	float  minD;
	float  minMaxD;
	struct MBR *nextMBR;
}MBR;

float pointx, pointy;
sqlite3 *db;

//===========END GLOBAL VARIBLES=============//

//==========HELPER TO CAL MINDISTANCE, MINMAXDISTANCE===========//	  
float minDis(float px, float py, float sx, float tx, float sy, float ty){
	float rx, ry;

	if(px < sx){
		rx = sx;
	} else if (px > tx) {
		rx = tx;
	} else {
		rx = px;
	}

	if(py < sy){
		ry = sy;
	} else if (py > ty) {
		ry = ty;
	} else {
		ry = py;
	}

	return ( ((px - rx)*( px - rx))+ ((py -ry)*(py -ry))); 
}

float minMaxDis(float px, float py, float sx, float tx, float sy, float ty){
	float rmx, rmy, rMx, rMy, dis1, dis2;

	if (px <= ((sx+tx)/2)) {
		rmx = sx;
	} else {
	  	rmx = tx;
	}

	if (py <= ((sy+ty)/2)) {
	   rmy = sy;
	} else {
	   rmy = ty;
	}

	if (px >= ((sx+tx)/2)) {
	   rMx = sx;
	} else {
	   rMx = tx;
	}

	if (py >= ((sy+ty)/2)) {
	   rMy = sy;
	} else {
	   rMy = ty;
	}

  	dis1 = ((px - rmx)*(px - rmx)) + ((py - rMy)*(py - rMy));
  	dis2 = ((py - rmy)*(py - rmy)) + ((px - rMx)*(px - rMx));

	return ((dis1 < dis2) ? dis1 : dis2);
}
//==========END HELPER TO CAL MINDISTANCE, MINMAXDISTANCE===========//	  

//==============HELPER FUNCTION LINK LIST===============//
void InitList(unsigned long ID, float x1, float y1, float x2, float y2, float minD, float minMaxD, MBR **head, MBR** tail){
    MBR *tempMBR = (MBR*)malloc(sizeof(MBR));
    tempMBR->ID = ID;
	tempMBR->x1 = x1;
	tempMBR->y1 = y1;
	tempMBR->x2 = x2;
 	tempMBR->y2 = y2;
	tempMBR->minD = minD;
	tempMBR->minMaxD = minMaxD;
    tempMBR->nextMBR = NULL;
    *head = *tail = tempMBR;
 
}

void LinkAdd(unsigned long ID, float x1, float x2, float y1, float y2, float minD, float minMaxD, int * length, MBR **head, MBR** tail){
    if(NULL == *head){
		(*length) = (*length)+1;
        InitList(ID,x1,y1,x2,y2,minD,minMaxD, head, tail);
    } else {

		MBR *tempMBR = (MBR*)malloc(sizeof(MBR));
	 
		tempMBR->ID = ID;
		tempMBR->x1 = x1;
		tempMBR->y1 = y1;
		tempMBR->x2 = x2;
	 	tempMBR->y2 = y2;
		tempMBR->minD = minD;
		tempMBR->minMaxD = minMaxD;
		tempMBR->nextMBR = NULL;
		(*tail)->nextMBR = tempMBR;
		*tail = tempMBR;
		(*length) = (*length)+1;
	}
}

MBR* search(unsigned long * IDval, MBR **prev, MBR** head){
    MBR *tempMBR = *head;
    MBR *tmp = NULL;
    bool found = false;

    while(tempMBR != NULL){
        if(tempMBR->ID == (*IDval)){
            found = true;
            break;
        } else {
            tmp = tempMBR;
            tempMBR = tempMBR->nextMBR;
        }
    }

    if(true == found){
        if(prev)
            *prev = tmp;
        return tempMBR;
    } else {
        return NULL;
    }
}

//delete element in linklist
void delete(unsigned long * IDval, MBR **head, MBR ** tail){
    MBR *prev = NULL;
    MBR *del = NULL;

    del = search(IDval,&prev, head);
	if(del == NULL){
        return;	
    } else {
        if(prev != NULL){
            prev->nextMBR = del->nextMBR;
		}
        if(del ==  *tail){
            *tail = prev;
        } else if(del==*head){
            *head = del->nextMBR;  
        } 
    }
    free(del);
    del = NULL;

    //return 0;
}

void copy( MBR **src, MBR **des)
{

	(*des)->ID = (*src)->ID;
	(*des)->x1 = (*src)->x1;
	(*des)->x2 = (*src)->x2;
	(*des)->y1 = (*src)->y1;
	(*des)->y2 = (*src)->y2;
	(*des)->minD = (*src)->minD;
	(*des)->minMaxD = (*src)->minMaxD;
	
}
//===========RECURSIVE BUBBLE SORT TAKEN FROM LINK 1===============/
MBR *sort( MBR ** head, MBR **tail, float px, float py){
	MBR *temp = malloc(sizeof(MBR));

    if(*head == NULL) {
		return NULL;
	}    
	if( (*head)->nextMBR != NULL){
		float a = (*head)->minMaxD;
		float b = (*head)->nextMBR->minMaxD;		
		if (a > b){
			copy(head, &temp);
			copy(&((*head)->nextMBR), head);
			copy(&temp, &((*head)->nextMBR));		
		}
	}

	(*head)->nextMBR = sort(&((*head)->nextMBR), tail, px, py);	

	if( (*head)->nextMBR != NULL){
		float c = (*head)->minMaxD;
		float d = (*head)->nextMBR->minMaxD;		
		if (c > d){
			copy(head, &temp);
			copy(&((*head)->nextMBR), head);
			copy(&temp, &((*head)->nextMBR));		
			(*head)->nextMBR = sort(&((*head)->nextMBR), tail, px, py);	
		}
	}

	free(temp);
    return *head;
}
//==============HELPER FUNCTION LINK LIST===============//		

//==============FUNCTION NEEDED TO IMPMENT THE PAPER==========//
void Prune( MBR ** head, MBR **tail, int * length, bool downprune, MBR ** nearest){
	//Init variables	
	MBR *currentHead = (*head);
	float smallMinMax = (*head)->minMaxD;
	int templength = (*length)-1;	

	//Pruning strategy 1,2 for downward
	if(downprune == true){
		//If the smallest minmax dis is smaller than nearest min distance swap
		if ((*nearest)->minD >= smallMinMax) {
            (*nearest)->minD = smallMinMax;
        }
		//Strategy 1 2
		for(int i = 0; i<= templength; ++i){
		    if ( currentHead->minD >= smallMinMax || currentHead->minD >= (*nearest)->minD) {
				delete(&(currentHead->ID), head, tail);
				(*length) = (*length)-1;
		    }
			//If there is another MBR switch to next
			if(!(currentHead->nextMBR==NULL)){
				currentHead = currentHead->nextMBR;
			}
		}
	} else {
		//Pruning strategy 3 for upward
		for (int i = 0; i <= templength; ++i) {
			//If you meet the condition delete the current and go to the next MBR
		    if ((currentHead->minD >= (*nearest)->minD)) {
				delete(&(currentHead->ID), head, tail);
		        (*length) = (*length)-1;
		    } 
			//If there is anotehr MBR switch to it
			if(!(currentHead->nextMBR==NULL)){
				currentHead = currentHead->nextMBR;
			}
		}//end for loop
	}
}

//Populate the MBR with children
void getChildren(MBR** newchildhead, MBR** newchildtail, int* childlength, unsigned long* nodeNum ){

	//Init variables to parse token and add to link list
	unsigned long  tempID;
	float tempx1,tempy1,tempx2,tempy2, tempMinD, tempMinMax;
	int i =0;
	char delim[] = "{ }";
	char* token;
	
	//Init variable for query and to hold results
	sqlite3_stmt *stmt;
	char branch[2048];	

	//Run query to get all the children
	char *test = sqlite3_mprintf(
	            	"SELECT rtreenode(2, data) "\
					"FROM Map_node "\
					"WHERE nodeno =%lu", *nodeNum
	);
	int rc = sqlite3_prepare_v2(db, test, -1, &stmt, 0);

  	if( rc!= SQLITE_OK) {
    	fprintf(stderr, "Preperation failed 3: %s\n", sqlite3_errmsg(db));
   	 	sqlite3_close(db);
  	}

	//Copy the result into branch
  	if(sqlite3_step(stmt) == SQLITE_ROW) {
    	strcpy(branch,sqlite3_column_text(stmt, 0));
  	}
	sqlite3_finalize(stmt);

	//Parse through the result and save the value to the corresponding variable
	//i is used to keep track of which part of the result you are parsing
	for (token = strtok(branch, delim); token; token = strtok(NULL, delim)){
			if(i%5 == 0){
   				tempID = strtol(token, NULL, 10);
			} else if (i%5 == 1){
				tempx1 = (float)atof(token);
			} else if (i%5 == 2){
				tempx2 =  (float)atof(token);
			} else if (i%5 == 3){
				tempy1 = (float)atof(token);
			} else if (i%5 == 4){
				//We completed 1 row of data ID, x1, x2, y1, y2 and therefore we can now calculate minD, minMaxD, and add to link list
				tempy2 = (float)atof(token);
				tempMinD = minDis(pointx, pointy,  tempx1,  tempx2,  tempy1,  tempy2);	
				tempMinMax = minMaxDis(pointx, pointy,  tempx1,  tempx2,  tempy1,  tempy2);
				LinkAdd(tempID, tempx1, tempx2,tempy1,tempy2,tempMinD, tempMinMax, childlength ,newchildhead, newchildtail);	
			}
			++i;
	}//end for loop
}

//Determinds if the node is a leaf or not
int isleaf(unsigned long* nodeNum){
	sqlite3_stmt *stmt; 
	int isLeaf = 0;
	//See if the node has children
	char *test = sqlite3_mprintf(
	            	"SELECT rtreenode(2, data) "\
					"FROM Map_node "\
					"WHERE nodeno = %d", *nodeNum
	);
	char areleaf[2048];
	int rc = sqlite3_prepare_v2(db, test, -1, &stmt, 0);
	int step = sqlite3_step(stmt);
	char delim[] = "{ }";
	char* token;
	strcpy(areleaf,sqlite3_column_text(stmt, 0));
	token = strtok(areleaf, delim);
	sqlite3_finalize(stmt);

	//See if the first element of that node has children
	test = sqlite3_mprintf(
	            	"SELECT rtreenode(2, data) "\
					"FROM Map_node "\
					"WHERE nodeno = %s", token
	);
	rc = sqlite3_prepare_v2(db, test, -1, &stmt, 0);

	//If the second query has no results, we are the leaf MBR and nodeNum children are row_ids from the original database
	step = sqlite3_step(stmt);
	if(step == SQLITE_DONE){
    	isLeaf = 1;
	}
	sqlite3_finalize(stmt);	
	return isLeaf;
}

void nearestNeighbor(int* childlength, unsigned long* nodeNum, MBR **nearest){
	//Init Variables head and tail
	MBR *newchildhead = NULL;
	MBR *newchildtail = NULL;
	
	//Check to see if leaf
	int isLeaf = isleaf(nodeNum);

	//If you are a leaf 
	if(isLeaf == 1){
		MBR * leafhead = NULL;
		MBR * leaftail = NULL;
		int leaflength = 0;
		//Get children of leaf node
		getChildren(&leafhead, &leaftail, &leaflength, nodeNum);	

		//if the leafhead is smaller than nearest switch
		for(int i = 0; i < leaflength; ++i){
			if (leafhead->minD < (*nearest)->minD) {
				(*nearest)->ID = leafhead->ID;
				(*nearest)->minD =  leafhead->minD;
				(*nearest)->minMaxD =  leafhead->minMaxD;
				(*nearest)->x1 = leafhead->x1;
				(*nearest)->x2 = leafhead->x2;
				(*nearest)->y1 = leafhead->y1;
				(*nearest)->y2 = leafhead->y2;
            }
			if(i != leaflength-1){
				leafhead = leafhead->nextMBR;
			}
		}//end for loop
	
	//If not leaf
	} else {
		int last = 0;
		//Get children
		getChildren(&newchildhead, &newchildtail, &last, nodeNum);
		//Sort based on MinMaxDis
		newchildhead = sort(&newchildhead, &newchildtail, pointx,pointy);
		//Prune with strategy 2 on (the true value)
		Prune(&newchildhead, &newchildtail, &last, true, nearest );

		for(int i = 0; i < last-1; ++i){
			 
			int templength = 0;
			//Get the ID of element in linklist
			unsigned long nextID = newchildhead->ID;
			//Recursive call on that ID
			nearestNeighbor(&templength, &nextID, nearest);
			
			//Prune again
			Prune(&newchildhead, &newchildtail, &last, false, nearest);
			if(i == last-1 && (newchildhead->nextMBR!= NULL)){
				newchildhead = newchildhead->nextMBR;
			}
		}//end for loop		
	}//end if leaf or not
}

//Runs query to delete element from database
void deleteN(MBR ** nearest){
	sqlite3_stmt *stmt; //the update statement
    char *delete_closest_stmt = sqlite3_mprintf(
				"delete from Map where id = %lu", (*nearest)->ID
	);
	int rc = sqlite3_prepare_v2(db, delete_closest_stmt, -1, &stmt, 0);

	if (rc != SQLITE_OK) {
			fprintf(stderr, "Preparation 2 failed: %s\n", sqlite3_errmsg(db));
			sqlite3_close(db);
			return;
	}
	sqlite3_exec(db, delete_closest_stmt, 0, 0, 0);
	sqlite3_finalize(stmt); //finalize statement 2
}

//==============END FUNCTION NEEDED TO IMPMENT THE PAPER==========//
int main(int argc, char **argv){
	sqlite3_stmt *stmt; //the update statement

	int *branchnode;
  	int rc;

  	if( argc!=5 ){
  		fprintf(stderr, "Usage: %s <database file> <x1> <y1> <k>"
  				"\n", argv[0]);
    	return(1);
  	}

  	rc = sqlite3_open(argv[1], &db);
  	if( rc ){
	    fprintf(stderr, "Can't open database: %s \n", sqlite3_errmsg(db));
	    sqlite3_close(db);
	    return(1);
  	}
	//Save input of pointx and pointy into global
	pointx = atof(argv[2]);
	pointy = atof(argv[3]);

	//Init nearest 
	MBR *nearest = malloc(sizeof(MBR));
	nearest->minD = FLT_MAX;
	nearest->ID = 0;
	int newlength = 0;
	unsigned long node = 1;

	//Run nearestNeighbor
	int k = atoi(argv[4]);
	for (int i = 0; i < k; ++i) {
		nearestNeighbor( &newlength, &node , &nearest);
		printf("The nearest neighbour is %lu \n", nearest->ID);
		//Delete the closest from BD and then run again 
		deleteN(&nearest);
		//reset nearest
		nearest->minD = FLT_MAX;
		nearest->ID = 0;
		node = 1;
	}


	
	return 0;
}
