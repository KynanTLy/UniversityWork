prompt Question 7 - kynan

select p.sin, p.name, p.addr 
from  ticket t, people p
where t.vdate >= add_months(sysdate, -12) and
p.sin = t.violator_no and
t.vtype not like '%Parking%' 
group by p.sin, p.name, p.addr
having count(*) = 3;

