alter table subjects
drop if exists grade;

alter table subjects
add if not exists subject_type int not null;