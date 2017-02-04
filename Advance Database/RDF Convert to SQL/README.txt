//=======GROUP========//
Kynan Ly
Gaylord Mbuyi Konji

//========COLLAB===========//
	Other classmates in 391 lab
	TA, eclass, forums, Paper's presented in lab and notes
	
	Discussed with TA and other present in lab: 
		How to handle parsing in q8 as well as the format for final query in q9 
		For every where clause statement we join the result with the next one recursively to parse
		through results and at the outer layer we apply the filters. This was done to ensure correct
		variables are passed up correctly

	Discussed how to format the sql query (use an other select to handle variables, from (list of subqueries 
	that are created from where clauses) and filters should be handled in the where of outer select)) during lab
	(Imran Ali)
	

//=======ADDITIONAL FILES======//
The files that are needed in addition are (included in package): 
	data.txt (data set used), qtest.txt(holds an example query) , assignment2.db (preloaded see Note below)
	

//========Question 8,9 =========//
To run the question 8 and 9 run the following command in terminal
	python3 q<question_num>.py <database_name>.db <query_or_database_file>.txt 

Where question_num is the question number
database_name is the database name
query_or_database_file is the file name

//==========NOTE==============// 
	In the handin folder there exist a "Data.db" which is preloaded with the table created from data.txt 
	using the table format explained in q6

	There is also a qtest.txt that contains a SPARQL query file to test the database 

//=========REFRENCES============//
Sqlite3			https://docs.python.org/2/library/sqlite3.html
Dictionary 		https://docs.python.org/3/tutorial/datastructures.html
Check python argument 	http://stackoverflow.com/questions/4188467/how-to-check-if-an-argument-from-commandline-has-been-set
Read lines from file 	https://docs.python.org/3.3/tutorial/inputoutput.html
Replacing String 	http://stackoverflow.com/questions/9452108/how-to-use-string-replace-in-python-3-x
Striping Strings 	https://www.tutorialspoint.com/python/string_strip.htm
Python regex 		https://docs.python.org/2/howto/regex.html , https://www.tutorialspoint.com/python/python_reg_expressions.htm
Looping in list 	http://stackoverflow.com/questions/4843158/check-if-a-python-list-item-contains-a-string-inside-another-string
SQL join		https://www.tutorialspoint.com/sqlite/sqlite_using_joins.htm
Using a select in from 	http://stackoverflow.com/questions/15961349/using-select-result-in-another-select

//=========ASSUMPTION============//
#1 literal for strings can only be contained by "" or '', they may include '^' after to the quotation
#2 No more than one logical comparasion can be used at a time in filter (a < b < c) not allowed
#3 There is a space between the last word and the '.' in the where clause ?place dbo:country schema:City . <- there is a space between City and .
#4 All instance in a where clause ends with a '.'
#5 No use of "AND" or "OR" and other logic gates
#6 When using regex it is assume that the result will contain the "string" 
#	A filter with string "Capital_Region" would return answers such as "Edmonton_Capital_Region" and "The_Edmonton_Capital_Region"
#7 No language tag are used in the query
#8 regex filters only take the form FILTER (regex(?v, "<text>")) where there is no additional string
#9 Valid mathical operations listed before in 'Op'




