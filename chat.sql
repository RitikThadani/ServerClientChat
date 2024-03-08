drop database if exists chat;

create database chat;

use chat;

create table usuarios(
	idus int auto_increment primary key,
    nombre varchar(50) not null,
    contra varchar(50) not null
);

create table mensajes(
	idmsg int auto_increment primary key,
    id_remitente int not null,
    id_destinatario int not null,
    mensaje varchar(1000),
    fecha datetime,
    foreign key (id_remitente) references usuarios(idus),
    foreign key (id_destinatario) references usuarios(idus)
);

insert into usuarios (nombre,contra) values("Ritik","1234");
insert into usuarios (nombre,contra) values("Airam","1234");
insert into usuarios (nombre,contra) values("Luis","1234");