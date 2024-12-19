CREATE TABLE IF NOT EXISTS topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    nombre_curso varchar(250) not null,
    fecha_creacion datetime not null,
    status varchar(100) not null,
    autor varchar(100) not null,
    primary key(id)
);