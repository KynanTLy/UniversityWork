/*
C functions that are linked into our LLVM
You'll notice all of the delcares at the top of LLVM.stg
thats these.
*/


#include <stdio.h>
#include <stdlib.h>
#include "functions.h"



// Our stuff

float promote(int i) {
    return (float)i;
}

char promoteNC(int i) {
    return (char)i;
}

char promoteNB(int i) {
    return (char)i;
}

float promoteNR(int i) {
    return (float)i;
}

int promoteBI(char i) {
    if (i == 1) {
        return 1;
    } else {
        return 0;
    }
}

float promoteBR(char i) {
    if (i == 1) {
        return 1.0;
    } else {
        return 0.0;
    }
}

char promoteBC(char i) {
    if (i == 1) {
        return '1';
    } else {
        return '0';
    }
}

char promoteIB(int i) {
    if (i == 0) {
        return 0;
    } else {
        return 1;
    }
}

char promoteIC(int c) {
    return c%256;
}

int promoteRI(float r) {
    return (int)r;
}

char promoteCB(char c) {
    if (c == 0) {
        return 0;
    }

    return 1;
}

int promoteCI(char c) {
    return (int)c;
}

float promoteCR(char c) {
    return (float)c;
}


int powerII(int l, int r){
    return (pow(l,r));
}

void printReal(float x){
    printf("%g", x);
}


void printBool(char x){
    if(x == 1){
        printf("T");
    } else {
        printf("F");
    }
}

char Booleq(char left, char right){
    if(left == right){
        return 1;
    } else {
        return 0;
    }
}

char Boolneq(char left, char right){
    if(left != right){
        return 1;
    } else {
        return 0;
    }
}

int IunaryPlus(int val){
    return +(val);
}

int IunaryNeg(int val){
    return -(val);
}


float RunaryPlus(float val){
    return +(val);
}

float RunaryNeg(float val){
    return -(val);
}


void printChar(char x) {
    printf("%c",x);
}

void readInt(int *x) {
    int bufflen = 512;
    char buffer[bufflen];
    char outBuf[bufflen];

    scanf("%s", buffer);

    int j = 0;
    for (int i=0; i < bufflen; i++) {
        if (buffer[i] != '_') {
            outBuf[j] = buffer[i];
            j++;
        }
    }

    sscanf(outBuf, "%d", x);
}

void readFloat(float *x) {
    int bufflen = 512;
    char buffer[bufflen];
    char outBuf[bufflen];

    scanf("%s", buffer);

    int j = 0;
    for (int i=0; i < bufflen; i++) {
        if (buffer[i] != '_') {
            outBuf[j] = buffer[i];
            j++;
        }
    }

    sscanf(outBuf, "%f", x);
}

void readChar(char *x) {
    //scanf("%c", x);

    // newlines and spaces are being left in input buffer
    // When we just get a char. Hopefully this will fix it...
    char buff[512];
    scanf("%s", buff);
    *x = buff[0];
}

void readBool(char *x) {
    char buff[512];

    scanf("%s", buff);

    if (strcmp(buff, "false") == 0)
        *x = 0;
    if (strcmp(buff, "true") == 0)
        *x = 1;
}

void dealWithFloats(float *x, char *s) {
    int bufflen = 512;
    char outBuf[bufflen];
    int j = 0;
    for (int i=0; s[i] != '\0'; i++) {
        if (s[i] != '_') {
            outBuf[j] = s[i];
            j++;
        }
    }
    sscanf(outBuf, "%f", x);
}

char Boolxor(char left, char right){
    return (left^right);
}

char Boolor(char left, char right){
    return (left | right);
}

char Booland(char left, char right){
    if(left != right){
        return 0;
    }
    return 1;
}

char Boolnot(char boolean){
    if(boolean == 1){
        return 0;
    }
    return 1;
}


float realMod(float left, float right) {
    return fmod(left, right);
}

float realExpon(float left, float right) {
    return pow(left, right);
}


/*
 *  Vector
 *
 */

int * allocateIntegerVector(int size) {
    int * array = malloc(sizeof(int) * (size+1));
    memset(array, 0, sizeof(int) * (size+1));
    array[0] = size;
    return array;
}

float * allocateRealVector(int size) {
    float * array = malloc(sizeof(float) * (size+1));
    memset(array, 0, sizeof(float) * (size+1));
    array[0] = size;
    return array;
}

char * allocateCharacterVector(int size) {
    char * array = malloc(sizeof(char) * (size+1));
    memset(array, 0, sizeof(char) * (size+1));
    array[0] = size;
    return array;
}


