CREATE TABLE students
(
    id            INT      NOT NULL AUTO_INCREMENT,
    last_name     NVARCHAR(50) NOT NULL,
    first_name    NVARCHAR(50) NOT NULL,
    birth         DATETIME NOT NULL,
    place         NVARCHAR(100),
    phone_number  NVARCHAR(20) NOT NULL,
    retraining_id INT      NOT NULL,
    user_id       INT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE student_schedule_relation
(
    student_id  INT NOT NULL,
    schedule_id INT NOT NULL,
    PRIMARY KEY (student_id, schedule_id)
);

CREATE TABLE users
(
    id       INT NOT NULL AUTO_INCREMENT,
    email    NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(500) NOT NULL,
    is_admin BIT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE retrainings
(
    id              INT  NOT NULL AUTO_INCREMENT,
    retraining_type INT  NOT NULL,
    start_day       DATE NOT NULL,
    end_date        DATE NOT NULL,
    fee_id          INT  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE schedules
(
    id          INT  NOT NULL AUTO_INCREMENT,
    name        NVARCHAR(100),
    start_day   DATE NOT NULL,
    end_date    DATE NOT NULL,
    semester_id INT  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE subjects
(
    id            INT NOT NULL AUTO_INCREMENT,
    name          NVARCHAR(100) NOT NULL,
    grade         FLOAT,
    schedule_id   INT NOT NULL,
    retraining_id INT NOT NULL,
    fee_id        INT NOT NULL,
    semester_id   INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE fees
(
    id              INT  NOT NULL AUTO_INCREMENT,
    amount_of_money INT  NOT NULL,
    fee_type        INT  NOT NULL,
    start_day       DATE NOT NULL,
    end_date        DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE semesters
(
    id          INT  NOT NULL AUTO_INCREMENT,
    term_number INT  NOT NULL,
    start_day   DATE NOT NULL,
    end_date    DATE NOT NULL,
    fee_id      INT  NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE students
    ADD CONSTRAINT FOREIGN KEY (retraining_id) REFERENCES retrainings (id);

ALTER TABLE students
    ADD CONSTRAINT FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE retrainings
    ADD CONSTRAINT FOREIGN KEY (fee_id) REFERENCES fees (id);

ALTER TABLE semesters
    ADD CONSTRAINT FOREIGN KEY (fee_id) REFERENCES fees (id);

ALTER TABLE schedules
    ADD CONSTRAINT FOREIGN KEY (semester_id) REFERENCES semesters (id);

ALTER TABLE subjects
    ADD CONSTRAINT FOREIGN KEY (schedule_id) REFERENCES schedules (id);

ALTER TABLE subjects
    ADD CONSTRAINT FOREIGN KEY (retraining_id) REFERENCES retrainings (id);

ALTER TABLE subjects
    ADD CONSTRAINT FOREIGN KEY (fee_id) REFERENCES fees (id);

ALTER TABLE subjects
    ADD CONSTRAINT FOREIGN KEY (semester_id) REFERENCES semesters (id);

ALTER TABLE student_schedule_relation
    ADD CONSTRAINT FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE student_schedule_relation
    ADD CONSTRAINT FOREIGN KEY (schedule_id) REFERENCES schedules (id);
