prompt Question 6 - kynan
(select p.name
from people p 
group by p.name
)minus(
select p.name
from vehicle_type d left join vehicle v on v.type_id = d.type_id left join auto_sale t on t.vehicle_id = v.serial_no left join owner o on o.vehicle_id = v.serial_no left join people p on p.sin = o.owner_id
group by d.type, substr(s_date, 8), v.maker, v.model, o.owner_id, p.name
having count(*) >= all (
select count(*)
from vehicle_type d2 left join vehicle v2 on v2.type_id = d2.type_id left join auto_sale t2 on t2.vehicle_id = v2.serial_no left join owner o2 on o2.vehicle_id = v2.serial_no left join people p2 on p2.sin = o2.owner_id      
where 
d.type = d2.type and
substr(t.s_date, 8) = substr(t2.s_date, 8)
group by d2.type, substr(t2.s_date, 8), v2.maker, v2.model, o2.owner_id, p2.name
)
);
