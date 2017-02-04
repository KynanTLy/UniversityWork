#include "String.H"
#include <iostream>
#include <cassert>
#include <string.h>
using namespace std;

String::String(const char *p){
	//Allocate memory for struct first then char
    shared = new SharedData();
    shared->data = new char[strlen(p)+1];
    strcpy(shared->data, p);
    shared->n = (int) strlen (p);
    shared->count = 1;
}//end Constructor

String::~String(){
	//Delete if you are last copy
    assert((int)shared->count > -1);
    if(shared->count > 1){
        shared->data--;
    } else {
        delete [] shared->data;
        delete shared;
    }
}//end Destructor

String::String(const String &x){
	//Create new pointer and increase count
	shared = x.shared;    
	shared->count++;
}//end CC

String &String::operator=(const String &x){
    if(this==&x){
		return *this;
	} else {
		if(shared->count > 1){
			shared->count--;
		} else {
			delete [] shared->data;
        	delete shared;
		}
		//Create new pointer and increase count
		shared = x.shared;
		shared->count++;
		return *this;
	}
	
}//end AO

size_t String::size() const {
    return shared->n;
}//end size()

size_t String::ref_count() const {
    return shared->count;
}//end ref_count()

const char * String::cstr() const {
    return shared->data;
}//end cstr
