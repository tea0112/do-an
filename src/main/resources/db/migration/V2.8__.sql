alter table classes
    drop INDEX if exists name;

alter table classes
    add if not exists session_id int not null;

SET FOREIGN_KEY_CHECKS=0;
SET GLOBAL FOREIGN_KEY_CHECKS=0;

alter table classes
    add constraint
        foreign key if not exists (session_id) references sessions (id);
