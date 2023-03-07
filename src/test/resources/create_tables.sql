drop table if exists workout_info;
drop table if exists exercise;
drop table if exists user_workout;

create table exercise
(
    id             bigserial   not null unique,
    exercise_name  varchar(50) not null unique,
    description    text        not null,
    muscle_group   varchar(50) not null,
    alternative_id int,

    primary key (id)
);

create table workout_info
(
    id             bigserial not null unique,
    user_id        bigint    not null,
    workout_day    int       not null,
    number_per_day int       not null,
    exercise_id    int       not null,
    exercise_rep   int       not null,
    exercise_set   int       not null,

    primary key (id),

    foreign key (exercise_id)
        references exercise (id)
        on update cascade
        on delete cascade
);

create table user_workout
(
    id             bigserial   not null unique,
    exercise_name  varchar(50) not null,
    exercise_rep   int         not null,
    exercise_set   int         not null,
    workout_day    int         not null,
    number_per_day int         not null,
    user_id        bigint      not null,

    primary key (id)
);
