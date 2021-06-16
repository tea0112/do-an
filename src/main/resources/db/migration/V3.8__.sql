alter table subjects
add constraint unique (name, subject_type, department_id)