SET FOREIGN_KEY_CHECKS = 0;
SET GLOBAL FOREIGN_KEY_CHECKS = 0;

drop table if exists student_schedule_relation;

alter table students
    drop constraint if exists students_ibfk_1;

alter table students
    drop column if exists retraining_id;

alter table students
    drop column if exists avatar;

drop table if exists retrainings;

alter table semesters
    drop constraint if exists semesters_ibfk_1;

alter table semesters
    drop column if exists fee_id;

drop table if exists fees;

alter table classes
    drop column if exists class_type;

