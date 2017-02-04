people = []
drive_licence = []
drive_condition = []
restriction = []
vehicle_type = []
vehicle = []
owner = []
auto_sale = []
ticket_type= []
ticket = []

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(15,40,float,float,10,10,50,char,date)
cursInsert.executemany("INSERT INTO PEOPLE(SIN,NAME,HEIGHT,WEIGHT,EYE_COLOUR,HAIR_COLOUR,ADDR,GENDER,BIRTHDAY)""VALUES(:1,:2,:3,:4,:5,:6,:7,:8,:9)",people)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(15,15,10,blob,date,date)
cursInsert.executemany("INSERT INTO DRIVE_LICENCE(LICENCE_NO,SIN,CLASS,PHOTO,ISSUING_DATE,EXPIRING_DATE)""VALUES(:1,:2,:3,:4,:5,:6)",drive_licence)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(int, 1024)
cursInsert.executemany("INSERT INTO DRIVE_CONDITIONS(C_ID,DESCRIPTION)""VALUES(:1,:2)",drive_condition)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(15, int)
cursInsert.executemany("INSERT INTO RESTRICTION(LICENCE_NO, R_ID)""VALUES(:1,:2)",restriction)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(int, 10)
cursInsert.executemany("INSERT INTO VEHICLE_TYPE(TYPE_ID, TYPE)""VALUES(:1,:2)",vehicle_type)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(15,20,20,float,10,int)
cursInsert.executemany("INSERT INTO VEHICLE(SERIAL_NO,MAKER,MODEL,YEAR,COLOUR,TYPE_ID)""VALUES(:1,:2,:3,:4,:5,:6)",vehicle)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(15,15,1)
cursInsert.executemany("INSERT INTO OWNER(OWNER_ID,VEHICLE_ID,IS_PRIMARY_OWNER)""VALUES(:1,:2,:3)",owner)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(int,15,15,15,date,float)
cursInsert.executemany("INSERT INTO AUTO_SALE(TRANSACTION_ID,SELLER_ID,BUYER_ID,VEHICLE_ID,S_DATE,PRICE)""VALUES(:1,:2,:3,:4,:5,:6)",auto_sale)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(10,float)
cursInsert.executemany("INSERT INTO TICKET_TYPE(VTYPE,FINE)""VALUES(:1,:2)",ticket_type)

cusrInsert.commit()

cursInsert = connection.cursor()
cursInsert.bindarraysize = 3
cursInsert.serinputsizes(int,15,15,15,10,date,20,1024)
cursInsert.executemany("INSERT INTO TICKET(TICKET_NO,VIOLATOR_NO,VEHICLE_ID,OFFICE_NO,VTYPE,VDATE,PLACE,DESCRIPTIONS)""VALUES(:1,:2,:3,:4,:5,:6,:7,:8)",ticket)

curs.prepare("SELECT T_NAME, PRICE FROM TOFFEES WHERE PRICE > :price")
curs.execute(None, {‘price’:INPUT})

rows = curs.fetchall()

print(rows)


curs.prepare("SELECT NAME, LICENCE_NO, ADDR, BIRTHDAY, CLASS, DESCRIPTION, EXPIRING_DATE FROM PEOPLE LEFT JOIN DIRVE_LICENCE ON PEOPLE.SIN = DRIVE_LICENCE LEFT JOIN RESTRICTION ON DRIVE_LICENCE.LICENCE_NO = RESTRICTION.LICENCE_NO LEFT JOIN DRIVING_CONDITION ON RESTRICTION.R_ID = DRIVING_CONDITION.C_ID WHERE LICENCE_NO = :INPUT_LICENCE")
curs.execute(None, {‘INPUT_LICENCE’:USER_LICENCE})
rows = curs.fetchall()
print(rows)

curs.prepare("SELECT NAME, LICENCE_NO, ADDR, BIRTHDAY, CLASS, DESCRIPTION, EXPIRING_DATE FROM PEOPLE LEFT JOIN DIRVE_LICENCE ON PEOPLE.SIN = DRIVE_LICENCE LEFT JOIN RESTRICTION ON DRIVE_LICENCE.LICENCE_NO = RESTRICTION.LICENCE_NO LEFT JOIN DRIVING_CONDITION ON RESTRICTION.R_ID = DRIVING_CONDITION.C_ID WHERE PEOPLE.NAME = :INPUT_NAME")
curs.execute(None, {‘INPUT_NAME’:USER_NAME})
rows = curs.fetchall()
print(rows)


curs.prepare("SELECT TICKET_NO, VIOLATOR_NO, VEHICLE_NO, OFFICE_NO,VTYPE,VDATE,PLACE,DESCRIPTIONS FROM PEOPLE LEFT JOIN DRIVE_LICENCE ON PEOPLE.SIN = DRIVE_LICENCE.SIN LEFT JOIN TICKET ON PEOPLE.SIN = TICKET.SIN LEFT JOIN TICKET_TYPE ON TICKET.VTYPE = TICKET_TYPE.TYPE WHERE PEOPLE.NAME = :INPUT_NAME")
curs.execute(None, {‘INPUT_NAME’:USER_NAME})
rows = curs.fetchall()
print(rows)


curs.prepare("SELECT TICKET_NO, VIOLATOR_NO, VEHICLE_NO, OFFICE_NO,VTYPE,VDATE,PLACE,DESCRIPTIONS FROM PEOPLE LEFT JOIN DRIVE_LICENCE ON PEOPLE.SIN = DRIVE_LICENCE.SIN LEFT JOIN TICKET ON PEOPLE.SIN = TICKET.SIN LEFT JOIN TICKET_TYPE ON TICKET.VTYPE = TICKET_TYPE.TYPE WHERE DRIVE_LICENCE.LICENCE_NO = :INPUT_LICENCE")
curs.execute(None, {‘INPUT_LICENCE’:LICENCE_NO})
rows = curs.fetchall()
print(rows)

//===================//

cursor.prepare("SELECT v.serial_no FROM v.vehicle WHERE v.serial_no = :INPUT_SERIAL")
cursor.execute(None, {'INPUT_SERIAL': serial_no})
rows = cursor.fetchall()


cursor.preprae("SELECT p.sin FROM p.people WHERE p.sin = :INPUT_SIN")
cursor.execute(None, {'INPUT_SIN': sin})
rows = cursor.fetchall()

cursor.execute("INSERT INTO vehicle VALUES (?,?,?,?,?,?)", (A,B,C,D,E,F))
cursor.commit()
// Owner
cursor.execute("INSERT INTO owner VALUES (?,?,'y')", (G,H))
cursor.commity()
// Second owner
cursor.execute("INSERT INTO owner VALUES (?,?,'n')", (I,H))
cursor.commity()

cursor.execute("INSERT INTO people VALUES (?,?,?,?,?,?,?,?,?)", (a,b,c,d,e,f,g,h,i))

//

uto_sale t left join vehicle v on t.vehicle_id = v.serial_no left join vehicle_type g on v.type_id = g.type_id  

