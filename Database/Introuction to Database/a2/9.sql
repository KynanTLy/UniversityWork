prompt Question 9 - kynan
select p.sin, p.name
from people p left join owner o on p.sin = o.owner_id left join vehicle_history b on o.vehicle_id = b.vehicle_no
where b.average_price = (
select min(b.average_price)
from vehicle_history b) or
b.number_sales = (
select max(b.number_sales)
from vehicle_history b) or
b.total_tickets = (
select max(b.total_tickets)
from vehicle_history b) 
group by p.sin, p.name;
