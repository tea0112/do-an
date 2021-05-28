CREATE TABLE classes
(
    id         INT          NOT NULL AUTO_INCREMENT,
    name       NVARCHAR(50) NOT NULL UNIQUE,
    class_type INT          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE student_class_relation
(
    id         INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    class_id   INT NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE student_class_relation
    ADD CONSTRAINT fk_student_class_relation_student
        FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE student_class_relation
    ADD CONSTRAINT fk_student_class_relation_class
        FOREIGN KEY (class_id) REFERENCES classes (id);