int * allocateIdentityIntegerVector(int size) {
    int * array = malloc(sizeof(int) * (size+1));
    for(int i = 1; i <= size; i++) {
    	array[i] = 1;
    }
    array[0] = size;
    return array;
}

float * allocateIdentityRealVector(int size) {
    float * array = malloc(sizeof(float) * (size+1));
    for(int i = 1; i <= size; i++) {
    	array[i] = 1.0;
    }
    array[0] = size;
    return array;
}

char * allocateIdentityCharacterVector(int size) {
    char * array = malloc(sizeof(char) * (size+1));
    for(int i = 1; i <= size; i++) {
    	array[i] = 1;
    }
    array[0] = size;
    return array;
}

char * allocateIdentityBoolVector(int size) {
    char * array = malloc(sizeof(char) * (size+1));
    memset(array, 1, sizeof(char) * (size+1));
    array[0] = size;
    return array;
}


char * allocateBoolVector(int size) {
    char * array = malloc(sizeof(char) * (size+1));
    memset(array, 0, sizeof(char) * (size+1));
    array[0] = size;
    return array;
}

void storeIntegerInVector(int * vector, int value, int position) {
    vector[position] = value;
}

void storeRealInVector(float * vector, float value, int position) {
    vector[position] = value;
}

void storeCharacterInVector(char * vector, char value, int position) {
    vector[position] = value;
}

void storeBoolInVector(char * vector, char value, int position) {
    vector[position] = value;
}

void printIntegerVector(int * vector) {
    int length = vector[0];
    for (int i = 1; i <= length; i++) {
        printf("%d", vector[i]);
    }
}

void printRealVector(float * vector) {
    int length = vector[0];
    for (int i = 1; i <= length; i++) {
        printf("%g", vector[i]);
    }
}

void printCharacterVector(char * vector) {
    int length = vector[0];
    for (int i = 1; i <= length; i++) {
        printf("%c", vector[i]);
    }
}

void printBoolVector(char * vector) {
    int length = vector[0];
    for (int i = 1; i <= length; i++) {
        if(vector[i] == 1){
            printf("T");
        } else {
            printf("F");
        }
    }
}



// *x is the vector we want to index, indexer is the values we want to get

int* indexIntegerVectorWithVector(int *x, int *indexer) {
	int size = indexer[0];
	int *result = allocateIntegerVector(size);
	for(int i = 1; i <= size; i++) {
		storeIntegerInVector(result,x[indexer[i]],i);
	}
	return result;
}

float* indexRealVectorWithVector(float *x, int *indexer) {
	int size = indexer[0];
	float *result = allocateRealVector(size);
	for(int i = 1; i <= size; i++) {
		storeRealInVector(result,x[indexer[i]],i);
	}
	return result;
}

char* indexCharacterVectorWithVector(char *x, int *indexer) {
	int size = indexer[0];
	char *result = allocateCharacterVector(size);
	for(int i = 1; i <= size; i++) {
		storeCharacterInVector(result,x[indexer[i]],i);
	}
	return result;
}

char* indexBooleanVectorWithVector(char *x, int *indexer) {
	int size = indexer[0];
	char *result = allocateBoolVector(size);
	for(int i = 1; i <= size; i++) {
		storeBoolInVector(result,x[indexer[i]],i);
	}
	return result;
}

int* intervalToVector(int a, int b) {
	int size = (b - a) + 1;
	int * result = allocateIntegerVector(size);
	for(int i = 1; i <= size ; i++) {
		storeIntegerInVector(result,a+i-1,i);
	}
	return result;
}

// Allocates memory for a new vector, and stores the
// Resulting addition there.
char boolVectorEQII(int * left, int * right){
    for (int i = 1; i <= left[0]; i++) {
        if(left[i] != right[i]){
            return 0;
        }
     }
    return 1;
}

char boolVectorEQFF(float * left, float * right){
    for (int i = 1; i <= left[0]; i++) {
        if(left[i] != right[i]){
            return 0;
        }
     }
    return 1;
}

char boolVectorNEQII(int * left, int * right){
    for (int i = 1; i <= left[0]; i++) {
        if(left[i] != right[i]){
            return 1;
        }
     }
    return 0;
}

char boolVectorNEQFF(float * left, float * right){
    for (int i = 1; i <= left[0]; i++) {
        if(left[i] != right[i]){
            return 1;
        }
     }
    return 0;
}

char boolVectorEQBool(char * left, char * right){
    for (int i = 1; i <= left[0]; i++) {
        if(left[i] != right[i]){
            return 0;
        }
     }
    return 1;
}

char boolVectorNEQBool(char * left, char * right){
    for (int i = 1; i <= left[0]; i++) {
        if(left[i] != right[i]){
            return 1;
        }
     }
    return 0;
}


