
import bsddb3 as bsddb


def main():
	DATABASE = "cstudents.db"
   
	db = bsddb.hashopen(DATABASE, 'c') 


	Sid = "123"
	Name = "James"
	Sid2 = "456"
	Name2 = "Josh"
	Sid3 = "789"
	Name3 = "James"

	Sid = Sid.encode(encoding='UTF-8')
	Name = Name.encode(encoding='UTF-8')
	Sid2 = Sid2.encode(encoding='UTF-8')
	Name2 = Name2.encode(encoding='UTF-8')
	Sid3 = Sid3.encode(encoding='UTF-8')
	Name3 = Name3.encode(encoding='UTF-8')


	db[Sid] = Name
	db[Sid2] = Name2
	db[Sid3] = Name3
	
	print (db[Sid])
	print (db[Sid2])
	print (db[Sid3])

	s = input("Enter Sid: ")
	s = s.encode(encoding='UTF-8')
	try:
		print(db[s])		
	except:
		print("no Key exist in that name")

	d = input("Enter data ")
	d = d.encode(encoding='UTF-8')	
	try:
		
		lKey = [key for key, value in db.iteritems() if value == d][0]
		print(lKey)		
		#print(list(db.keys())[list(db.values()).index(d)]) 

	except:
		print("None")
		print(d)
		print(db.items())
	try:
		db.close()
	except Exception as e:
		print (e)

if __name__ == "__main__":
	main()
