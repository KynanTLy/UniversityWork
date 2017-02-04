Student Name: Kynan Ly
//=========COLLABORATION==========//
Collaboration in Lab: TA/Prof in both Wednesday/Thursdat Lab with regards to Question: 2,4,6,7, creation of a new table to parse through in question 4, explaination of the forum post regarding recursion and how it relates to question 6-9
Collaboration with online sources:
 Class forums post
 strtok: https://www.tutorialspoint.com/c_standard_library/c_function_strtok.htm
 recursion: https://www.sqlite.org/lang_with.html
 Geolocation: https://en.wikipedia.org/wiki/Geographical_distance
Collaboration with classmates in Lab:
	Alanna McLafferty, Gaylord Mbuyi Konji, Imran Ali, and others(for simple question of clarification/how to use C/process of to tackle question)
	Group discussion with some of the listed classmate and TA at the lab in regards to question 4,6,7

CODE TAKEN FROM LAB0 SELECT.C AND UPDATE.C
	
//=========RUNTIME/OTHER NOTE===============//
Question 4,7 will take about ~2 min to run 
Openflight.db provide is empty of start without any tables/data imported

//=========HOW TO RUN .C FILES========//
IMPORTANT NOTE: ALL .C FILE ASSUME YOU HAVE A DATABASED CALLED:
	Openflight.db
IN THE DIRECTORY FOLDER ()

To run each entry run the command:
	gcc -g q<QUESTION>.c sqlite3.c -lpthread -ldl -lm
Where <QUESTION> is replaced with the question number. 

ex. to run question 7; 
	gcc -g q7.c sqlite3.c -lpthread -ldl -lm

ALL .C file results in an executable "a.out" and to run enter the command
	"./a.out"

NOTE: QUESTION 9 REQUIRES YOU TO RUN: 
	"./a.out <IATA_FFA>
Where <IATA_FAA> is the airport code you would like to enter. All other .C do not require addition input outside "./a.out"

//=========QUESTION 0=============//
Execute tables one at a time followed by the .mode / .import afterwards in sqlite
ASSUMPTION THAT:
	airports.dat, airlines.dat, routes.dat, sqlite3 (executable), sqlite3.c
	and sqlite3.h 
ARE IN THE DIRECTORY FOLDER 



