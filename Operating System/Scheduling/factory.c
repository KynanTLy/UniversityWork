#include <stdio.h>
#include <stdlib.h>
#include <sys/shm.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#include <pthread.h>
#include "assemblers.h"
#include "packers.h"
#include "factory.h"

/**
	Note: Running valgrind --tool=memcheck --leak-check=yes ./Factory 5 5 10 20 1000
	      Takes sometime to finish

	      Although running valgrind --tool=memcheck --leak-check=yes ./Factory 1 1 1 1 1
	      run quickly, it seem other cases with higher value argument takes more time 

*/

//===Global Variables===//
queVar *qV;

/** @brief Init all the values for queVar, as well as pthread
 *         
 *  @param numAssem = Total number of Assembler
 *        
 *  @param amountEach = Total amount of product a single Assembler must produce
 *      
 *  @param arraysize = Size of assembly line
 *       
 *  @param packsize = Size of a box
 *          
 *  @return void.
 */ 
void init(int numAssem, int amountEach, int arraysize, int packsize){
  	qV = (queVar *)malloc( sizeof(queVar));
	qV->insert = -1; 
	qV->del = -1;   
	qV->count = arraysize; 
	qV->amountProduceEach = amountEach; 
	qV->totalNumProduct = numAssem*amountEach;
	qV->packageSize = packsize;
	qV->queArray = malloc((size_t)arraysize * sizeof(product));
	qV->info = (threadQueInfo *) malloc(sizeof(threadQueInfo));

	//====Init Pthread====//
	pthread_mutex_init(&((qV->info)->mutex),NULL);
	pthread_cond_init(&((qV->info)->fcond), NULL);
}//end void init


/** @brief Takes in 5 parameters (#assembler, #packer, Size of assembly line, #product per box, #product each assembler makes)
 *         Emulate an assembly line for a factory, where each assembler makes X product and places it on an assembly line
 *	   Where packers then take said product and place it in a box of Y size, whereby when full it will print content.
 *  @param 1st parameter = # of assembler threads
 *        
 *  @param 2nd paraneter = # of packer threads
 *      
 *  @param 3rd parameter = Size of assembly line
 *       
 *  @param 4th parameter = # product per box
 *          
 *  @param 5th parameter = # product each assembler produces
 *
 *  @return void.
 */ 
int main (int argc, char *argv[]){

	//====Init Variables====//
	int i, err, err2;

	//====Check correct # of parameter====//
	if (argc > 6){
		printf("Too many arguement \n");
		return (0);	
	} else if (argc < 5){
		printf("Missing argument \n");
		return (0);
	}//end check correct number of arguements


	//===Save arguments into varibles===//
	int numAssembler = atoi(argv[1]);
	int numPacker = atoi(argv[2]);
	int sizeAssemLine = atoi(argv[3]);
	int sizePacker = atoi(argv[4]);
	int numProductEach = atoi(argv[5]);

	//===Init Struct + pthreads mutex/cond===//
	init(numAssembler, numProductEach, sizeAssemLine, sizePacker);
	pthread_t tid[numAssembler];
	pthread_t tid2[numPacker];
	void *tret = NULL;

	srand((unsigned int)time(0));

	//===Create packer/assembler threads===//
	for(i = 0;i<numAssembler; i++){
		err = pthread_create(&tid[i], NULL, insertProduct, (void *)qV);
		if (err!=0){
			perror("error n thread creation");
		}
	}//end for loop

	for(i = 0;i<numPacker; i++){
		err2 = pthread_create(&tid2[i], NULL, removeProduct, (void *)qV);
		if (err2!=0){
			perror("error n thread creation");
		}
	}//end for loop

	//===Join packer/assembler threads===//
	for(i = 0;i<numAssembler; i++){
		err = pthread_join(tid[i], &tret);
		if (err!=0){
			perror("can't join thread");
		}
	}//end for loop

	for(i = 0;i<numPacker; i++){
		err2 = pthread_join(tid2[i], &tret);
		if (err2!=0){
			perror("can't join thread");
		}
	}//end for loop

	//===Free mallocs===//
      	free(qV->queArray);
        free(qV->info);
	free(qV);


	exit(0);

}//end main



















