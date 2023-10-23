create table if not exists studies
(
    id          int auto_increment primary key,
    grade       float unsigned not null,
    subject_id  int            not null,
    semester_id int            not null,
    student_id  int            not null,
    grade_type  tinyint(1)     not null
);

alter table studies
    add constraint foreign key fk_study_subject (subject_id) references subjects (id);
alter table studies
    add constraint foreign key fk_study_semester (semester_id) references semesters (id);
alter table studies
    add constraint foreign key fk_study_student (student_id) references students (id);
alter table studies add constraint unique unique_key (subject_id, semester_id, student_id, grade_type)