alter table semesters
drop column if exists end_date;
alter table semesters
add if not exists end_day date not null;