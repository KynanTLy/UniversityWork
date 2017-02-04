prompt Question 1 - kynan
select serial_no
from vehicle, owner, people
where owner.vehicle_id = vehicle.serial_no and 
people.addr not like '%Edmonton%' and 
owner.owner_id = people.sin;
