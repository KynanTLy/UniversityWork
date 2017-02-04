-- Insert for People -- 

insert into people values ('123456789', 'Happy', 150.2, 170.6, 'blue', 'green', 'South Edmonton', 'f', '31-may-80');
insert into people values ('987654321', 'Sad', 290.2, 80.6, 'red', 'black', 'Calgary', 'm', '10-june-95');
insert into people values ('555555555', 'Mad', 390.3, 96.7, 'purple', 'orange', 'Edmonton', 'm', '16-december-92');
insert into people values ('666666666', 'Calm', 60.0, 150.78, 'blue', 'white', 'Edmonton', 'f', '9-november-87');

insert into people values ('999999999', 'Doom', 70.0, 15.78, 'grey', 'teal', 'Edmonton', 'm', '28-june-80');
insert into people values ('111111111', 'Rage', 18.0, 770.78, 'black', 'white', 'Edmonton', 'f', '13-july-79');



-- Insert for Driver licence --

insert into drive_licence values ('101abc101', '123456789', '3', null,'4-june-07', '4-june-10');
insert into drive_licence values ('abc101abc', '987654321', 'A', null,'29-April-13', '29-April-23');
insert into drive_licence values ('a101b101c', '555555555', 'B', null,'16-june-10', '18-june-22');
insert into drive_licence values ('a101b101d', '666666666', 'nondriving', null,'07-may-11', '18-june-22');
insert into drive_licence values ('a101b101e', '999999999', 'nondriving', null,'30-july-12', '18-june-22');
insert into drive_licence values ('a101b101f', '111111111', 'nondriving', null,'14-may-11', '18-june-22');

-- Insert for driving_condition  --
insert into driving_condition  values (1, 'Blind');
insert into driving_condition  values (2, 'Perfectly Okay');
insert into driving_condition  values (3, 'Should not drive');

-- Insert for Restriction --
insert into restriction values ('101abc101', 1);
insert into restriction values ('abc101abc', 2);
insert into restriction values ('a101b101c', 3);


-- Insert for Vehicle_Type --

insert into vehicle_type values (101, 'General');
insert into vehicle_type values (1337, 'Sport');
insert into vehicle_type values (89, 'SUVs');

-- Insert for vehicle --

insert into vehicle values ('000001', 'Honda' , 'Fast' , 2010, 'black', 1337);

insert into vehicle values ('000002', 'Google', 'Bad' , 2012, 'red' , 89);
insert into vehicle values ('000003', 'Google', 'Best' , 2011, 'grey' , 89);
insert into vehicle values ('000004', 'Google', 'Slow' , 2000, 'red' , 89);
insert into vehicle values ('000005', 'Google', 'Good' , 2001, 'black' , 89);

insert into vehicle values ('000006', 'Honda', 'Jump' , 2012, 'purple' , 89);
insert into vehicle values ('000007', 'Honda' , 'Race', 2016, 'red' , 89);


insert into vehicle values ('000008', 'Apple' , 'Boo', 2016, 'teal' , 101);
insert into vehicle values ('000009', 'Apple' , 'Sucks', 2012, 'white' , 101);





-- Insert for owner --

insert into owner values ('123456789', '000003' , 'y');
insert into owner values ('123456789', '000005' , 'y');
insert into owner values ('123456789', '000007' , 'y');
insert into owner values ('123456789', '000006' , 'y');
insert into owner values ('123456789', '000001' , 'y');


insert into owner values ('987654321', '000002' , 'y');
insert into owner values ('987654321', '000004' , 'y');
insert into owner values ('987654321', '000006' , 'n');


insert into owner values ('555555555', '000008' , 'n');
insert into owner values ('555555555', '000009' , 'y');
insert into owner values ('555555555', '000001' , 'n');
insert into owner values ('555555555', '000006' , 'y');

-- 
insert into owner values ('666666666', '000006' , 'n');
insert into owner values ('666666666', '000001' , 'n');
insert into owner values ('666666666', '000008' , 'n');


-- Insert auto_sale -- 



insert into auto_sale values ('337' ,  '123456789' , '987654321', '000002', '10-June-2011', 100000);
insert into auto_sale values ('338' , '666666666' , '123456789', '000003',  '01-July-2012', 10000 );
insert into auto_sale values ('339' ,  '555555555' , '123456789', '000006', '01-May-2011', 100000  );

insert into auto_sale values ('340' ,   '123456789' , '111111111', '000003', '30-March-2000', 7000 );
insert into auto_sale values ('341' , '123456789' , '111111111', '000005', '30-March-2000', 7000  );
insert into auto_sale values ('342' ,   '123456789' , '111111111', '000007', '30-March-2000', 7000);
insert into auto_sale values ('343' , '123456789' , '111111111', '000006', '30-March-2000', 10000  );
insert into auto_sale values ('344' ,   '123456789' , '111111111', '000001', '30-March-2000', 7000);
insert into auto_sale values ('345' , '987654321' , '111111111', '000002', '30-March-2000', 7000  );
insert into auto_sale values ('346' ,   '987654321' , '111111111', '000004', '30-March-2000', 7000 );
insert into auto_sale values ('347' ,  '987654321' , '111111111', '000006','30-March-2000', 1000);
insert into auto_sale values ('348' ,  '555555555' , '111111111', '000008', '30-March-2000', 7000 );
insert into auto_sale values ('349' ,   '555555555' , '111111111', '000009', '30-March-2000', 7000 );
insert into auto_sale values ('350' , '555555555' , '111111111', '000001','30-March-2000', 7000 );
insert into auto_sale values ('351' ,  '555555555' , '111111111', '000006', '30-March-2000', 10000  );
insert into auto_sale values ('352' ,  '666666666' , '111111111', '000006', '30-March-2000', 10000);
insert into auto_sale values ('353' ,  '666666666' , '111111111', '000001', '30-March-2000', 7000  );
insert into auto_sale values ('354' ,   '666666666' , '111111111', '000008', '30-March-2000', 7000);



insert into auto_sale values ( '336' , '987654321' , '123456789', '000001', '01-January-2010', 100 );
insert into auto_sale values ('335' , '123456789' , '987654321', '000006', '31-May-2012', 10000 );
insert into auto_sale values ( '334' , '987654321' , '123456789', '000006', '24-July-2012', 10000 );


--Insert for ticket type --

insert into ticket_type values ('Speed', '127.20');
insert into ticket_type values ('Damage', '780.67');
insert into ticket_type values ('Parking', '2.00');

-- Insert for Ticket --


insert into ticket values ('01', '123456789',  '000001','111111111' ,'Damage',   '09-June-2013','Edmonton','come on');
insert into ticket values ('02', '123456789', '000006' , '999999999', 'Damage', '10-June-2014','Edmonton','What was the person doing');
insert into ticket values ('03',  '123456789', '000006' ,'999999999',    'Damage', '10-June-2014','Edmonton','Really?');
insert into ticket values ('04', '123456789', '000006' , '999999999',  'Damage', '10-June-2014','Edmonton','......');

insert into ticket values ('08',  '987654321', '000002' , '999999999', 'Parking', '20-May-2013','Edmonton','Why?');


insert into ticket values ('05', '666666666', '000009' ,  '999999999', 'Parking', '29-May-2014','Edmonton','Nope.');
insert into ticket values ('06',  '666666666', '000009' ,  '999999999', 'Parking', '29-May-2014','Edmonton','Nope..');
insert into ticket values ('07', '666666666', '000009' ,  '999999999' ,'Parking', '29-May-2014','Edmonton','Nope...');



