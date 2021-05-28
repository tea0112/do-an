alter table subjects
drop constraint if exists subjects_ibfk_4;
alter table subjects
drop column if exists semester_id;