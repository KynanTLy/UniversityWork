#include <stdio.h>
#include <sqlite3.h>
#include <math.h>

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

	//=====QUERY TO GET COMMERCIAL FLIGHTS=====//
  	char *sql_stmt = 
	"SELECT DISTINCT R1.Airline_ID, R1.Source_airport_ID, R1.Destination_airport_ID, A1.Latitude, A2.Latitude, A1.Longitude, A2.Longitude \
	FROM routes R1, airports A1, airports A2, airlines B1 \
	WHERE R1.Airline_ID = B1.Airline_ID \
	AND (B1.IATA != '\\N' \
	OR B1.IATA != ''\
	OR B1.Country != '\\N'\
	OR B1.Country != '' \
	OR B1.Callsign != '\\N' \
	OR B1.Callsign != ''\
	OR B1.ICAO != '\\N' \
	OR B1.ICAO != '')\
	AND R1.Destination_airport_ID = A2.Airport_ID \
	AND R1.Source_airport_ID = A1.Airport_ID "					
	;

	//=====CREATE THE NEW TABLE TO HOLD DISTANCE====//
	const char* NewTable =  "CREATE TABLE DISTANCE("  
         "Airline_ID INTEGER PRIMARY KEY," 
         "Source_Airport_ID INTEGER," 
         "Destination_Airport_ID INTEGER," 
         "Distance INTEGER);";

	//=====EXECUTE THE TABLE=====//
	 int table = sqlite3_exec(db, NewTable, NULL, NULL, NULL);


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

		double temp = CalculateDistance(sqlite3_column_double(stmt, 3),sqlite3_column_double(stmt, 4),sqlite3_column_double(stmt, 5),sqlite3_column_double(stmt, 6));

		char *addspl = sqlite3_mprintf("INSERT INTO DISTANCE (Airline_ID,Source_Airport_ID,Destination_Airport_ID,Distance) VALUES (%q, %q, %q,%lf);", sqlite3_column_text(stmt, 0),sqlite3_column_text(stmt, 1),sqlite3_column_text(stmt, 2),temp);
	
		sqlite3_exec(db, addspl, NULL, NULL, NULL);
  	}//end while

	//Destory the prepared statement 
    sqlite3_finalize(stmt); //always finalize a statement

	//=========QUERY TO GET TOP 10 DISTANCE==========//
 	const char* Q4Query = 
		"SELECT * \
		FROM DISTANCE D\
		ORDER BY D.Distance DESC\
		LIMIT 10;";
		
	rc = sqlite3_prepare_v2(db, Q4Query, -1, &stmt, 0);
	//Error message for the prepare statement 	
    if (rc != SQLITE_OK) {  
        fprintf(stderr, "Preparation failed: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
        return 1;
    }    

	 while((rc = sqlite3_step(stmt)) == SQLITE_ROW) {
        int col;
		//For the number of col the row has 
        for(col=0; col<sqlite3_column_count(stmt)-1; col++) {
          	printf("%s|", sqlite3_column_text(stmt, col));
        }
        printf("%s", sqlite3_column_text(stmt, col));
        printf("\n");
    }
	sqlite3_finalize(stmt); //always finalize a statement

	//=====DROP TABLE DISTANCE====//
	NewTable =  "drop table DISTANCE;";

	//=====EXECUTE DROP TABLE=====//
	 table = sqlite3_exec(db, NewTable, NULL, NULL, NULL);
}
