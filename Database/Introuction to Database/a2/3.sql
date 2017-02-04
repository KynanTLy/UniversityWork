prompt Question 3 - kynan
(select s.name, l.licence_no
from people s, drive_licence l, owner o, vehicle v
where s.sin = l.sin and
 l.sin = o.owner_id and
 s.sin = o.owner_id and
l.class not like '%nondriving' and
o.vehicle_id = v.serial_no and
o.is_primary_owner like '%y'
) minus 
(select s.name, l.licence_no
from people s, drive_licence l, owner o, vehicle v
where s.sin = l.sin and
 l.sin = o.owner_id and
 s.sin = o.owner_id and
l.class not like '%nondriving' and
o.vehicle_id = v.serial_no and
o.is_primary_owner like '%y'and 
v.color like '%red'
);
