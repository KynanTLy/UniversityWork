/*
 * Takes as parameters the coordinates of a bounding rectangle (x1,y1) (x2, y2)
 * and POI class c and prints the list of objects within that rectangle and
 * of that class.
 */


#include <stdio.h>
#include <sqlite3.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv){
	sqlite3 *db; //the database
	sqlite3_stmt *stmt; //the update statement

  	int rc;

  	if( argc!=7 ){
  		fprintf(stderr, "Usage: %s <database file> <x1> <y1> "
  				"<x2> <y2> <class>\n", argv[0]);
    	return(1);
  	}

  	rc = sqlite3_open(argv[1], &db);
  	if( rc ){
    	fprintf(stderr, "Can't open database: %s\n", sqlite3_errmsg(db));
    	sqlite3_close(db);
    	return(1);
  	}

  	/* Coordinates of the first square */
  	double x1 = strtod(argv[2], NULL); // x1
  	double y1 = strtod(argv[3], NULL); // y1

  	/* Coordinates of the second square */
  	double x2 = strtod(argv[4], NULL); // x2
  	double y2 = strtod(argv[5], NULL); // y2

  	/* The class */
  	char *class = argv[6];


  	/* Determine min and max of two longitude and latitude values */
  	double minX = (x1 < x2) ? x1 : x2;
  	double maxX = (x1 > x2) ? x1 : x2;
  	double minY = (y1 < y2) ? y1 : y2;
  	double maxY = (y1 >= y2) ? y1 : y2;

  	printf("%lf %lf %lf %lf %s\n", minX, maxX, minY, maxY, class);

  	//SQL Query 
	char *sql_stmt = sqlite3_mprintf(
	            		"SELECT id "\
						"FROM Map " \
						"WHERE minX >= %lf AND maxX <= %lf "\
						"AND minY >= %lf AND maxY <= %lf " \
						"INTERSECT " \
						"SELECT id " \
						"FROM poi_tag " \
						"WHERE key = 'class' "\
						"AND value = '%s' ",
						minX, maxX, minY, maxY, class
	);


  	// Prepare statement
  	rc = sqlite3_prepare_v2(db, sql_stmt, -1, &stmt, 0);

    if (rc != SQLITE_OK) {
        fprintf(stderr, "Preparation failed: %s\n", sqlite3_errmsg(db));
        sqlite3_close(db);
        return 1;
    }//end if

    // Print results on screen
    while((rc = sqlite3_step(stmt)) == SQLITE_ROW) {
        int col;
        for(col=0; col<sqlite3_column_count(stmt)-1; col++) {
          printf("%s|", sqlite3_column_text(stmt, col));
        }
        printf("%s", sqlite3_column_text(stmt, col));
        printf("\n");
    }//end while loop

    // Finalize statement
    sqlite3_finalize(stmt);
}
