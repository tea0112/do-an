alter table classes
    drop INDEX if exists name;
alter table classes
    add constraint unique (name, session_id, department_id);