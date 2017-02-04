#include <stdio.h>
#include <sqlite3.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>

//===========CALCULATES DISTANCE GIVEN INPUT OF LAT AN LONG=============//
//===========2 BEING DESTINATION, 1 BEING SOURCE========================//
double CalculateDistance(double LAT1, double LAT2, double LONG1, double LONG2){
	double R = 6371.009;
	double NLat1 = (M_PI * LAT1)/180;
	double NLat2 = (M_PI *LAT2)/180;
	double NLong1 = (M_PI *LONG1)/180;
	double NLong2 = (M_PI * LONG2)/180;
	double a =  pow(  NLat2 - NLat1   ,2.0);
	double b =  cos( (NLat1 + NLat2)/2);
	double c =   ( NLong2 - NLong1);
	return R *sqrt(a + pow((b*c), 2.0) ) ;	
}//END CALCULATEDISTANCE

int main(int argc, char **argv){
	sqlite3 *db; //the database
	sqlite3_stmt *stmt; //the update statement

  	int rc;
	//=====SET SIZE OF LIST EQUIPMENT====//
	int size = 1;
	//=====COUNTER TO DETERMINE IF EQUIPMENT TO BE ADDED=====//
	int add = 0;
	//=====ENSURE FIRST EQUIPMENT IS ADDED======//
	int first = 0;
	//=====TOKEN TO USE TO PARSE FROM ATTRIBUTE EQUIPMENT=====//
	char* token = NULL;
	//=====USED TO STORE THE EQUIPMENT VALUES====//
	char** listEquip = (char**)malloc(size*sizeof(char*));

  	if( argc!=1 ){
    	fprintf(stderr, "Usage: DOES NOT REQUIRE USER INPUT \n");
    	return(1);
  	}

  	rc = sqlite3_open("Openflight.db", &db);
  	if( rc ){
    	fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
    	sqlite3_close(db);
    	return(1);
  	}

	//=====QUERY TO GET FLIGHTS OVER 10,000====//
  	char *sql_stmt = 
	"SELECT DISTINCT R1.Airline_ID, R1.Source_airport_ID, R1.Destination_airport_ID,A1.Latitude, A2.Latitude, 		A1.Longitude, A2.Longitude, R1.Equipment  \
	FROM routes R1, airports A1, airports A2, airlines B1 			\
	WHERE R1.Airline_ID = B1.Airline_ID\
	AND R1.Source_airport_ID = A1.Airport_ID\
	AND R1.Destination_airport_ID = A2.Airport_ID;				";


	//Prepare the the statement
	//(Database, String Query, -1 = use whole string ,stmt object, __Don't need to worry leave at 0__)
  	rc = sqlite3_prepare_v2(db, sql_stmt, -1, &stmt, 0);

	//Error message for the prepare statement 	
    if (rc != SQLITE_OK) {  
        fprintf(stderr, "Preparation failed: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
        return 1;
    }    

	//Evaluate the prepared statement using step
	//Check if the return value is a sqlite row if not finish
    while((rc = sqlite3_step(stmt)) == SQLITE_ROW) {
	   	int col;
		double temp = CalculateDistance(sqlite3_column_double(stmt, 3),sqlite3_column_double(stmt, 4),sqlite3_column_double(stmt, 5),sqlite3_column_double(stmt, 6));
		//======CHECK IF DISTANCE OVER 10000====//
		if (temp > 10000){
			//=====PARSE THE EQUIPMENT IF THERE IS MORE THAN 1======//
			token = strtok((char*)sqlite3_column_text(stmt, 7), " ");
			 while( token != NULL ) 
	  		 {
				//=====IF IT IS THE FIRST EQUIPMENT ENCOUNTER EVER ADD IT======//
				if(first == 0){
					listEquip = (char**)realloc(listEquip , size*sizeof(char*));
					listEquip[size-1] = strdup(token);
					++size;
					first = 1;
				} else {
					//=======CHECK TO SEE IF EQUIPMENT IS ALREADY PART OF LIST======//
					int i;
			 		for(i = 0; i<size-1; ++i){
						if(strcmp(listEquip[i], token) == 0){
							//=====SET ADD TO 1 IF THERE IS ALREADY A COPY=======//
							add = 1;
							break;
						}//END IF STATEMENT COMPARE TOKEN WITH EQUIPMENT
					}//END FOR LOOP CHECK	
					if (add == 0){
						//=====ADD TO EQUIPMENT LIST======//
						listEquip = (char**)realloc(listEquip , size*sizeof(char*));
						listEquip[size-1] = strdup(token);
						++size;
					}//END ADD IF ADD==0
					add = 0;
				}//END CHECK EQUIPMENT
				//=======CLEAR TOKEN======//
				token = strtok(NULL, " ");
	   		 }//END WHILE TOKEN != NULL
		}//END TEMP > 10000
    }//END CHECK ROW WHILE LOOP

	//Destory the prepared statement 
    sqlite3_finalize(stmt); //always finalize a statement

	//=======PRINT EQUIPMENT LIST AND FREE MEMORY AT END======//
	int j;
	for(j = 0; j < size-1; ++j){
		printf("%s\n", listEquip[j]);
	}//END FOR LOOP
	free(listEquip);
	

}//END MAIN
