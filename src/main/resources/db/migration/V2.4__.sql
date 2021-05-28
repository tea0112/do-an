alter table schedules
drop column if exists is_morning;
alter table schedules
add if not exists period_type int not null;