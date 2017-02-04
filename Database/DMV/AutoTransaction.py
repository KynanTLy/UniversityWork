"""
This file contains the execution of the AutoTransaction
application. Any funcions pertaining to said application will
be contained within this file.

Auto Transaction

This component is used to complete an auto transaction. Your program 
shall allow the officer to enter all necessary information to complete 
this task, including, but not limiting to, the details about the seller, 
the buyer, the date, and the price. The component shall also remove the 
relevant information of the previous ownership.
"""

# === External Definitions
import cx_Oracle
import sys
from datetime import datetime #Used to verify date format

def AT(cursor, connection):

	# === Dictionary Definition
	choices = {'1':1, '2':2}

	description()
	buy = 0
	while(True):	# === Application Loop
		print("1. Register Transaction")
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
					td = int(input("Enter Transaction ID: "))
					cursor.prepare("SELECT t.transaction_id FROM auto_sale t WHERE t.transaction_id = :INPUT_TRANS")
					cursor.execute(None, {'INPUT_TRANS': td})
					transaction = cursor.fetchall()
					if transaction == []:
						break
					print("Transaction ID exists, please try again")
					
				
				while(True): 
					sd = input("Enter Seller ID: ")
					while len(sd) < 15:
						sd = sd + ' '

					cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_SELL")
					cursor.execute(None, {'INPUT_SELL': sd})
					seller = cursor.fetchall()
					if seller != []:
						break
					print("SIN not found, please try again")

				
				while(True): # === Enter new person
					bi = input("Enter Buyer ID: ")
					while len(bi) < 15:
						bi = bi + ' '
					cursor.prepare("SELECT p.sin FROM people p WHERE p.sin = :INPUT_BUY")
					cursor.execute(None, {'INPUT_BUY': bi})
					buyer = cursor.fetchall()
					if buyer == []:
						print("SIN not found, would you like to register this person?")
						selection = input("[1. Yes, 2. No] ")
						buy = 0
						try:
							buy = choices[selection]
						except KeyError:
							print("Invalid Selection, please try again")
							print("")
						if buy == 1:
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
							print("SIN: " + '	' + '	' + '	' + bi)
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
								break
					else: break

				while(True):
					vd = input("Enter Vehicle ID: ")
					while len(vd) < 15:
						vd = vd + ' '
					cursor.prepare("SELECT t.vehicle_id FROM auto_sale t WHERE t.vehicle_id = :INPUT_VEH")
					cursor.execute(None, {'INPUT_VEH': vd})
					vehicle = cursor.fetchall()

					if vehicle == []:
						print("Vehicle ID not found (Serial Number)")
						print("Please register the vehicle in the vehicle registration system first.")
						print("Returning to selection")
						AT(cursor, connection) #Return to selection
						return

					cursor.prepare("SELECT t.seller_id FROM auto_sale t, owner o, vehicle v WHERE o.vehicle_id = :INPUT_VEH AND o.owner_id = :INPUT_SELL AND o.is_primary_owner = 'y'")
					cursor.execute(None, {'INPUT_VEH': vd, 'INPUT_SELL': sd})				
					VehOwn = cursor.fetchall()					
					if VehOwn == []:
						print("Seller does not own this vehicle!")
						print("Returning to selection")
						AT(cursor, connection) # Returns to selection
						return	# makes sure it eventually terminates
					else:
						break
					

				while(True): # === Check date repeatedly until Format is correct.
					dd = input("Enter Date [yyyy-mm-dd]: ")
					try:
					# If try succeeds then date is in correct format
						datetime.strptime(dd, '%Y-%m-%d')
						break
					except ValueError:
						print("Incorrect format/Value, should be [yyyy-mm-dd]")

				while(True): # === Check the price repeatedl until user inputs a correct value
					pr = input("Enter Price [xxxxxxxxx.xx]: ")
					try:
						pr = float(pr) #Change price into an int
						break
					except ValueError:
						print("Please enter a Floating Point Value")

				# === Confirmation
				print("Is everything correct?")
				print("---------------------------------------")
				print("Fields" + '	' + '	' + '	' + "Values")
				print("Transaction ID: " + '	' + str(td))
				print("Seller ID: " + '	' + '	'+ sd)
				print("Buyer ID: " + '	' + '	'+ bi)
				print("Vehicle ID: " + '	' + '	' + vd)
				print("Transaction Date: " + '	' + dd)
				print("Price: " + '	' + '	' + '	' + str(pr))
				print("---------------------------------------")

				selection = input("[1. Yes, 2. No] ")
				confirm = 0 # Reset Value to prevent reselection

				try:
					confirm = choices[selection]
				except KeyError:
					print("Invalid Selection, please try again")
					print("")

				if confirm == 1: 

					cursor.prepare("DELETE FROM owner o WHERE o.vehicle_id = :INPUT_VEH")
					cursor.execute(None, {'INPUT_VEH': vd})

					if buy == 1:
						cursor.execute( "INSERT INTO people (sin,name,height,weight,eyecolor,haircolor,addr,gender,birthday)VALUES(:1,:2,:3,:4,:5,:6,:7,:8,to_date(:9, 'YYYY-MM-DD'))",{'1':bi,'2':nm,'3':ht,'4':wt,'5':ec,'6':hc,'7':ad,'8':gr,'9':bd})

					cursor.execute("INSERT INTO owner (owner_id, vehicle_id, is_primary_owner)VALUES (:1,:2,'y')", {'1':bi,'2':vd})
					cursor.execute("INSERT INTO auto_sale (transaction_id, seller_id, buyer_id, vehicle_id, s_date, price) VALUES (:1,:2,:3,:4,to_date(:5,'YYYY-MM-DD'),:6)", {'1':td,'2':sd, '3':bi, '4':vd, '5':dd, '6':pr})
					
					connection.commit()
					break
		if choice == 2: return 

def description():
	# === Introduction Messages
	print("")
	print("----------------------------")
	print("Welcome to Auto Transaction.")
	print("----------------------------")
	print("")
	return
