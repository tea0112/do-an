CREATE TABLE IF NOT EXISTS sessions (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) CHARACTER SET utf8mb4 UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS departments (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    is_general tinyint(1) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS classes (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(50) CHARACTER SET utf8mb4 UNIQUE NOT NULL,
    session_id int NOT NULL,
    department_id int NOT NULL,
    PRIMARY KEY (id)
--    FOREIGN KEY (session_id) REFERENCES sessions (id),
--    FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE IF NOT EXISTS lecture_halls (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    address varchar(200) CHARACTER SET utf8mb4,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS classrooms (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    lecture_hall_id int NOT NULL,
    PRIMARY KEY (id)
--    FOREIGN KEY (lecture_hall_id) REFERENCES lecture_halls (id)
);

CREATE TABLE IF NOT EXISTS lecturers (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    department_id int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE IF NOT EXISTS schedules (
    id int NOT NULL AUTO_INCREMENT,
    semester_id int NOT NULL,
    end_day date NOT NULL,
    week_day int NOT NULL,
    lecturer_id int NOT NULL,
    class_id int NOT NULL,
    subject_id int NOT NULL,
    start_day date NOT NULL,
    start_period int NOT NULL,
    end_period int NOT NULL,
    period_type int NOT NULL,
    classroom_id int NOT NULL,
    PRIMARY KEY (id)
--    FOREIGN KEY (class_id) REFERENCES classes (id),
--    FOREIGN KEY (lecturer_id) REFERENCES lecturers (id),
--    FOREIGN KEY (class_id) REFERENCES classes (id),
--    FOREIGN KEY (semester_id) REFERENCES semesters (id),
--    FOREIGN KEY (subject_id) REFERENCES subjects (id),
--    FOREIGN KEY (classroom_id) REFERENCES classrooms (id)
    );

CREATE TABLE IF NOT EXISTS semesters (
    id int NOT NULL AUTO_INCREMENT,
    term_number int NOT NULL UNIQUE,
    start_day date NOT NULL,
    end_day date NOT NULL,
    session_id int NOT NULL,
    PRIMARY KEY (id)
--    FOREIGN KEY (session_id) REFERENCES sessions (id)
    );

CREATE TABLE IF NOT EXISTS students (
    id int NOT NULL AUTO_INCREMENT,
    last_name varchar(50) CHARACTER SET utf8mb4 NOT NULL,
    first_name varchar(50) CHARACTER SET utf8mb4 NOT NULL,
    birth date NOT NULL,
    place varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
    phone_number varchar(20) CHARACTER SET utf8mb4 NOT NULL,
    user_id int NOT NULL,
    session_id int NOT NULL,
    gender tinyint(1) NOT NULL,
    PRIMARY KEY (id)
--    FOREIGN KEY (user_id) REFERENCES users (id),
--    FOREIGN KEY (session_id) REFERENCES sessions (id)
    );

CREATE TABLE IF NOT EXISTS student_class_relations (
    student_id int NOT NULL,
    class_id int NOT NULL,
    PRIMARY KEY (student_id, class_id)
);

CREATE TABLE IF NOT EXISTS subjects (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(100) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    subject_type int NOT NULL UNIQUE,
    department_id int NOT NULL,
    PRIMARY KEY (id)
--    FOREIGN KEY (department_id) REFERENCES departments (id)
    );

CREATE TABLE IF NOT EXISTS users (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(200) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    password varchar(200) CHARACTER SET utf8mb4 NOT NULL,
    is_admin bit(1) NOT NULL,
    PRIMARY KEY (id)
    );

ALTER TABLE classes
    ADD CONSTRAINT fk_classes_sessions FOREIGN KEY (session_id) REFERENCES sessions (id),
  ADD CONSTRAINT fk_classes_department FOREIGN KEY (department_id) REFERENCES departments (id);

ALTER TABLE classrooms
    ADD CONSTRAINT fk_classrooms_lecture_halls FOREIGN KEY (lecture_hall_id) REFERENCES lecture_halls (id);

ALTER TABLE schedules
    ADD CONSTRAINT fk_schedule_class FOREIGN KEY (class_id) REFERENCES classes (id),
  ADD CONSTRAINT fk_schedule_lecturer FOREIGN KEY (lecturer_id) REFERENCES lecturers (id),
  ADD CONSTRAINT fk_schedule_semester FOREIGN KEY (semester_id) REFERENCES semesters (id),
  ADD CONSTRAINT fk_schedule_subject FOREIGN KEY (subject_id) REFERENCES subjects (id),
  ADD CONSTRAINT fk_schedules_semesters FOREIGN KEY (semester_id) REFERENCES semesters (id),
  ADD CONSTRAINT fk_schedules_classrooms FOREIGN KEY (classroom_id) REFERENCES classrooms (id);

ALTER TABLE semesters
    ADD CONSTRAINT fk_semester_session FOREIGN KEY (session_id) REFERENCES sessions (id);

ALTER TABLE students
    ADD CONSTRAINT fk_student_session FOREIGN KEY (session_id) REFERENCES sessions (id),
  ADD CONSTRAINT fk_students_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE student_class_relations
    ADD CONSTRAINT fk_student_class_relation_class FOREIGN KEY (class_id) REFERENCES classes (id),
  ADD CONSTRAINT fk_student_class_relation_student FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE subjects
    ADD CONSTRAINT fk_subjects_departments FOREIGN KEY (department_id) REFERENCES departments (id);

CREATE TABLE IF NOT EXISTS studies
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    grade       FLOAT UNSIGNED NOT NULL,
    subject_id  INT            NOT NULL,
    semester_id INT            NOT NULL,
    student_id  INT            NOT NULL,
    grade_type  TINYINT(1)     NOT NULL UNIQUE
);

ALTER TABLE studies
    ADD CONSTRAINT FOREIGN KEY fk_study_subject (subject_id) REFERENCES subjects (id);
ALTER TABLE studies
    ADD CONSTRAINT FOREIGN KEY fk_study_semester (semester_id) REFERENCES semesters (id);
ALTER TABLE studies
    ADD CONSTRAINT FOREIGN KEY fk_study_student (student_id) REFERENCES students (id);