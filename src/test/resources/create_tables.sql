drop table if exists workout_info;
drop table if exists exercises;

create table exercises
(
    id           bigserial   not null unique,
    name         varchar(50) not null unique,
    description  text        not null,
    muscle_group varchar(50) not null,

    primary key (id)
);

create table workout_info
(
    id             bigserial not null unique,
    user_id        bigint    not null,
    workout_day    int       not null,
    number_per_day int       not null,
    exercise_id    int       not null,
    reps           int       not null,
    sets           int       not null,

    primary key (id),

    foreign key (exercise_id)
        references exercises (id)
        on update cascade
        on delete cascade
);
