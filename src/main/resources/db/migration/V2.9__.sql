create table if not exists department
(
    id   int           not null auto_increment,
    name nvarchar(100) not null unique,
    primary key (id)
);

alter table lecturer
    add if not exists department_id int not null;

alter table lecturer
    add constraint unique (name, department_id);