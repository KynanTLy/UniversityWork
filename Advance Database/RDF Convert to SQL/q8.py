#======Import required libraries========#
import sqlite3
import sys
import re

#=========Globals============#
#To store all the prefixs we encounter
Prefix_Dictionary = {}

#Holds each row of data to be inserted into database
Data_Insert = []

#Variable to hold multi line
RowBuilder = []

#Variables that hold subject, predicate, object, object_type
Subject = None
Predicate = None
Object = None
ObjectType = None

#Variable to hold starting and ending index for RowBuilder
Start_I = -1
End_I = -1

#Holds the previous end token of previous line (assume to be '.' at start)
PreEnd = '.'

#Varibles for combining literals, multi line
Combining = False
Complete = False
BuildingMulti = False
EnclosingType = ""

#Variables to hold base
Base = None

#List of tokens  that end a line in the RDF file and type of quote
EndLine = ['.' , ',' , ';' ]
Quotes = ['"' , "'''", "'"]

#=========End Globals============#

def main():
	if len(sys.argv) != 3:
		print ("Incorrect Input entered \n")
		print ("python3 q8.py <Database> <RDF Data Path>")
		sys.exit()

	#Open database and read file
	database = sqlite3.connect(sys.argv[1])
	inputFile = open(sys.argv[2], 'r')
	fileLines = inputFile.readlines()

	#Close file
	inputFile.close()

	#Generates the Data rows
	genDataRow(fileLines)

	#Insert to database
	cursor = database.cursor()
	cursor.executemany('INSERT INTO Entry VALUES (?,?,?,?)', Data_Insert)
	database.commit()
	database.close()

#Generate query	
def genDataRow(fileLines):
	global RowBuilder

	for row in fileLines:
		#Strip the row
		row = row.strip()

		#Skip new lines
		if len(row) == 0:
			continue

		#Ignore Comments
		if '#' in row:
			row = checkComment(row)
			#Check to see if there is anything left after we remove comments from line
			if len(row) == 0:
				continue

		#Check for multi line
		#Assume that if it does not end in EndLine it is part of multi line
		#and proceed to next line
		#Error checking will be handle later
		if checkMultiLine(row):
			continue

		#Check to see if it a prefix declaration
		if checkPrefix():
			#Clear RowBuilder
			RowBuilder = []
			continue

		#Assemble literals
		assemble()

		#Constructs data entry and format
		constructDataEntry()

		#Clear Rowbuilder
		RowBuilder = []

#Insert triple
def constructDataEntry():
	global PreEnd, Subject, Predicate, Object, ObjectType


	RBLength = len(RowBuilder)

	#Case 1: all values are given
	if(RBLength == 4):
		#Assign the corresponding values
		Subject = formating(RowBuilder[0])
		Predicate = formating(RowBuilder[1])
		Object, ObjectType = findType(RowBuilder[2])

	#Case 2: Subject has been declared
	elif((RBLength == 3) and (PreEnd == ';')):
		#Check to see if a subject has been declared before
		if(Subject):
			Predicate = formating(RowBuilder[0])
			Object, ObjectType = findType(RowBuilder[1])
		else:
			#Error checking
			print("Error: Predicate without Subject")
			sys.exit(1)

	#Case 3: Subject and Predicate been declared
	elif ((RBLength == 2) and (PreEnd == ',')):
		if (Subject and Predicate):
			Object, ObjectType = findType(RowBuilder[0])
		else:
			#Error checking
			print("Error: Object with no Predicate or Subject")
			sys.exit(1)
	#Case 4: There was a error in file
	else:
		print("Error: Too many or too little arguments found")
		sys.exit(1)

	#Remove language tag
	Object = LanguageTag(Object)

	#Assign the token at the end of the line
	PreEnd = RowBuilder[-1]
	if Object:
		#If there is data to store, store it
		Data_Insert.append((Subject, Predicate, formating(Object), ObjectType))

#Find the type of the object as well as format it 
#Returns the object and type back
def findType(object):
	#Set default return values
	returnObject = object
	returnType = "string"
	try:
		#If it is a decminal value to assign it to a float
		if '.' in object:
			tempFloat = float(object)
			returnType = "float"
		#Try to assign it to a integer
		else:
			tempInt = int(object)
			returnType = "int"
	#If not either int or float it is a string
	except ValueError:
		#Check for website link or other types
		if ("http://" in object) and ("^^" not in object):
			object_type = "string"
		elif "^^" in object:
			objectList = object.split("^^")
			returnObject = objectList[0]
			returnType = replacePrefix(objectList[1])

	return returnObject, returnType

#Determine if we need to format the element
def formating(element):
	if ":" in element:
		return replacePrefix(element)
	else:
		return element

#Look up global dictionary for prefix tag
def replacePrefix(object):
	#If there was improper prefix declaration
	if ":" not in object:
		print("Invalid character found with ", object)
		sys.exit(1)

	#If it is a direct link return
	if ("http://" in object):
		return object

	#Split on the ":"
	newObject = object.split(':')
	newObject[0] += ':'

	#If blank node return
	if "_" in newObject[0]:
		return object.strip()

	#Check to see if it is inside the dictionary
	if newObject[0] not in Prefix_Dictionary.keys():
		print("Prefix was not declare before use: ", newObject[0])
		sys.exit(1)

	return (Prefix_Dictionary[newObject[0]] + newObject[1])

