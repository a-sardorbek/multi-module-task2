
DROP TABLE IF EXISTS `gift_certificate_tag`;
DROP TABLE IF EXISTS `gift_certificate`;
DROP TABLE IF EXISTS `tag`;

create table gift_certificate(
       id int not null primary key,
       name varchar(45),
       description text(2000),
       price decimal(10,2),
       duration int,
       create_date varchar(25),
       last_update_date varchar(25)
       );


       create table tag(
           id int not null primary key,
           name varchar(45)
           );


create table gift_certificate_tag(
        id_gift_certificate int not null,
        id_tag int not null,
        foreign key (id_gift_certificate) references gift_certificate (id)
        on delete restrict on update cascade,
        foreign key (id_tag) references tag (id)
        on delete restrict on update cascade,
        primary key (id_gift_certificate, id_tag)
        );

