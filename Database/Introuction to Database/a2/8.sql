prompt Question 8 - kynan
drop view vehicle_history;

create view vehicle_history as 
select v.serial_no as vehicle_no, count(distinct b.transaction_id) as number_sales, round(avg(b.price),2) as average_price, count(distinct t.ticket_no) as total_tickets
from vehicle v left join auto_sale b on v.serial_no = b.vehicle_id left join ticket t on t.vehicle_id  = v.serial_no
group by v.serial_no;
