#include <stdio.h>
#include <sqlite3.h>

int main(int argc, char **argv){
	sqlite3 *db; //the database
	sqlite3_stmt *stmt; //the update statement

  	int rc;

  	if( argc!=1 ){
    	fprintf(stderr, "Usage: DO NOT REQUIRE USER INPUT \n");
    	return(1);
  	}
	
	
  	rc = sqlite3_open("Openflight.db", &db);
  	if( rc ){
    	fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
    	sqlite3_close(db);
    	return(1);
  	}

	//=====QUERY TO GET COMMERCIAL FLIGHTS=====//
	//=====PARSED WITH THOSE THAT ARE NOT RECIPRCAL=====//
  	char *sql_stmt= 
	"SELECT DISTINCT R1.Airline_ID, R1.Source_airport, R1.Destination_airport \
	FROM routes R1, airports A1, airports A2, airlines B1 \
	WHERE   R1.Airline_ID = B1.Airline_ID \
		AND (B1.IATA != '\\N' \
		OR B1.IATA != ''\
		OR B1.Country != '\\N' \
		OR B1.Country != ''\
		OR B1.Callsign != '\\N' \
		OR B1.Callsign != ''\
		OR B1.ICAO != '\\N' \
		OR B1.ICAO != '')\
		AND R1.Destination_airport_ID = A2.Airport_ID \
		AND R1.Source_airport_ID = A1.Airport_ID \
		AND A1.Country != A2.Country\
	EXCEPT\
	SELECT DISTINCT R1.Airline_ID, R1.Destination_airport, R1.Source_airport\
	FROM routes R1, airports A1, airports A2, airlines B1\
	WHERE R1.Source_airport_ID = A1.Airport_ID \
	AND R1.Destination_airport_ID = A2.Airport_ID \
	AND A2.Airport_ID != R1.Source_airport_ID\
	AND A1.Airport_ID != R1.Destination_airport_ID\
	AND B1.Airline_ID = R1.Airline_ID 			"		
	;


	//Prepare the the statement
	//(Database, String Query, -1 = use whole string ,stmt object, __Don't need to worry leave at 0__)
  	rc = sqlite3_prepare_v2(db, sql_stmt, -1, &stmt, 0);

	//Error message for the prepare statement 	
    if (rc != SQLITE_OK) {  
        fprintf(stderr, "Preparation failed: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
        return 1;
    }//END IF    

	//Evaluate the prepared statement using step
	//Check if the return value is a sqlite row if not finish

	//=====FILL NEW TABLE DISTANCE WITH VALUES=====//
    while((rc = sqlite3_step(stmt)) == SQLITE_ROW) {

		int col;
		//For the number of col the row has 
        for(col=0; col<sqlite3_column_count(stmt)-1; col++) {
          	printf("%s|", sqlite3_column_text(stmt, col));
        }
        printf("%s", sqlite3_column_text(stmt, col));
        printf("\n");
  	}//end while

	//Destory the prepared statement 
    sqlite3_finalize(stmt); //always finalize a statement
	
}//END MAIN
