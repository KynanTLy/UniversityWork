#=======REUQIRED IMPORTS======#
import sqlite3
import sys

#=====Global=======#
#To store all the prefixs we encounter
Prefix_Dictionary = {}

#Variable to hold Rowbuilder
RowBuilder = []

#Variables to check proper formatInput
isSelect = False
isWhere = False
isWhereEnclose = False

#Holds the type of output we want
Output_type = []
Varibles_Name = []

#Holds each query database
Query_Insert = []
finishedQuery = []

#Valid operation for filter
Op =  ["=", "!=", ">", "<", ">=", "<="]

#Holds convert filter expression
FormatFilterSearch = []


#=======START OF PROGRAM=========#

#Type of Quotes
def main():
	if len(sys.argv) != 3:
		print ("Incorrect Input entered \n")
		print ("python3 q8.py <Database> <RDF Query Path>")
		sys.exit(1)	
		
	#Connect to database	
	database = sqlite3.connect(sys.argv[1])
	inputFile = open(sys.argv[2], 'r')

	#Load the file
	unformatInput = inputFile.read()

	#Format
	formatInput = initParse(unformatInput)

	#Close file	
	inputFile.close()
	
	#Generates the Query rows
	genQueryRow(formatInput)

	
	#Create the components of the sqlite3 query 
	createQueryPart()

	#Assemble the final version of the query with its components
	doneQuery =finalAssembly()


	#Execute query
	datacursor = database.cursor()
	datacursor.execute(doneQuery)
	result = datacursor.fetchall()

	#Print the results header
	for num in range(len(Output_type)):
		print("|========" + Output_type[num] + "========|" , end='')
	print('\n')

	#Print the entries
	for entry in result:
		for num in range(len(Output_type)):
			print("|  " + entry[num] + "  |" , end='')
		print('\n')

	#Close database
	database.close()

#Compute the Sqlite3 query to passed onto the database cursor
def finalAssembly():
	global finishedQuery, Output_type
	isFirst = True

	#For each where clause values we put that statement and the previous statement into a select from -q1- natural join -q2- query
	#The first time through we build the first query (there is nothing to join it to)
	for subquery in finishedQuery:
		if isFirst:
			query = builder(subquery)
			isFirst = False
		else:
			query = "(SELECT * \n FROM ( \n" + query + "  \nJOIN \n " + builder(subquery) + ") )"  

	#Build the select Statement around the body of sqlite statement created before (case of "*" and case of variables)
	if Output_type[0] != '*':
		first = True
		varlist = ""	
		for element in Output_type:			
			if(first):		
				varlist += element  
				first = False 
			else:
				varlist += ", " + element
		#Compute final version of the sqlite3 statement and add in filter if any
		query = "SELECT " + varlist + " \nFROM (\n " + query + ")"  + ifFilter()
	else:
		#Compute final version of the sqlite3 statement and add in filter if any
		query = "SELECT *\nFROM (\n " + query + " )" + ifFilter()
		
	#Return query
	return query + ";"

#Builds filters into the proper sqlite3 format
def ifFilter():
	isFirst = True
	returnPart = ""
	#Go through each member and create the proper format
	for member in FormatFilterSearch:
		if isFirst:
			returnPart = "\n WHERE " + member
			isFirst = False
		else:
			returnPart += "\n AND " + member
	return returnPart

#Builds the formats the elements into sqlite3 format
def builder(query):

	firsthalf = ""	
	secondhalf = ""

	#Check to see if we need a comma or not between elements
	for length in range(3):
		if "as" in query[length]:
			firsthalf += query[length] + " "
		else:
			secondhalf +=  query[length] + " " 

	#No varbles declared
	if(firsthalf == ""):
		firsthalf = " * "

	#Return proper format
	return "(SELECT DISTINCT " + firsthalf + " \nFROM Entry \nWHERE " + secondhalf + " )"
 
