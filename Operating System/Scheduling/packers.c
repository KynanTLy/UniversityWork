#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#include <pthread.h>
#include "factory.h"
#include "assemblers.h"

/** @brief Removes product from the que and places in box
 *         Prints product in box if box is full
 *	   Prints product remaining in box at end if any	
 *
 *  @param Takes in the pointer to queVar
 *
 *  @return void.
 */

void *removeProduct(void *arg){

	//====Init Variables====//	
	queVar *qV = (queVar *)arg;
	int packageIndex = 0;
	int i;

	//=====Create Box======//
	product pack[qV->packageSize-1];

	//====Get Thread ID====//
	unsigned int thread_Id = (unsigned int)(pthread_self()%10000);

	//===If there is product attemp to remove a product===//
	while (qV->totalNumProduct > 0){
      		pthread_mutex_lock(&(qV->info->mutex));

		//===If assembly line not empty===//
		if((qV->del) >  -1){
			qV->totalNumProduct-=1;
			pack[packageIndex] = qV->queArray[qV->del];
			packageIndex += 1;
	
			//===If assembly line is full broadcast===//
			if ((qV->del == 0 && qV->insert == ((qV->count) -1)) || (qV->del == ((qV->insert) + 1)) ){
			pthread_cond_broadcast(&(qV->info->fcond)); }

			//===If box is full Print===//
			if(packageIndex >= (qV->packageSize)){
			  printf("[Packer %d]: I have a box of product containing: ", thread_Id);
				for(i = 0; i< qV->packageSize; i++){
				  printf("%s %d, ", pack[i].colour, pack[i].productNum);
				}//end for loop
				printf("\n");
				packageIndex = 0;
			}//end if

			//===Update Index===//
			if (qV->insert == qV->del){
				qV->insert = -1;
				qV->del = -1;
			} else if (qV->del == qV->count -1){
				qV->del = 0;			
			}else {
				qV->del = qV->del + 1;	
			}//end if for insert/delete

		}//end if not empty
		

		//===Print remainder in box if any===//
		if(qV->totalNumProduct < 1 && packageIndex > 0){
		  printf("Package %d contains: ", thread_Id);
		  for(i = 0; i < packageIndex; i++){
		    printf("%s %d ", pack[i].colour, pack[i].productNum);
		  }
		  printf("\n");
		}//end if remainder 

		pthread_mutex_unlock(&(qV->info->mutex));
	}//end while
	return ((void *)0);
}//end remove product
