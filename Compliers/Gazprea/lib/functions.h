#pragma once

#include <stdint.h>
#include <math.h>
#include <string.h>

typedef struct vector{
	int value;
	struct vector * nextVec;
}vector;

uint64_t factorial(uint64_t n);

int add(int a, int b);


// Our stuff



void getNextVec(vector * v);
void printVec(vector * v);
vector * Range(int left, int right);
int returnIndex(vector * v, int target, int count);
int sizeVector(vector * v, int count);
vector * returnIndexV(vector * v, vector * index);
vector * clone(vector * v);
vector * vectorE(vector * v, int size, int type);
vector * intPromote(int value, int size);
vector * vecAdd(vector * left, vector * right);
vector * vecDiv(vector * left, vector * right);
vector * vecSub(vector * left, vector * right);
vector * vecMul(vector * left, vector * right);
int vecLES(vector * left, vector * right);
int vecGRE(vector * left, vector * right);
int vecEQ(vector * left, vector * right);
int vecNEQ(vector * left, vector * right);
int bigger(vector * left, vector * right);
int vecVal(vector * v);
vector * insert(vector * v, int val);
vector * move(vector * v);
int powerII(int l, int r);
float promote(int i);
void printReal(float x);
void printBool(char x);
void printChar(char x);
void readInt(int *x);
void readFloat(float *x);
void readChar(char *x);
void readBool(char *x);
void dealWithFloats(float *x, char *s);

char promoteNC(int i);
char promoteNB(int i);
float promoteNR(int i);

char Boolxor(char left, char right);
char Boolor(char left, char right);
char Booland(char left, char right);
char Booleq(char left, char right);
char boolneq(char left, char right);
char Boolnot(char boolean);

int IunaryPlus(int val);
int IunaryNeg(int val);
float RunaryPlus(float val);
float RunaryNeg(float val);

int promoteBI(char i);
float promoteBR(char i);
char promoteBC(char i);
char promoteIB(int i);
char promoteIC(int c);
int promoteRI(float r);
char promoteCB(char c);
int promoteCI(char c);
float promoteCR(char c);

int * allocateIntegerVector(int size);
float * allocateRealVector(int size);
char * allocateCharacterVector(int size);
char * allocateBoolVector(int size);

int * allocateIdentityIntegerVector(int size);
float * allocateIdentityRealVector(int size);
char * allocateIdentityCharacterVector(int size);
char * allocateIdentityBoolVector(int size);


void storeIntegerInVector(int * vector, int value, int position);
void storeRealInVector(float * vector, float value, int position);
void storeCharacterInVector(char * vector, char value, int position);
void storeBoolInVector(char * vector, char value, int position);
void printIntegerVector(int * vector);
void printRealVector(float * vector);
void printCharacterVector(char * vector);
void printBoolVector(char * vector);
float realMod(float left, float right);
float realExpon(float left, float right);


int* indexIntegerVectorWithVector(int *x, int *indexer);
float* indexRealVectorWithVector(float *x, int *indexer);
char* indexCharacterVectorWithVector(char *x, int *indexer);
char* indexBooleanVectorWithVector(char *x, int *indexer);


int* intervalToVector(int a, int b);
int * addIntegerVectors(int * left, int * right);
int * subIntegerVectors(int * left, int * right);
int * mulIntegerVectors(int * left, int * right);
int * divIntegerVectors(int * left, int * right);
int * exponIntegerVectors(int * left, int * right);
int * modIntegerVectors(int * left, int * right);
int * unNIntegerVectors(int * vector);
int * unPIntegerVectors(int * vector);

float * addRealVectors(float * left, float * right);
float * subRealVectors(float * left, float * right);
float * mulRealVectors(float * left, float * right);
float * divRealVectors(float * left, float * right);
float * exponRealVectors(float * left, float * right);
float * modRealVectors(float * left, float * right);
float * unNRealVectors(float * vector);
float * unPRealVectors(float * vector);

int * concatIntegerVectors(int * left, int * right);
float * concatRealVectors(float * left, float * right);
char * concatCharacterVectors(char * left, char * right);
char * concatBooleanVectors(char * left, char * right);

int dotIntegerVector(int * left, int * right);
float dotRealVector(float * left, float * right);

int getIntVectorLength(int * vector);
int getRealVectorLength(float * vector);
int getCharVectorLength(char * vector);
int getBoolVectorLength(char * vector);

typedef struct integermatrix {
	int row;
	int col;
    int **data;
} integermatrix;

typedef struct realmatrix {
	int row;
	int col;
    float **data;
} realmatrix;

typedef struct booleanmatrix {
	int row;
	int col;
    char **data;
} booleanmatrix;

typedef struct charactermatrix {
	int row;
	int col;
    char **data;
} charactermatrix;

integermatrix allocateIntegerMatrix(int row, int col);
realmatrix allocateRealMatrix(int row, int col);
booleanmatrix allocateBooleanMatrix(int row, int col);
charactermatrix allocateCharacterMatrix(int row, int col);

void storeIntegerVectorInMatrix(integermatrix *mat, int* value, int position);
void storeRealVectorInMatrix(realmatrix *mat, float* value, int position);
void storeBooleanVectorInMatrix(booleanmatrix *mat, char* value, int position);
void storeCharacterVectorInMatrix(charactermatrix *mat, char* value, int position);

float realMod(float left, float right);
float realExpon(float left, float right);


char boolVectorEQII(int * left, int * right);
char boolVectorEQFF(float * left, float * right);
char boolVectorNEQII(int * left, int * right);
char boolVectorNEQFF(float * left, float * right);
char boolVectorNEQBool(char * left, char * right);
char boolVectorEQBool(char * left, char * right);
char * IVectorLES(int * left, int * right);
char * IVectorLESEQ(int * left, int * right);
char * IVectorGRT(int * left, int * right);
char * IVectorGRTEQ(int * left, int * right);
char * FVectorLES(float * left, float * right);
char * FVectorLESEQ(float * left, float * right);
char * FVectorGRT(float * left, float * right);
char * FVectorGRTEQ(float * left, float * right);
char * boolVectorxor(char* left, char* right);
char * boolVectoror(char* left, char* right);
char * boolVectorand(char* left, char* right);
char * boolVectornot(char* booleanV);

