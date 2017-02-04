"""
This file contains the execution of the Driver License Registration
application. Any funcions pertaining to said application will
be contained within this file.

Driver Licence Registration

This component is used to record the information needed to issuing 
a drive licence, including the personal information and a picture 
for the driver. You may assume that all the image files are stored 
in a local disk system.

https://code.google.com/p/cx-oracle-demos/source/browse/trunk/blob.py?r=11
"""

# === External Definitions
import cx_Oracle
import sys
from datetime import datetime #Used to verify date format

def DL(cursor, connection):

	# === Dictionary Definition
	choices = {'1':1, '2':2}

	description()
	soc = 0
	while(True):	# === Application Loop
		print("1. Register Driver License")
		print("2. Return to Main Menu")

		selection = input("[Please enter 1 or 2] ")
		choice = 0 # Reset Value to prevent reselection

		try:
			choice = choices[selection]
			
		except KeyError:
			print("Invalid Selection, please try again")
			print("")

		if choice == 1:
			while(True):
				print("")
				print("Please input the following as correctly as possible")
				print("")

				while(True):
					ln = input("Enter License Number: ")
					while len(ln) < 15:
						ln = ln + ' '
					cursor.prepare("SELECT d.licence_no FROM drive_licence d WHERE d.licence_no = :INPUT_DRI")
					cursor.execute(None, {'INPUT_DRI': ln})
					license = cursor.fetchall()
					if license == []:
						break
					print("This license number exists, please try again")

				while(True): # === Enter new person
					sin = input("Enter Social Insurance Number: ")
					while len(sin) < 15:
						sin = sin + ' '
					cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_SIN")
					cursor.execute(None, {'INPUT_SIN': sin})
					social = cursor.fetchall()
		
					cursor.prepare("SELECT d.sin FROM drive_licence d WHERE d.sin = :INPUT_SIN")
					cursor.execute(None, {'INPUT_SIN':sin })
					match = cursor.fetchall()
					if social == []:
						print("Sin not found, would you like to register this person?")
						selection = input("[1. Yes, 2. No] ")
						soc = 0
						try:
							soc = choices[selection]
						except KeyError:
							print("Invalid Selection, please try again")
							print("")
						if soc == 1:
							nm = input("Enter Name: ")
							while(True):
								ht = input("Enter Height: ")
								try: 
									ht = int(ht)
									break
								except ValueError:
									print("Insert valid height")
							while(True):
								wt = input("Enter Weight: ")
								try: 
									wt = int(wt)
									break
								except ValueError:
									print("Insert valid weight")
							ec = input("Enter Eyecolour: ")
							hc = input("Enter Haircolour: ")
							ad = input("Enter Address: ")
							while(True):
								gr = input("Enter Gender: ")
								gr = gr.lower()
								if gr not in ('m' ,'f'):
									print("Please enter 'm' or 'f'")
								else:
									break 
							while(True): # === Check date repeatedly until Format is correct.
								bd = input("Enter Date [yyyy-mm-dd]: ")
								try:
								# If try succeeds then date is in correct format
									datetime.strptime(bd, '%Y-%m-%d')
									break
								except ValueError:
									print("Incorrect format/Value, should be [yyyy-mm-dd]")
	

						# === Confirmation
							print("Is everything correct?")
							print("---------------------------------------")
							print("Field" + '	' + '	' + '	' + "Values")
							print("SIN: " + '	' + '	' + '	' + sin)
							print("Name: " + '	' + '	' + '	' + nm)
							print("Height: " + '	' + '	' + str(ht))
							print("Weight: " + '	' + '	' + str(wt))
							print("Eyecolour: " + '	' + '	' + ec)
							print("Haircolour: " + '	' + '	'+ hc)
							print("Address: " + '	' + '	' + ad)
							print("Gender: " + '	' + '	' + gr)
							print("Birthday: " + '	' + '	' + bd)
							print("---------------------------------------")

							selection = input("[1. Yes, 2. No] ")
							confirm = 0 # Reset Value to prevent reselection

							try:
								confirm = choices[selection]
							except KeyError:
								print("Invalid Selection, please try again")
								print("")

							if confirm == 1:
							#Before you would put the table here but i have coded it such
							#that you can make all the tables at the end
								break
					
					elif match != []:
						print("This person already has a license.")
						print("Returning to Selection")
						DL(cursor, connection)
						return	#After the new apllication returns then return in the mother application
					else: break
					
				cl = input("Enter Driver Class: ")
				
				# Insert photo Stuff here
				# linku: https://eclass.srv.ualberta.ca/pluginfile.php/1930631/mod_assign/intro/InsertLobsOracle.py
				# Load image into memory from local file 
				# (Assumes a file by this name exists in the directory you are running from)

				while(True): # Make sure image is valid
					imagePath = input("Enter Image Name wih the Extension (.jpg): ")
					
					try:
						f_image  = open(imagePath,'rb')
						image  = f_image.read() #Insert image as the variable for photo 
						
						break
					except IOError:
						print("File not found, remember to add the .jpg extension")

						while(True): # return?
							print("Try again or return to main menu?")
							selection = input("[1. Retry, 2. Main Menu] ")
							retry = 0 # Reset Value to prevent reselection

							try:
								retry = choices[selection]
								if retry == 2: return
								break
							except KeyError:
								print("Invalid Selection, please try again")
								print("")

						

				while(True): # === Check date repeatedly until Format is correct.
					ss = input("Enter Issuing Date [yyyy-mm-dd]: ")
					try:
					# If try succeeds then date is in correct format
						datetime.strptime(ss, '%Y-%m-%d')
						break
					except ValueError:
						print("Incorrect format/Value, should be [yyyy-mm-dd]")

				while(True): # === Check date repeatedly until Format is correct.
					ed = input("Enter Expiring Date [yyyy-mm-dd]: ")
					try:
					# If try succeeds then date is in correct format
						datetime.strptime(ed, '%Y-%m-%d')
						break
					except ValueError:
						print("Incorrect format/Value, should be [yyyy-mm-dd]")

								# === Confirmation
				print("Is everything correct?")
				print("---------------------------------------")
				print("Fields" + '	' + '	' + '	' + '	'+ "Values")
				print("License Number: " + '	' + '	' + ln)
				print("Social Insurance Number: " + '	' + sin)
				print("Driver Class: " + '	' + '	' + '	' + cl)
				print("Issuing Date: " + '	' + '	' + '	' + ss)
				print("Expiry Date: " + '	' + '	' + '	' + ed)
				print("---------------------------------------")

				selection = input("[1. Yes, 2. No] ")
				confirm = 0 # Reset Value to prevent reselection

				try:
					confirm = choices[selection]
				except KeyError:
					print("Invalid Selection, please try again")
					print("")

				if confirm == 1:  
				
					if soc == 1:
						cursor.execute( "INSERT INTO people (sin,name,height,weight,eyecolor,haircolor,addr,gender,birthday)VALUES(:1,:2,:3,:4,:5,:6,:7,:8,to_date(:9, 'YYYY-MM-DD'))",{'1':sin,'2':nm,'3':ht,'4':wt,'5':ec,'6':hc,'7':ad,'8':gr,'9':bd})
					cursor.setinputsizes(image=cx_Oracle.BLOB)
					drive = "INSERT INTO drive_licence (licence_no, sin, class, photo, issuing_date, expiring_date)VALUES (:1,:2,:3,:image,to_date(:5, 'YYYY-MM-DD'),to_date(:6, 'YYYY-MM-DD'))"
					cursor.execute(drive , {'1':ln,'2':sin, '3':cl , 'image':image, '5':ss, '6':ed})
					connection.commit()
					f_image.close()
					break

		if choice == 2: return #Return to main menu

def description():
	# === Introduction Messages
	print("")
	print("---------------------------------------")
	print("Welcome to Driver License Registration.")
	print("---------------------------------------")
	print("")
	return