#Check langauge tag using regex 
def LanguageTag(object):
	#Remove @en tag if there is one
	tag = re.search("@[a-z]*", object)
	if(tag):
		#If the tag is not @en
		if tag.group() != "@en":
			return None
		else:
			return object.replace("@en", '')
	return object

#Assembles the part of the insert statement
def assemble():
	global Start_I, End_I, Complete, Combining, Base
	#Init Varibles
	builderIndex = 0;

	#Reset globals
	Start_I = -1
	End_I = -1
	Complete = False;
	Combining = False;

	for element in RowBuilder:
		#Format URL if it has one
		if ('<' in element) and ('>' in element):
			#If there is no website we look for a base
			if "http://" not in element:
				if (Base):
					RowBuilder[builderIndex] = Base + formatURL(element)
				else:
					#Error checking for base
					print("Error: No declaration of Base")
					sys.exit(1)
			else:
				#format URL
				RowBuilder[builderIndex] = formatURL(element)

		#Check for Literal
		if any(part in element for part in Quotes):
			formatLiteral(element, builderIndex)

		#Check to see if we have completed a literal
		if (Complete):
			combineIndex()

		builderIndex += 1

#Complete a literal and there we need to now combine it together
def combineIndex():
	global RowBuilder, Complete
	#Join elements between index
	temp = " ".join(RowBuilder[Start_I:(End_I+1)])

	#Format the list, and insert the new combined literal
	RowBuilder = RowBuilder[:Start_I] + RowBuilder[(End_I+1):]
	Complete = False
	RowBuilder.insert(Start_I, temp)

#Format if it is multi line
def formatLiteral(element, builderIndex):
	global Combining, EnclosingType, Start_I, End_I, Complete, BuildingMulti

	#Attempt to find all type of quote
	QuoteIndex = [element.find('"'), element.find("'''"), element.find("'")]
	IndexNum = 0

	#Set all value to inf if we cannot find that quote type
	for elementInQuoteIndex in QuoteIndex:
		if elementInQuoteIndex < 0:
			QuoteIndex[IndexNum] = float("inf")
		IndexNum += 1

	#Determine which type of quote arrived first to determine encasing
	if(Combining == False):
		if(QuoteIndex[0] < QuoteIndex[1]) and (QuoteIndex[0] < QuoteIndex[2]):
			EnclosingType = '"'
		elif (QuoteIndex[1] < QuoteIndex[0]) and (QuoteIndex[1] < QuoteIndex[2]):
			EnclosingType = "'''"
		else:
			EnclosingType = "'"
		#Assign the start to be builderIndex
		Start_I = builderIndex
		Combining = True

	#Check to see if we are in the middle of a multi line and the element is the end
	#of it. If it is then we declare that as the end
	elif (Combining == True) and (EnclosingType in element):
		End_I = builderIndex
		EnclosingType = ""
		Combining = False
		Complete = True

#Formats URL
def formatURL(element):
	#Check for "^^"
	if("^^" in element):
		return (element[:(element.find('<'))] + removeEncase(element))
	else:
		return removeEncase(element)

#Remove "< >" from URL
def removeEncase(URL):
	return URL[URL.index("<") + 1 : URL.index(">")]

#Checks for multi line and combine them if needed
def checkMultiLine(row):
	global RowBuilder, BuildingMulti
	rowTokens = row.split()
	#If not end correctly assume that it is part of multi line

	if rowTokens[-1] not in EndLine:
		RowBuilder += rowTokens
		BuildingMulti = True
		return True;
	#It end correctly so return
	else:
		#Check to see if part of building multi line
		if(not BuildingMulti):
			RowBuilder = rowTokens
		else:
			RowBuilder += rowTokens
		return False

#Checks for comments and removes them
def checkComment(row):
	index_of_Hash = row.find('#')
	#Parsing the comment out
	while index_of_Hash != -1:
		left_of_Hash = row[:index_of_Hash]
		right_of_Hash = row[index_of_Hash:]

		#Check to see if "#" appears in a URL
		if (('>' not in right_of_Hash) or ('<' not in left_of_Hash)) and (index_of_Hash != -1):
			row = row[:index_of_Hash]

		index_of_Hash = row.find('#', index_of_Hash+1)

	return row

#Check to see if prefix/base exist
def checkPrefix():
	global RowBuilder, Base
	#Check to see if both method to declare refix
	if (RowBuilder[0] == "@prefix") or (RowBuilder[0] == "PREFIX"):
		#Add to dictionary the prefix and URL with "< >" removed
		Prefix_Dictionary[RowBuilder[1]] = removeEncase(RowBuilder[2])
		return True;
	#Handles Base
	elif (RowBuilder[0] == "@base") or (RowBuilder[0] == "BASE") :
		#There can only be one base
		Base = removeEncase(RowBuilder[1])
		return True;
	else:
		return False;

#Runs the program
if __name__ == "__main__":
    main()