#Convert each section of where clause into correct Sqlite3 format
def createQueryPart():
	global Varibles_Name, finishedQuery, Query_Insert 

	#No var template
	query_template_noVar = [("subject = ", "predicate = ", "object = ")]

	#Possible Query templates for 1 variable
	query_template = [("subject as " , " predicate = " , " and object = "),
                      	("predicate as ", " subject = ", " and object = "),
                      	("object as ", "subject = ", " and predicate = ")
			]

	#Possible Query template for 2 variable
	query_multi_template = [("subject as ", ", predicate as ", "object = "),
                      	 ("subject as " , ", object as ", "predicate = "),
			 ("predicate as " , ", object as ", "subject = ")
			]

	#3 Var query template
	query_three_var_template = [("subject as ", "predicate as ", "object as ")]

	#Holds the index for template
	queryType = None

	#Which template we are going to use
	finalQueryType = []

	#Holds the combination to fill in the value given (from file) into the correct spot in the template
	#This is because where "subject" is located in template changes based of which template, therefore we need
	#to make sure they are correctly place
	combination = [] 

	for clause in Query_Insert:
		elementIndex = 0

		#Variable checks
		twoVar = False
		oneVar = False
		noVar = True	
		for element in clause:
			#For every where clause statement eval accordingly based off template and combination they were declared in
			if element in Varibles_Name:
				#If there are two variables
				if twoVar:
					if elementIndex == 3:
						finalQueryType = query_three_var_template[0]
						combination = [0, 1, 2]
					else:
						print("Invalid query variables declaration")
						sys.exit(1)
				#Atleast one var combination
				if oneVar:
					if queryType == 0 and elementIndex == 1:
						finalQueryType = query_multi_template[0]
						combination = [0, 1, 2]
					elif queryType == 0 and elementIndex == 2:
						finalQueryType = query_multi_template[1]
						combination = [0, 2, 1]
					elif queryType == 1 and elementIndex == 2:
						finalQueryType = query_multi_template[2]
						combination = [1, 2, 0]
					else:
						print("Invalid query variables declaration")
						sys.exit(1)
					oneVar = False
					twoVar = True
				#There are currently no variables in the clause
				else:
					if elementIndex == 0:
						queryType = 0
						combination = [0, 1, 2]
						finalQueryType = query_template[0]
					elif elementIndex == 1:
						queryType = 1
						combination = [1, 0, 2]
						finalQueryType = query_template[1]
					elif elementIndex == 2:
						queryType = 2	
						combination = [2, 0, 1]
						finalQueryType = query_template[2]
					else:
						print("Invalid query variables declaration")
						sys.exit(1)	
					oneVar = True
					noVar = False
			else:
				clause[elementIndex] = '"' + element + '"'			
			elementIndex += 1
	
		#If there are no variables
		if(noVar):
			finalQueryType = query_template_noVar[0]
			combination = [0, 1, 2]
				
		#Format the subquery parts based off the template version and in the correct combination
		finishedSubQueryPart = [(finalQueryType[0] + clause[combination[0]]) , (finalQueryType[1] + clause[combination[1]]) , (finalQueryType[2] + clause[combination[2]])]

		#Add the subquery to finishQuery list
		finishedQuery.append(finishedSubQueryPart)

#Eval the rows in the SPARQL query file
def genQueryRow(fileInput):
	global RowBuilder, isSelect, isWhere, Output_type 
	#Reset variables
	isSelect = False
	isWhere = False
	for element in fileInput:
		#Strip the row	
		element = element.strip().split()

		#If it is empty
		if (len(element) == 0):
			continue

		#We are now in select section
		if (element[0].upper() == "SELECT"):
			isSelect = True
			continue
		
		#We are now in where section
		if (element[0].upper() == "WHERE"):		
			isWhere = True
			isSelect = False			
			continue

		#Eval the select statements
		if(isSelect):
			for selectElement in element:
				#Find all the values in select clause and add them to Output type for later use
				if "*" in selectElement:
					Output_type.append("*")
				#Handles variables
				elif "?" in selectElement:
					Output_type.append(selectElement[1:])
					if(element not in Varibles_Name):
						Varibles_Name.append(selectElement[1:])
				else:
					print("Incorrect use of Select attribute")
					sys.exit(1)
		#Eval the where statement
		if (isWhere):
			#Check/handle for filter
			if element[0].upper() == "FILTER":	
				filterHandle(element[1:])
			#Eval regular where clause expression
			else:
				expressionHandle(element)

#Handles parsing of '{' '}' and '.' in relation to Where clause
def expressionHandle(element):
	global isWhereEnclose
	
	#Check enclosing
	if(element[0] == '{') and (not isWhereEnclose):
		isWhereEnclose  = True
		element =  element[1:]

	#After parsing previous ensure there is still something left
	if(len(element) == 0):
		return

	#Check for enclosing for last where clause (and check for '.' as well)
	if(element[-1] == "}"):
		if(len(element) == 1):
			return
		if(element[-2] != '.'):
			print("Where clause statement not properly finished")
			sys.exit(1)
		else:
			element = element[:-2]
	#If there is no '.' incorrect use
	elif element[-1] != '.':
		print("Where clause statement not properly finished")
		sys.exit(1)
	else:
		element = element[:-1]

	#After parsing previous ensure there is still something left
	if(len(element) == 0):
		return

	#Go format the elements to be used in a query
	assembleQuery(element)

#Formats the elements of a query clause (remove prefixes, '?', etc)
def assembleQuery(element):
	global Variables_Name
	elementIndex = 0	
	for part in element:
		#Check for variables
		if("?" in part):
			element[elementIndex] = part[1:]
			if(element not in Varibles_Name):
				Varibles_Name.append(part[1:])
		#Check for URL
		elif("<" in part):
			element[elementIndex] = removeEncase(part)
	
		#Check for Prefix
		elif(":" in part):
			element[elementIndex] = replacePrefix(part)
		elementIndex += 1
	
	#Add the formated elements 
	Query_Insert.append(element)	

