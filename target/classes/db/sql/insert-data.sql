

insert into gift_certificate(idGift,name,description,price,duration,create_date,last_update_date) values
(1,'New Year','congrats',55.0,20,'2022-05-08T23:30:04.8880','2022-05-08T23:30:04.888'),
(2,'Birthday','congrats',55.0,20,'2022-05-08T23:30:04.8880','2022-05-08T23:30:04.888'),
(3,'New month','congrats',55.0,20,'2022-05-08T23:30:04.8880','2022-05-08T23:30:04.888');

insert into tag(idTag,name) values
(1,'Winter'),
(2,'Snow'),
(3,'Rain'),
(4,'Sky');

insert into gift_certificate_tag(id_gift_certificate,id_tag) values
(1,2),
(1,3),
(2,3);