char *boolVectorxor(char* left, char* right){
    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        result[i] = left[i] ^ right[i];
    }

    return result;
}

char *boolVectoror(char* left, char* right){
    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        result[i] = (left[i] | right[i]);
    }

    return result;

}

char *boolVectorand(char* left, char* right){
    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        result[i] = (left[i] == right[i]);
    }

    return result;
}

char * boolVectornot(char* booleanV){
    char * result = allocateBoolVector(booleanV[0]);

    for (int i = 1; i <= booleanV[0]; i++) {
        if(booleanV[i] == 1){
            result[i] = 0;
        } else {
            result[i] = 1;
        }
    }
    return result;
}

char * FVectorLES(float * left, float * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] < right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * FVectorLESEQ(float * left, float * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] <= right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * FVectorGRT(float * left, float * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] > right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * FVectorGRTEQ(float * left, float * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] >= right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * IVectorLES(int * left, int * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] < right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * IVectorLESEQ(int * left, int * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] <= right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * IVectorGRT(int * left, int * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] > right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

char * IVectorGRTEQ(int * left, int * right) {

    char * result = allocateBoolVector(left[0]);

    for (int i = 1; i <= left[0]; i++) {
        if(left[i] >= right[i]){
            result[i] = 1;
        } else {
            result[i] = 0;
        }
    }

    return result;
}

int * addIntegerVectors(int * left, int * right) {
    int * longest;
    int * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    int * result = allocateIntegerVector(longest[0]);

    for (int i = 1; i <= longest[0]; i++) {
        if (i <= shortest[0]) {
            result[i] = longest[i] + shortest[i];
        } else {
            result[i] = longest[i];
        }
    }

    return result;
}

int * subIntegerVectors(int * left, int * right) {
    int * longest;
    int * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    int * result = allocateIntegerVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = left[i] - right[i];
    }

    return result;
}

int * mulIntegerVectors(int * left, int * right) {
    int * longest;
    int * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    int * result = allocateIntegerVector(longest[0]);

    for (int i = 1; i <= longest[0]; i++) {
        if (i <= shortest[0]) {
            result[i] = longest[i] * shortest[i];
        } else {
            result[i] = longest[i];
        }
    }

    return result;
}

int * divIntegerVectors(int * left, int * right) {
    int * longest;
    int * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    int * result = allocateIntegerVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = left[i] / right[i];
    }

    return result;
}

int * exponIntegerVectors(int * left, int * right) {
    int * longest;
    int * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    int * result = allocateIntegerVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = pow(left[i], right[i]);
    }

    return result;
}

int * modIntegerVectors(int * left, int * right) {
    int * longest;
    int * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    int * result = allocateIntegerVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = left[i] % right[i];
    }

    return result;
}

int * concatIntegerVectors(int * left, int * right) {
    int * result = allocateIntegerVector(left[0] + right[0]);

    for (int i = 1; i <= left[0]; i++) {
        result[i] = left[i];
    }

    for (int i = left[0]+1; i <= left[0]+right[0]; i++){
        result[i] = right[i-left[0]];
    }

    return result;
}

float * concatRealVectors(float * left, float * right) {
    float * result = allocateRealVector(left[0] + right[0]);

    for (int i = 1; i <= (int)left[0]; i++) {
        result[i] = left[i];
    }

    for (int i = (int)left[0]+1; i <= (int)left[0]+(int)right[0]; i++){
        result[i] = right[i-(int)left[0]];
    }

    return result;
}

char * concatCharacterVectors(char * left, char * right) {
    char * result = allocateCharacterVector((int)left[0] + (int)right[0]);

    for (int i = 1; i <= (int)left[0]; i++) {
        result[i] = left[i];
    }

    for (int i = (int)left[0]+1; i <= (int)left[0]+(int)right[0]; i++){
        result[i] = right[i-(int)left[0]];
    }

    return result;
}

char * concatBooleanVectors(char * left, char * right) {
    char * result = allocateBoolVector(left[0] + right[0]);

    for (int i = 1; i <= (int)left[0]; i++) {
        result[i] = left[i];
    }

    for (int i = (int)left[0]+1; i <= (int)left[0]+(int)right[0]; i++){
        result[i] = right[i-(int)left[0]];
    }

    return result;
}

int dotIntegerVector(int * left, int * right) {
    int result = 0;

    for (int i = 1; i <= left[0]; i++) {
        result += left[i] * right[i];
    }

    return result;
}

float dotRealVector(float * left, float * right) {
    float result = 0;

    for (int i = 1; i <= (int)left[0]; i++) {
        result += left[i] * right[i];
    }

    return result;
}