#Handles filter parsing and assembly		
def filterHandle(element):
	global EnclosingType, FormatFilterSearch, Varibles_Name 

	filterElement = []

	#Format the filter to ensure they splite correctly
	for object in element:
		filterElement += object.replace("(", " ( ").replace(")", " ) ").replace(",", " , ").split()
	
	#Init variables
	isString = False
	variableName = None

	#Variables to check what type of operation and if there is one
	Operation = None
	isOp = False

	for object in filterElement:
		#Ignore Bracket
		if('(' in object) or (')' in object) or (',' in object):
			continue
		
		#Determine if it is a string
		if "regex" in object:
			isString = True;
			continue
	
		#Get Variable name
		if "?" in object:
			variableName = object[1:]
			#If not already declared add it
			if(element not in Varibles_Name):
				Varibles_Name.append(object[1:])
			continue			

		#If it is a string
		if isString:
			#Attemp to find the correct quotes
			OpenQuote = object.find('"')
			if (OpenQuote < 0):
				#Handle single quote
				OpenQuote = object.find("'")
				if(OpenQuote < 0):
					print("Improper use of opening quotation with string")
					sys.exit(1)
				EndQuote = object.find('"', OpenQuote + 1)
				if (EndQuote < 0):
					print("Improper use of closing quotation with string")
					sys.exit(1)
				if (object[OpenQuote + 1] == '^'):
					OpenQuote += 1
				#Append the sqlite3 formated filter for string
				FormatFilterSearch.append( variableName + " like " + '"' + "%" + object[OpenQuote + 1:EndQuote] + "%" + '"')
				break		
			else:
				#Handle double quote
				OpenQuote = object.find('"')
				if(OpenQuote < 0):
					print("Improper use of opening quotation with string")
					sys.exit(1)
				EndQuote = object.find('"', OpenQuote + 1)
				if (EndQuote < 0):
					print("Improper use of closing quotation with string")
					sys.exit(1)
				if (object[OpenQuote + 1] == '^'):
					OpenQuote += 1

				#Append the sqlite3 formated filter for string
				FormatFilterSearch.append( variableName + " like " + '"' + "%" + object[OpenQuote + 1:EndQuote] + "%" + '"')
				break

		#If it not a String it has to be a int or float 
		else:
			#Check if it is an operation
			if(object in Op):
				Operation  = object
				isOp = True
				continue
			elif isOp:
				#Check to see if the object on other side of operation is a number if not throw error
				try:
					complex(object)
				except ValueError:
					print("Invalid number given in filter")	
					sys.exit(1)
				#Append to list a formatted filter for sqlite3
				FormatFilterSearch.append( variableName + " " + Operation + " " + object)
				isOp = False
				break

#Handles prefixes and sperating SELECT and WHERE into their own line	
def initParse(fileLines):
	global Prefix_Dictionary, isSelect, isWhere, isEnd, RowBuilder

	#Ensure that there are spaces between certain words to make parsing easier
	formatFileLine = EnsureSpace(fileLines)
	fileindex = 0
	returnIndex = 0

	for row in formatFileLine:

		#Strip the row			
		row = row.strip()
		#Skip new lines and blank
		if len(row) == 0:
			fileindex += 1
			continue
		
		#Split the row into parts
		RowBuilder = row.split()

		if checkPrefix():
			fileindex += 1
			continue
		
		#Find out where select begin
		if (RowBuilder[0].upper() == "SELECT"):
			returnIndex = fileindex
			isSelect = True
			continue
		
		#Check to see if we see a "WHERE" right after a "SELECT"	
		if (isSelect):
			if (RowBuilder[0].upper() == "WHERE"):
				print("No Select Delcared")
				sys.exit(1)
			else:
				isSelect = False
		
		#Check for Where
		if (RowBuilder[0].upper() == "WHERE"):
			isWhere = True
			continue
		
		#Check to see if '{' is declared after the WHERE
		if (isWhere):
			if (RowBuilder[0] != '{'):
				print("Where statement not declared correctly")
				sys.exit(1)
			else:
				isWhere = False

	#Finish init parsing
	return formatFileLine[returnIndex:]
		
#Ensure that there are spaces between certain words to make it easier to parse later
def EnsureSpace(inputFile):
	returnFile = inputFile.replace("SELECT", "SELECT\n").replace("WHERE", "\nWHERE\n").replace("," , ", ").replace("FILTER", "FILTER ")		
	return returnFile.split('\n')
	
#Checks for prefixes		
def checkPrefix():
	global RowBuilder

	#Check for prefix declaration format
	if(RowBuilder[0] == "@prefix") or (RowBuilder[0] == "PREFIX"):
		Prefix_Dictionary[RowBuilder[1]] = removeEncase(RowBuilder[2])
		return True		
	else:
		return False

#Removes '<' and '>' from URL	
def removeEncase(URL):
	try:
		return URL[URL.index("<") + 1 : URL.index(">")]
	except:
		print("Invalid declaration of URL")
		sys.exit(1)

#Replace the prefix on the object
def replacePrefix(object):
	#If it is already a URL return
	if ("http://" in object):
		return object
	
	#Split on the ":"
	newObject = object.split(':')
	newObject[0] += ':'

	#Check to see if it is inside the dictionary
	if newObject[0] not in Prefix_Dictionary.keys():
		print("Prefix was not declare before use: ", newObject[0])
		sys.exit(1)

	return (Prefix_Dictionary[newObject[0]] + newObject[1])

#Runs the program
if __name__ == "__main__":
    main()

		
