drop table if exists exercises;

create table exercises
(
    id           int         not null unique auto_increment,
    name         varchar(50) not null unique,
    description  varchar     not null,
    muscle_group varchar(50) not null,
    primary key (id)
);