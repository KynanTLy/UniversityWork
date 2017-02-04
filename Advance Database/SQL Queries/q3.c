#include <stdio.h>
#include <sqlite3.h>

int main(int argc, char **argv){
	sqlite3 *db; //the database
	sqlite3_stmt *stmt; //the update statement

  	int rc;

  	if( argc!=1 ){
    	fprintf(stderr, "Usage: DOES NOT REQUIRE USER INPUT \n");
    	return(1);
  	}//END IF 
	
	
  	rc = sqlite3_open("Openflight.db", &db);
  	if( rc ){
    	fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
    	sqlite3_close(db);
    	return(1);
  	}//END IF

	//=====QUERY TO GET TOP 10 COUNTRY FLYING TO/FROM CANADA=====//
  	char *sql_stmt= 
	"SELECT B1.Country, Count(DISTINCT B1.Airline_ID)\
	FROM routes R1, airports A1, airports A2, airlines B1\
	WHERE R1.Source_airport_ID = A1.Airport_ID\
	AND R1.Destination_airport_ID = A2.Airport_ID\
	AND R1.Airline_ID = B1.Airline_ID\
	AND ((A1.Country = 'Canada') OR (A2.Country = 'Canada'))\
	GROUP BY B1.Country\
	ORDER BY Count(DISTINCT B1.Airline_ID) DESC LIMIT 10"				
	;


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
