"""
This file contains the execution of the Search Engine
application. Any funcions pertaining to said application will
be contained within this file.

Auto Transaction

This component is used to perform the following searches.

	1. List the name, licence_no, addr, birthday, driving class, 
	driving_condition, and the expiring_data of a driver by entering 
	either a licence_no or a given name. It shall display all the 
	entries if a duplicate name is given.
	2. List all violation records received by a person if the drive 
	licence_no or sin of a person  is entered.
	3. Print out the vehicle_history, including the number of times 
	that a vehicle has been changed hand, the average price, and the 
	number of violations it has been involved by entering the 
	vehicle's serial number.
"""

# === External Definitions
import cx_Oracle
import sys

def SE(cursor,connection):
	choices = {'1':1, '2':2, '3':3, '4':4}
	yesNo = {'1':1, '2':2}

	intro()

	while(True):
		print("Please enter your selection")
		print("")
		print("1. Search License Details")
		print("2. Search Violation Records")
		print("3. Search Vehicle History")
		print("4. Return to Main Menu")

		selection = input("[Please enter an Integer between 1 and 4] ")

		try:
			choice = choices[selection]
		except KeyError:
			print("Invalid Choice")
			print("")



		while(True):
			if choice == 1:
			# === Licence Details

				print("\n")
				print("Which query do you wish to use?")
				print("")
				print("1. License Number")
				print("2. Name")
				select = input("[Please enter 1 or 2] ")

				try:
					userChoice = yesNo[select]
			
				except KeyError:
					print("Invalid Selection, please try again")
					print("")

				# === User Choice: Licence Number === #
				if userChoice == 1:
					userAns = input("Please enter a valid license number: ")

					# === Account for CHAR(15)
					while len(userAns) < 15:
						userAns  = userAns + " "

					# === SQL Query for Licence as Input
					# Explicit Line joining for Ease of Reading
					cursor.prepare("\
\
SELECT p.name ,d.licence_no, p.addr, p.birthday, d.class, d.expiring_date \
FROM people p , drive_licence d \
WHERE p.sin = d.sin AND d.licence_no = :licenceno")

					# === Execute and Fetch from SQL
					cursor.execute(None,{"licenceno":userAns})
					rows = cursor.fetchall()

					# === SQL Query for Restrictions
					cursor.prepare("\
SELECT c.description \
FROM people p , drive_licence d, driving_condition c, restriction r \
WHERE p.sin = d.sin AND d.licence_no = r.licence_no AND r.r_id = c.c_id AND d.licence_no = :licenceno")

					# === Execute and Fetch from SQL
					cursor.execute(None,{"licenceno":userAns})
					extra = cursor.fetchall()

					if extra == []:
						extra = [[("None")]]

					if rows:
						for row in rows:
							# Print out Header:
							print("---------------------------------------------")
							print("Description" + '	' + '	' + "Value")
							# Print out Details
							print("Name: " + '	' + '	' + '	' + row[0])
							print("License Number: " + '	' + row[1])
							print("Address: " + '	' + '	' + row[2])
							print("Birthday: " + '	' + '	' + str(row[3])[:10])
							print("Class: " + '	' + '	' + '	' + row[4])
							print("Driving Conditions:")
							i = 0
							for ro in extra:
								i+=1 #Numbering
								print('	' + str(i) + ". " + ro[0])

							print("Expiry Date: " + '	' + '	' + str(row[5])[:10])
							print("---------------------------------------------")
						break # === Return to Selection
					else:
						print("Licence number not found!")
						print("Returning to Selection")

						break # === Return to Selection

				# === User Choice: Name === #
				elif userChoice == 2:
					userAns = input("Please enter a valid name: ")

					# === SQL Query for Name as Input
					# Explicit Line joining for Ease of Reading
					cursor.prepare("\
\
SELECT p.name , d.licence_no, p.addr, p.birthday, d.class, d.expiring_date \
FROM people p , drive_licence d \
WHERE p.sin = d.sin AND p.name = :name")

					cursor.execute(None,{"name":userAns})
					rows = cursor.fetchall()

					if rows:
						for row in rows:

							# === SQL Query for Restrictions
							cursor.prepare("\
SELECT c.description \
FROM people p , drive_licence d, driving_condition c, restriction r \
WHERE p.sin = d.sin AND d.licence_no = r.licence_no AND r.r_id = c.c_id AND d.licence_no = :licenceno")

							# === Execute and Fetch from SQL
							cursor.execute(None,{"licenceno":row[1]}) # Check for restriction for current driver
							extra = cursor.fetchall()

							if extra == []:
								extra = [[("None")]]
							# Print out Header:
							print("---------------------------------------------")
							print("Description" + '	' + '	' + "Value")
							# Print out Details
							print("Name: " + '	' + '	' + '	' + row[0])
							print("License Number: " + '	' + row[1])
							print("Address: " + '	' + '	' + row[2])
							print("Birthday: " + '	' + '	' + str(row[3])[:10])
							print("Class: " + '	' + '	' + '	' + row[4])
							print("Driving Conditions:")
							i = 0
							for ro in extra:
								i+=1 #Numbering
								print('	' + str(i) + ". " + ro[0])

							print("Expiry Date: " + '	' + '	' + str(row[5])[:10])
							print("---------------------------------------------")
						break # === Return to Selection
					else:
						print("Name not found!")
						print("Returning to Selection")

						break # === Return to Selection

			# === Search for Violation Records
			if choice == 2:
				print("\n")
				print("Which query do you wish to use?")
				print("")
				print("1. License Number")
				print("2. SIN")
				select = input("[Please enter 1 or 2] ")

				try:
					userChoice = yesNo[select]
			
				except KeyError:
					print("Invalid Selection, please try again")
					print("")

				if userChoice == 1:
					userAns = input("Please enter a valid license number: ")
					while len(userAns) < 15:
						userAns  = userAns + " "
					#sql statement for licence
					cursor.prepare("\
\
SELECT t.ticket_no as tick#, t.violator_no, t.vehicle_id, t.office_no, t.vtype, t.vdate, t.place, t.descriptions \
FROM ticket t, ticket_type y, people p, drive_licence d \
WHERE p.sin = t.violator_no AND t.vtype = y.vtype AND d.sin = p.sin AND d.licence_no = :licenceno")

					cursor.execute(None,{"licenceno":userAns})
					rows = cursor.fetchall()
					if rows:
						for row in rows:
							# Print out Header:
							print("---------------------------------------------")
							print("Description" + '	' + '	' + "Value")
							# Print out Details
							print("Ticket Number: " + '	' + '	' + str(row[0]))
							print("Violator SIN: " +'	' + '	' + row[1])
							print("Vehicle ID: " + '	' + '	' + row[2])
							print("Officer SIN: " + '	' + '	' + row[3])
							print("Violation Type: " + '	' + row[4])
							print("Violation Date: " + '	' + str(row[5])[:10])
							print("Place: " + '	' + '	' + '	' + row[6])
							print("Violation Description: ")
							print("	" + row[7])
							print("---------------------------------------------")
						break # === Return to Selection
					else:
						print("No violation found for the entered license number.")
						print("Returning to Selection")

						break # === Return to Selection

				elif userChoice == 2:
					userAns = input("Please enter a valid SIN: ")
					while len(userAns) < 15:
						userAns  = userAns + " "
					#sql statement for sin
					cursor.prepare("\
SELECT t.ticket_no as tick#, t.violator_no, t.vehicle_id, t.office_no, t.vtype, t.vdate, t.place, t.descriptions \
FROM ticket t, ticket_type y, people p, vehicle v \
WHERE p.sin = t.violator_no AND v.serial_no = t.vehicle_id AND t.vtype = y.vtype and p.sin = :sin")

					cursor.execute(None,{"sin":userAns})
					rows = cursor.fetchall()
					if rows:
						for row in rows:
							# Print out Header:
							print("---------------------------------------------")
							print("Description" + '	' + '	' + "Value")
							# Print out Details
							print("Ticket Number: " + '	' + '	' + str(row[0]))
							print("Violator SIN: " + '	' + '	' + row[1])
							print("Vehicle ID: " + '	' + '	' + row[2])
							print("Officer SIN: " + '	' + '	' + row[3])
							print("Violation Type: " + '	' + row[4])
							print("Violation Date: " + '	' + str(row[5])[:10])
							print("Place: " + '	' + '	' + '	' + row[6])
							print("Violation Description: ")
							print("	" + row[7])
							print("---------------------------------------------")
						break # === Return to Selection
					else:
						print("No violations found for entered SIN.")
						print("Returning to Selection")

						break # === Return to Selection

			# === Search for Vehicle History === # 
			if choice == 3:
				userAns = input("Please enter a valid vehicle serial number: ")
				while len(userAns) < 15: #Compensate for CHAR (IF user enters a number less than 15 characters)
					userAns  = userAns + " "

				#sql statement for serial number
				cursor.prepare("\
SELECT count(DISTINCT auto_sale.transaction_id), avg(auto_sale.price), count(DISTINCT ticket.ticket_no) as numTickets \
FROM vehicle left join auto_sale on vehicle.serial_no = auto_sale.vehicle_id left join ticket on vehicle.serial_no = ticket.vehicle_id \
WHERE vehicle.serial_no = :serialno ")

				cursor.execute(None,{"serialno":userAns})
				rows = cursor.fetchall()
				if rows[0][0] == 0:
					print("Vehicle does not exist")
					break
				elif rows:
					for row in rows:
						# Print out Header:
						print("----------------------------------------------------")
						print("Description" + '	' + '	' + '	' + '	' + "Value")
						# Print out Details
						print("Changed Hands: " + '	' + '	' + '	' + '	' + str(row[0]) + " times")
						print("Average Sale Price: " + '	' + '	' + '	' + str(row[1]))
						print("Number of Violations Involved in: " + '	' + str(row[2]))
						print("----------------------------------------------------")
					break # === Return to Selection
				else:
					print("Serial number not found!")
					print("Returning to Selection")

					break # === Return to Selection

			if choice == 4: return #Returns to main menu

def intro():
	# === Introduction Messages
	print("")
	print("-----------------------------")
	print("Welcome to the Search Engine.")
	print("-----------------------------")
	print("")
	return

