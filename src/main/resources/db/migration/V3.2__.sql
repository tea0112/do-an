SET FOREIGN_KEY_CHECKS = 0;
SET GLOBAL FOREIGN_KEY_CHECKS = 0;

alter table classes
    add if not exists department_id int not null;

alter table classes
    add constraint fk_classes_department foreign key if not exists (department_id)
        references department (id);