// Allocates memory for a new vector, and stores the
// Resulting addition there.
float * addRealVectors(float * left, float * right) {
    float * longest;
    float * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    float * result = allocateRealVector(longest[0]);

    for (int i = 1; i <= longest[0]; i++) {
        if (i <= shortest[0]) {
            result[i] = longest[i] + shortest[i];
        } else {
            result[i] = longest[i];
        }
    }

    return result;
}

float * subRealVectors(float * left, float * right) {
    float * longest;
    float * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    float * result = allocateRealVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = left[i] - right[i];
    }

    return result;
}

float * mulRealVectors(float * left, float * right) {
    float * longest;
    float * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    float * result = allocateRealVector(longest[0]);

    for (int i = 1; i <= longest[0]; i++) {
        if (i <= shortest[0]) {
            result[i] = longest[i] * shortest[i];
        } else {
            result[i] = longest[i];
        }
    }

    return result;
}

float * divRealVectors(float * left, float * right) {
    float * longest;
    float * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    float * result = allocateRealVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = left[i] / right[i];
    }

    return result;
}

float * exponRealVectors(float * left, float * right) {
    float * longest;
    float * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    float * result = allocateRealVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = pow(left[i], right[i]);
    }

    return result;
}

float * modRealVectors(float * left, float * right) {
    float * longest;
    float * shortest;
    if (left[0] >= right[0]) {
        longest = left;
        shortest = right;
    } else {
        longest = right;
        shortest = left;
    }
    float * result = allocateRealVector(shortest[0]);

    for (int i = 1; i <= shortest[0]; i++) {
        result[i] = fmod(left[i], right[i]);
    }

    return result;
}

int * unNIntegerVectors(int * vector) {
    int * result = allocateIntegerVector(vector[0]);

    for (int i = 1; i <= vector[0]; i++) {
        result[i] = -vector[i];
    }

    return result;
}

int * unPIntegerVectors(int * vector) {
    int * result = allocateIntegerVector(vector[0]);

    for (int i = 1; i <= vector[0]; i++) {
        result[i] = vector[i];
    }

    return result;
}

float * unNRealVectors(float * vector) {
    float * result = allocateRealVector(vector[0]);

    for (int i = 1; i <= vector[0]; i++) {
        result[i] = -vector[i];
    }

    return result;
}

float * unPRealVectors(float * vector) {
    float * result = allocateRealVector(vector[0]);

    for (int i = 1; i <= vector[0]; i++) {
        result[i] = vector[i];
    }

    return result;
}

int getIntVectorLength(int * vector) {
    return vector[0];
}

int getRealVectorLength(float * vector) {
    return (int)vector[0];
}

int getCharVectorLength(char * vector) {
    return (int)vector[0];
}

int getBoolVectorLength(char * vector) {
    return (int)vector[0];
}

/*
 *  Matrix
 *
 */


integermatrix allocateIntegerMatrix(int row, int col) {
	integermatrix matrix;
    matrix.data = calloc(row+1, sizeof(int));
	for(int i = 0; i < row+1; i++) {
		matrix.data[i] = calloc(col+1, sizeof(int));
	}
	matrix.col = col;
	matrix.row = row;
    return matrix;
}

realmatrix allocateRealMatrix(int row, int col) {
	realmatrix matrix;
    matrix.data = calloc(row+1, sizeof(float));
	for(int i = 0; i < row+1; i++) {
		matrix.data[i] = calloc(col+1, sizeof(float));
	}
	matrix.col = col;
	matrix.row = row;
    return matrix;
}


booleanmatrix allocateBooleanMatrix(int row, int col) {
	booleanmatrix matrix;
    matrix.data = calloc(row+1, sizeof(char));
	for(int i = 0; i < row+1; i++) {
		matrix.data[i] = calloc(col+1, sizeof(char));
	}
	matrix.col = col;
	matrix.row = row;
    return matrix;
}

charactermatrix allocateCharacterMatrix(int row, int col) {
	charactermatrix matrix;
    matrix.data = calloc(row+1, sizeof(char));
	for(int i = 0; i < row+1; i++) {
		matrix.data[i] = calloc(col+1, sizeof(char));
	}
	matrix.col = col;
	matrix.row = row;
    return matrix;
}



void storeIntegerVectorInMatrix(integermatrix *mat, int* value, int position) {
	mat->data[position] = value;
}
void storeRealVectorInMatrix(realmatrix *mat, float* value, int position) {
	mat->data[position] = value;
}
void storeBooleanVectorInMatrix(booleanmatrix *mat, char* value, int position) {
	mat->data[position] = value;
}
void storeCharacterVectorInMatrix(charactermatrix *mat, char* value, int position) {
	mat->data[position] = value;
}

