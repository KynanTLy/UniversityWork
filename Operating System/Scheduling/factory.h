#ifndef factory
#define factory
#include "assemblers.h"

//===Product Struct===//
typedef struct{
	const char *colour;
	int productNum;
}product;


//===Pthread Variables Struct===//
typedef struct{
	pthread_cond_t fcond;
	pthread_mutex_t mutex;

}threadQueInfo;


//===Information on Parameter Struct (also contains the assembly line + Pthread var struct)===//
typedef struct{
	int insert;
	int del;
	int count;
	int amountProduceEach;
	int totalNumProduct;
	int packageSize;
	threadQueInfo *info;
	product *queArray;
}queVar;

#endif
