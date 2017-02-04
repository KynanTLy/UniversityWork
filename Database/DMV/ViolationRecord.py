"""
This file contains the execution of the Violation Record
application. Any funcions pertaining to said application will
be contained within this file.

Violation Record

This component is used by a police officer to issue a traffic 
ticket and record the violation. You may assume that all the 
information about ticket_type has been loaded in the initial 
database.
"""

# === External Definitions
import cx_Oracle
import sys
from datetime import datetime #Used to verify date format

def VR(cursor, connection):
	"""
	This program makes the assumption that the vehicle, and people in
	question are already registered. If they are not the user will be
	told that they have not yet been registered.
	"""

	# === Dictionary Definition
	choices = {'1':1, '2':2}

	description()

	while(True):	# === Application Loop
		print("1. Issue Traffic Ticket")
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
					tn = int(input("Enter Violation Number: "))
					cursor.prepare("SELECT t.ticket_no FROM ticket t WHERE t.ticket_no = :INPUT_TICK")
					cursor.execute(None, {'INPUT_TICK': tn})
					ticket = cursor.fetchall()
					if ticket == []:
						break
					print("This violation number exists, please try again")

				while(True):
					vi = input("Enter Vehicle Serial Number: ")
					while len(vi) < 15:
						vi = vi + ' '

					cursor.prepare("SELECT t.vehicle_id FROM ticket t WHERE t.vehicle_id = :INPUT_VEH")
					cursor.execute(None, {'INPUT_VEH': vi})
					vehicle = cursor.fetchall()
					if vehicle != []:
						break
					print("This vehicle does not exist, please try again")


				while(True):
					print("Charge the Primary Owner or a Given Driver")
					pick = input("[Please enter 1 or 2] ")
					picks = 0
					try:
						picks = choices[pick]
					except KeyError:
						print("Invalid Selection, please try again")
						print("")					
					
					if picks == 1:
						cursor.prepare("SELECT o.owner_id FROM owner o WHERE o.vehicle_id = :INPUT_PO AND o.is_primary_owner = 'y'")
						cursor.execute(None, {'INPUT_PO': vi})
						sin = cursor.fetchall()
						vn = sin[0][0]
						break	
					if picks == 2:
						while(True):
							vn = input("Enter Violator Number: ")
							while len(vn) < 15:
								vn = vn + ' '
		
							cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_VIOL")
							cursor.execute(None, {'INPUT_VIOL': vn})
							sin = cursor.fetchall()
							if sin != []:
								break
							print("This sin number does not exist, please try again")
						break					


				while(True):
					on = input("Enter Officer's SIN: ")
					while len(on) < 15:
						on = on + ' '

					cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_OFF")
					cursor.execute(None, {'INPUT_OFF': on})
					officer = cursor.fetchall()
					if officer != []:
						break
					print("This sin does not exist, please try again")

				while(True):
					vt = input("Enter Violation Type: ")
					while len(vt) < 10:
						vt = vt + ' '

					cursor.prepare("SELECT t.vtype FROM ticket_type t WHERE t.vtype = :INPUT_TYPE")
					cursor.execute(None, {'INPUT_TYPE': vt})
					vtype = cursor.fetchall()
					if vtype != []:
						break
					print("This violation type does not exist, please try again")
				

				while(True): # === Check date repeatedly until Format is correct.
					vd = input("Enter Date [yyyy-mm-dd]: ")
					try:
					# If try succeeds then dae is in correct format
						datetime.strptime(vd, '%Y-%m-%d')
						break
					except ValueError:
						print("Incorrect format/Value, should be [yyyy-mm-dd]")

				pc = input("Enter Place: ")
				ds = input("Enter Description: ")

				# === Confirmation
				print("Is everything correct?")
				print("---------------------------------------")
				print("Fields" + '	' + '	' + '	' + "Values")
				print("Violation Number: " + '	' + str(tn))
				print("Violator SIN: " + '	' + '	'+ vn)
				print("Vehicle ID: " + '	' + '	'+ vi)
				print("Officer SIN: " + '	' + '	' + on)
				print("Violation Type: " + '	' + vt)
				print("Date: " + '	' + '	' + '	' + vd)
				print("Place: " + '	' + '	' + '	' + pc)
				print("Description: ")
				print('	'+  ds)
				print("---------------------------------------")

				selection = input("[1. Yes, 2. No] ")
				confirm = 0 # Reset Value to prevent reselection

				try:
					confirm = choices[selection]
				except KeyError:
					print("Invalid Selection, please try again")
					print("")

				if confirm == 1: 
					cursor.execute( "INSERT INTO ticket (ticket_no,violator_no,vehicle_id,office_no,vtype,vdate,place,descriptions)VALUES(:1,:2,:3,:4,:5,to_date(:6, 'YYYY-MM-DD'),:7,:8)",{'1':tn,'2':vn,'3':vi,'4':on,'5':vt,'6':vd,'7':pc,'8':ds})
					connection.commit()
					break
 					
					
		if choice == 2: return

def description():
	# === Introduction Messages
	print("")
	print("-----------------------------")
	print("Welcome to Violation Records.")
	print("-----------------------------")
	print("")
	return
