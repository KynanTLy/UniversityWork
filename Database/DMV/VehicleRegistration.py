"""
This file contains the execution of the Vehicle Registration
application. Any funcions pertaining to said application will
be contained within this file.

New Vehicle Registration

This component is used to register a new vehicle by an auto 
registration officer. By a new vehicle, we mean a vehicle that 
has not been registered in the database. The component shall 
allow an officer to enter the detailed information about the 
vehicle and personal information about its new owners, if it 
is not in the database. You may assume that all the information 
about vehicle types has been loaded in the initial database.
"""
# === External Imports
import cx_Oracle
import sys
from datetime import datetime #Used to verify date format

def VR(cursor, connection):


	# === Introduction Messages
	print("")
	print("--------------------------------")
	print("Welcome to vehicle registration.")
	print("--------------------------------")
	print("")

	choices = {'1':1, '2':2}

	while(True): # === Application Loop
					
		print("1. Register a new vehicle")
		print("2. Return to Main Menu")

		selection = input("[Please enter 1 or 2] ")
		choice = 0 # Reset Value to prevent reselection
		newOwn = 0 # Reset Values
		newSec = 0

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
				sn = input("Enter Vehicle Serial Number: ")
				while len(sn) < 15:
					sn = sn + ' '
				cursor.prepare("SELECT v.serial_no FROM vehicle v WHERE v.serial_no = :INPUT_SERIAL")
				cursor.execute(None, {'INPUT_SERIAL': sn})
				vehiclefound = cursor.fetchall()
						
				if vehiclefound != []:
					print("This vehicle is already in the system.")
					print("You will be returned to the selection.")
					break

				mn = input("Enter Car Manufacturer: ")
				mk = input("Enter Car Model/Make: ")
				while(True):
					yr = input("Enter Car Year: ")
					try:		
						if len(yr) > 4:
							print("Please enter Valid Year [xxxx] ")
						else: 
							yr = int(yr)							
							break
					except ValueError:
						print("Please enter Valid Year [xxxx] ")
				cr = input("Enter Car Colour: ")

				while(True): 
					ti = int(input("Enter Type ID: "))
					cursor.prepare("SELECT v.type_id FROM vehicle_type v WHERE v.type_id = :INPUT_VID")
					cursor.execute(None, {'INPUT_VID': ti})
					vid = cursor.fetchall()
					if vid != []:
						break
					print("Vehicle Type is not found, please try again")		
	
				while(True): 
					ow = input("Enter Owner ID: ")
					while len(ow) < 15:
						ow = ow + ' '
					cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_SIN")
					cursor.execute(None, {'INPUT_SIN': ow})
					person = cursor.fetchall()
					if person == []:
						print("Sin not found, would you like to register this person?")
						selection = input("[1. Yes, 2. No]")
						exists = 0
						try:
							exists = choices[selection]
						except KeyError:
							print("Invalid Selection, please try again")
							print("")
						if exists == 1:
						
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
							print("SIN: " + '	' + '	' + '	' + ow)
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
								newOwn = 1		
								break			
					else: break

				while(True):
					selection = input("Would you like to enter a Secondary Owner? [1. Yes, 2. No]")
					would = 0
					try:
						would = choices[selection]
						break
					except KeyError:
						print("Invalid Selection, please try again")
						print("")


				while(True):
					if would != 1:
						break
					sd = input("Enter Second Owner ID: ")
					while len(sd) < 15:
						sd = sd + ' '
					cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_SIN")
					cursor.execute(None, {'INPUT_SIN': sd})
					person = cursor.fetchall()
					if person == []:
						print("Sin not found, would you like to register this person?")
						selection = input("[1. Yes, 2. No]")
						exists = 0
						try:
							exists = choices[selection]
						except KeyError:
							print("Invalid Selection, please try again")
							print("")
						if exists == 1:
	
							nm2 = input("Enter Name: ")
							while(True):
								ht2 = input("Enter Height: ")
								try: 
									ht2 = int(ht2)
									break
								except ValueError:
									print("Insert valid height")
							while(True):
								wt2 = input("Enter Weight: ")
								try: 
									wt2 = int(wt2)
									break
								except ValueError:
									print("Insert valid weight")

							ec2 = input("Enter Eyecolour: ")
							hc2 = input("Enter Haircolour: ")
							ad2 = input("Enter Address: ")
							while(True):
								gr2 = input("Enter Gender: ")
								gr2 = gr2.lower()
								if gr2 not in ('m' ,'f'):
									print("Please enter 'm' or 'f'")
								else:
									break

							while(True): # === Check date repeatedly until Format is correct.
								bd2 = input("Enter Date [yyyy-mm-dd]: ")
								try:
								# If try succeeds then date is in correct format
									datetime.strptime(bd2, '%Y-%m-%d')
									break
								except ValueError:
									print("Incorrect format/Value, should be [yyyy-mm-dd]")
						

							# === Confirmation
							print("Is everything correct?")
							print("---------------------------------------")
							print("Field" + '	' + '	' + '	' + "Values")
							print("SIN: " + '	' + '	' + '	' + sd)
							print("Name: " + '	' + '	' + '	' + nm2)
							print("Height: " + '	' + '	' +  str(ht2))
							print("Weight: " + '	' + '	' +  str(wt2))
							print("Eyecolour: " + '	' + '	' + ec2)
							print("Haircolour: " + '	' + '	'+ hc2)
							print("Address: " + '	' + '	' + ad2)
							print("Gender: " + '	' + '	' + gr2)
							print("Birthday: " + '	' + '	' + bd2)
							print("---------------------------------------")

							selection = input("[1. Yes, 2. No] ")
							confirm = 0 # Reset Value to prevent reselection

							try:
								confirm = choices[selection]
							except KeyError:
								print("Invalid Selection, please try again")
								print("")

							if confirm == 1:
								newSec = 1 
								break
					else: break

				# === Confirmation
				print("Is everything correct?")
				print("---------------------------------------")
				print("Fields" + '	' + '	' + '	' + "Values")
				print("Serial Number: " + '	' + '	' + sn)
				print("Car Manufacturer: " + '	' + mn)
				print("Car Model/Make: " + '	' + mk)
				print("Car Year: " + '	' + '	' + str(yr))
				print("Car Colour: " + '	' + '	' + cr)
				print("Type ID: " + '	' + '	' + str(ti))
				print("Owner ID: " + '	' + '	' + ow)
				if would == 1: print("Secondary Owner: " + '	' + sd)
				print("---------------------------------------")

				selection = input("[1. Yes, 2. No] ")
				confirm = 0 # Reset Value to prevent reselection

				try:
					confirm = choices[selection]
				except KeyError:
					print("Invalid Selection, please try again")
					print("")

				if confirm == 1: 
					# The user has confirmed the choice and the rows will be inserted
					cursor.execute("INSERT INTO vehicle (serial_no, maker, model, year,color, type_id)VALUES (:1,:2,:3,:4,:5,:6)", {'1':sn,'2':mn,'3':mk,'4':yr,'5':cr,'6':ti})	
					if newOwn == 1: # If the owner was not registered
						cursor.execute( "INSERT INTO people (sin,name,height,weight,eyecolor,haircolor,addr,gender,birthday)VALUES(:1,:2,:3,:4,:5,:6,:7,:8,to_date(:9, 'YYYY-MM-DD'))",{'1':ow,'2':nm,'3':ht,'4':wt,'5':ec,'6':hc,'7':ad,'8':gr,'9':bd})
						
					if newSec == 1: # If the secondary owner was not registered
						cursor.execute( "INSERT INTO people (sin,name,height,weight,eyecolor,haircolor,addr,gender,birthday)VALUES(:1,:2,:3,:4,:5,:6,:7,:8,to_date(:9, 'YYYY-MM-DD'))",{'1':sd,'2':nm2,'3':ht2,'4':wt2,'5':ec2,'6':hc2,'7':ad2,'8':gr2,'9':bd2})
					
					# Insert owner rows
					cursor.execute("INSERT INTO owner (owner_id, vehicle_id, is_primary_owner)VALUES (:1,:2,'y')", {'1':ow,'2':sn})
					if would == 1:
						cursor.execute("INSERT INTO owner (owner_id, vehicle_id, is_primary_owner)VALUES  (:1,:2,'n')",  {'1':sd,'2':sn})
					connection.commit()
					print("Returning to Selection")
					break
		if choice == 2: return





