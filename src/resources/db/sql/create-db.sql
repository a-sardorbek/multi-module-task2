
DROP TABLE IF EXISTS `gift_certificate_tag`;
DROP TABLE IF EXISTS `gift_certificate`;
DROP TABLE IF EXISTS `tag`;

create table gift_certificate(
       idGift int not null primary key,
       name varchar(45),
       description text(2000),
       price decimal(10,2),
       duration int,
       create_date varchar(25),
       last_update_date varchar(25)
       );


       create table tag(
           idTag int not null primary key,
           name varchar(45)
           );


create table gift_certificate_tag(
        id_gift_certificate int not null,
        id_tag int not null,
        foreign key (id_gift_certificate) references gift_certificate (idGift)
        on delete restrict on update cascade,
        foreign key (id_tag) references tag (idTag)
        on delete restrict on update cascade,
        primary key (id_gift_certificate, id_tag)
        );
