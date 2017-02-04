#include <stdio.h>
#include <sqlite3.h>

int main(int argc, char **argv){
	sqlite3 *db; //the database
	sqlite3_stmt *stmt; //the update statement

  	int rc;

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

	//=====QUERY TO GET HIGHEST AIRPORT=====//
  	char *sql_stmt= 
	"WITH RECURSIVE Q7(Source, Dest, ALTITUDE) as \
	(\
		SELECT Source_airport, Destination_airport, Altitude\
		FROM routes, airports \
		WHERE Source_airport  = 'YEG'\
		AND IATA_FAA = Destination_airport\
		UNION \
		SELECT Source, Destination_airport, A1.Altitude\
		FROM Q7 , routes, airports A1\
		WHERE Dest = Source_airport\
		AND Source != Destination_airport\
		AND Destination_airport = A1.IATA_FAA\
	)\
	select Source, Dest, ALTITUDE \
	FROM Q7 \
	WHERE Source != Dest\
	AND ALTITUDE IN (\
	SELECT MAX(ALTITUDE) \
	FROM Q7);	"		
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
