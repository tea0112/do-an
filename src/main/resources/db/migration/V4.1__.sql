create table if not exists lecture_halls
(
    id      int auto_increment primary key,
    name    varchar(100) charset utf8 not null unique,
    address varchar(200) charset utf8
);

create table if not exists classrooms
(
    id              int auto_increment primary key,
    name            varchar(100) charset utf8 not null unique,
    lecture_hall_id int                       not null
);

alter table classrooms
    add constraint foreign key (lecture_hall_id) references lecture_halls (id);