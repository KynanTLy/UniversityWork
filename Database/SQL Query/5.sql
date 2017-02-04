prompt Question 5 - kynan
select substr(s_date, 8), avg(t.price) , g.type
from auto_sale t left join vehicle v on t.vehicle_id = v.serial_no left join vehicle_type g on v.type_id = g.type_id  
where substr(s_date,8) >= 10 and
substr(s_date,8) <= 13 
group by substr(s_date,8), g.type
;
