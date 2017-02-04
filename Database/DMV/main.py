"""
// === Program Description
This program embodies the main menu and will be used as such
It will also ask for the user to enter his or her username
as well as their password for verification. The program
itself has five applications:

	- New Vehicle Registration
	- Auto Transaction
	- Driver Licence Registration
	- Violation Record
	- Search Engine
		- Which will encompass the following:
   		1. List the name, licence_no, addr, birthday, 
		driving class, driving_condition, and the 
		expiring_data of a driver by entering either a 
		licence_no or a given name. It shall display all 
		the entries if a duplicate name is given.
		2. List all violation records received by a person 
		if the drive licence_no or sin of a person is 
		entered.
		3. Print out the vehicle_history, including the 
		number of times that a vehicle has been changed 
		hand, the average price, and the number of 
		violations it has been involved by entering the 
		vehicle's serial number.

// === Authors
Jayden Chan
Kynan Ly
Jason Phung

// === Additional Information
Since there are 5 different applications they will be entered
using a numerical system as follows:
	1. New Vehicle Registration
	2. Auto transacion
	3. Driver License Registration
	4. Violation Record
	5. Search Engine
Additionally,
	6. Quit

A Design Document is available in hard copy.

// == Reference Documents
http://stackoverflow.com/questions/16870663/how-do-i-validate-a-date-string-format-in-python
https://eclass.srv.ualberta.ca/pluginfile.php/1930631/mod_assign/intro/InsertLobsOracle.py
https://code.google.com/p/cx-oracle-demos/source/browse/trunk/blob.py?r=11
http://www.orafaq.com/wiki/Python#Inserting_rows

"""

# === External Libraries
import cx_Oracle
import getpass
import sys

# === External Definitions
import VehicleRegistration, AutoTransaction, DriverLicense, ViolationRecord, Search
							   
# Retrieve login information

def main():
	while(True): # === Log In loop
		user = input("Username [%s]: " % getpass.getuser())
		if not user:
			user=getpass.getuser()
		pw = getpass.getpass()

		# === Dictionary Definitions
		choices = {'1':1, '2':2, '3':3, '4':4, '5':5 ,'6':6}
		yesNo = {'1':1, '2':2}

		# Establish connection
		try:
			conString=''+user+'/' + pw +'@gwynne.cs.ualberta.ca:1521/CRS'
			connection = cx_Oracle.connect(conString) 
			cursor = connection.cursor()

			# === Introduction Messages
			print("-----------------------------------")
			print("Welcome to Vehicular Database v0.01")
			print("-----------------------------------")
			print("")

			while(True): # === Main program Loop
				print("")
				print("Please enter your selection")
				print("")
				print("1. New Vehicle Registration")
				print("2. Auto Transaction")
				print("3. Driver License Registration")
				print("4. Violation Record")
				print("5. Search Engine")
				print("6. Quit")

				selection = input("Please enter an Integer between 1 and 6: ")
				choice = 0 # Reset Value to prevent reselection

				try:
					choice = choices[selection]
				except KeyError:
					print("Invalid Choice")
					print("")

				if choice == 1: VehicleRegistration.VR(cursor, connection)
				if choice == 2: AutoTransaction.AT(cursor, connection)
				if choice == 3: DriverLicense.DL(cursor, connection)
				if choice == 4: ViolationRecord.VR(cursor, connection)
				if choice == 5:	Search.SE(cursor, connection)
				if choice == 6: close(cursor, connection)

		except cx_Oracle.DatabaseError as exc:
			print("Connection Invalid/Invalid Credentials/Value Error")
			print("Retry? (Indicating No will result in program termination).")
			tryA = input("[1. Yes, 2. No] ")
			try:
				again = yesNo[tryA]
				if again == 2: sys.exit(0)
			except KeyError:
				print("Invalid Choice")
				print("")

def close(cursor, connection):
	# HouseKeeping Tasks
	connection.commit()
	cursor.close()
	connection.close()
	# Quit program
	sys.exit(0)

if __name__ == '__main__':
	# Run main
	main()














