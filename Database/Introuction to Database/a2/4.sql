prompt Question 4 - kynan
select p.name
from ticket t left join ticket_type f on t.vtype = f.vtype, people p left join drive_licence d on p.sin = d.sin 
where d.class not like '%nondriving' and
t.violator_no = p.sin
group by p.name
having sum(f.fine) > (select avg(sum(f.fine))
from ticket t left join ticket_type f on t.vtype = f.vtype, people p left join drive_licence d on p.sin = d.sin 
where d.class not like '%nondriving' and
t.violator_no = p.sin 
group by p.name);


