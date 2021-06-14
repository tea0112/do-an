alter table department
add column if not exists is_general tinyint(1) not null;