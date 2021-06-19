alter table schedules
    add if not exists classroom_id int not null;

alter table schedules
    add constraint foreign key (classroom_id) references classrooms (id);