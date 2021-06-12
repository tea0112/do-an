alter table classes
add constraint UNIQUE (name, session_id);