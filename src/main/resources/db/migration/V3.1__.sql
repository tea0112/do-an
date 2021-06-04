SET FOREIGN_KEY_CHECKS=0;
SET GLOBAL FOREIGN_KEY_CHECKS=0;

alter table subjects
    add if not exists department_id int not null;

alter table subjects
    add constraint fk_subjects_departments
        foreign key if not exists (department_id) references department (id);
