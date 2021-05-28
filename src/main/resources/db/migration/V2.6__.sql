SET FOREIGN_KEY_CHECKS=0;

alter table semesters
    add if not exists session_id int not null;

alter table semesters
    add constraint fk_semester_session
        foreign key if not exists (session_id) references sessions(id);
