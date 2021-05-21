ALTER TABLE students
MODIFY COLUMN birth DATE NOT NULL;

CREATE TABLE sessions
(
    id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(1000) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

ALTER TABLE students
ADD session_id INT NOT NULL;

ALTER TABLE students
ADD CONSTRAINT fk_student_session
FOREIGN KEY (session_id) REFERENCES sessions(id);