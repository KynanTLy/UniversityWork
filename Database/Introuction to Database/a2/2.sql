prompt Question 2 - kynan
select distinct name, addr
from owner o1,owner o2, owner o3 , people, vehicle v1, vehicle v2, vehicle v3, vehicle_type
where vehicle_type.type_id = 89 and
v1.type_id = vehicle_type.type_id and 
v2.type_id = vehicle_type.type_id and 
v3.type_id = vehicle_type.type_id and 
v1.serial_no = o1.vehicle_id and 
v2.serial_no = o2.vehicle_id and  
v3.serial_no = o3.vehicle_id and 
v3.serial_no <> v1.serial_no and
v2.serial_no <> v1.serial_no and
v3.serial_no <> v2.serial_no and
o1.vehicle_id <> o2.vehicle_id and 
o1.vehicle_id <> o3.vehicle_id and 
o3.vehicle_id <> o2.vehicle_id and 
o1.owner_id = people.sin and
o2.owner_id = people.sin and
o3.owner_id = people.sin 
;
