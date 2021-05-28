alter table subjects
drop constraint if exists subjects_ibfk_1;
alter table subjects
drop column if exists schedule_id;