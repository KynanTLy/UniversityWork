#include "Set.H"
#include <iostream>
#include <cassert>
#include <string.h>
#include <math.h>
using namespace std;

//Set size of INT in bits
static const int  INT_BITS = sizeof(int)*8;

Set::Set(size_t n){
  	size_t numBit = ceil( n/INT_BITS);
  	a = numBit;
	//Assign memory for bit
 	bits = new int[a];
  	this->n = n;
}//end Constructor

Set::~Set(){
	//Free bits
    delete [] bits;
}//end Destructor

Set::Set(const Set &s){
	//Assign memory for bits
    bits = new int[a];
	//Copy each value int[]
    for(size_t i = 0; i < a; ++i){
    	bits[i] = s.bits[i];
    }//end for loop
    n = s.n;
	a = s.a;	
}//end CC

Set &Set::operator=(const Set &s){
    if(this==&s){
		//prevent self assignemnt 
		return *this;
	} else {
	    assert(n==s.n);	
      	for(size_t i  = 0; i < a; ++i){
        	bits[i] = s.bits[i];
      	}//end for loop
      	n = s.n;
      	a = s.a;
      	return *this;
    }//end if 
}//end AO

void Set::clear() {
  for(size_t i = 0; i < a; ++i){
	//Set all values to 0
    bits[i] = bits[i] & 0;
  }
}//end clear()

void Set::complement() {
  for(size_t i = 0; i < a; ++i ){
    bits[i] = ~bits[i];
  }//end for loop
}//end complement()

bool Set::has(size_t x) const {
  	//Find position and index of X
	int position = x/INT_BITS;
	int value = x % INT_BITS;
	//Determine if true or false
	if ((bits[position] >> value) &  1){ 
		return true;
	} else {
		return false;
	}//end if
}//end has()

void Set::add(size_t x){
	//Find position and index of X
	int position = x/INT_BITS;
	int value= x % INT_BITS;
	//Shift correct value
	int temp = 1 << value;
	bits[position] = bits[position] | temp;
}//end add()

void Set::remove(size_t x){
	//Find position and index of 
	int position = x/INT_BITS;
	int value = x % INT_BITS;
	//Shift correct value
	int temp = 1 << value;
	bits[position] = temp &  ~bits[position];
}//end remove()

void Set::add(const Set &s){
	assert(s.a == a);
    //Or each int in int[]
	for(size_t i = 0; i < a; ++i){
    	bits[i] = bits[i] | s.bits[i];
	}//end loop
  
}//end add(set s)

void Set::remove(const Set &s){
	assert(s.a == a);
	//AND~ each int in int[]
	for(size_t i = 0; i < a; ++i){
    	bits[i] = bits[i] & ~s.bits[i];
  	}//end for loop
}//end remove(set s)

void Set::print(std::ostream &os) const {
  os << '[' << " ";
  for(size_t i = 0; i < n; ++i){
    if (this->has(i)){
      os<<i <<" ";
    }//end if
  }//end forloop
  os<<']';
}//end print()








