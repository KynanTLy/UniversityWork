#include <time.h>
#include <stdio.h>
#include <sqlite3.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv){
	sqlite3 *db; //the database
	sqlite3_stmt *stmt; //the update statement
	sqlite3_stmt *stmt2; //the update statement

  	int rc;

  	if( argc!=3 ){
  		fprintf(stderr, "Usage: %s <database file> <length> \n", argv[0]);
    	return(1);
  	}

	//Error check to open database
  	rc = sqlite3_open(argv[1], &db);
  	if( rc ){
    	fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
    	sqlite3_close(db);
    	return(1);
  	}

  	/* Length / Total initialization */
  	int length = strtod(argv[2], NULL); // length of program
	double totalA = 0;
	double totalB = 0;

	for(int i = 0; i<100; ++i){
		clock_t begin;
		clock_t end; 
		//Choose a random x,y 
		double x = (double)(rand()%(999-length));
		double y = (double)(rand()%(999-length));

		//SQL query for rtree
		char *sql_stmt = sqlite3_mprintf(
	            		"SELECT id "\
						"FROM Map " \
						"WHERE minX >= %lf AND maxX <= %lf "\
						"AND minY >= %lf AND maxY <= %lf ",
						x, x+length, y, y+length
	    );

		//Prepare statement
		rc = sqlite3_prepare_v2(db, sql_stmt, -1, &stmt, 0);

		//Run rtree query 20 times
		for (int j = 0; j < 20; ++j){
			begin = clock();
			rc = sqlite3_exec(db, sql_stmt, 0 , 0 ,0 );
			end = clock();
			totalA += 1000 * ((double)(end - begin) / CLOCKS_PER_SEC);
		}//end for loop

		// Finalize statement
    		sqlite3_finalize(stmt);

		//SQL query for without rtree
		char *sql_stmt2 = sqlite3_mprintf(
	            		"SELECT id "\
						"FROM Map1 " \
						"WHERE minX >= %lf AND maxX <= %lf "\
						"AND minY >= %lf AND maxY <= %lf ",
						x, x+length, y, y+length
	    );
		
		//Prepare next statement
		rc = sqlite3_prepare_v2(db, sql_stmt2, -1, &stmt2, 0);

		//Run query for non-rtree 20 times
		for (int j = 0; j < 20; ++j){
			rc = sqlite3_prepare_v2(db, sql_stmt2, -1, &stmt2, 0);	
			begin = clock();
			rc = sqlite3_exec(db, sql_stmt2, 0 , 0 ,0 );
			end = clock();
			totalB +=  1000 * ((double)(end - begin) / CLOCKS_PER_SEC);
		}//end for loop
	
		// Finalize statement
    	sqlite3_finalize(stmt2);
		
	}//end for loop of 100 test cases

	printf("Parameter l: %d \n", length);
	printf("Average runtime with r-tree: %lf ms\n" , totalA/2000 );
	printf("Average runtime without r-tree: %lf ms\n" , totalB/2000 );
}//end main
