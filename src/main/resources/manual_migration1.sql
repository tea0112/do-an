CREATE TABLE IF NOT EXISTS `classes` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) CHARACTER SET utf8 NOT NULL,
    `session_id` int(11) NOT NULL,
    `department_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`,`session_id`,`department_id`),
    KEY `session_id` (`session_id`),
    KEY `fk_classes_department` (`department_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `classrooms` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 NOT NULL,
    `lecture_hall_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`),
    KEY `lecture_hall_id` (`lecture_hall_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `departments` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 NOT NULL,
    `is_general` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `lecturers` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 NOT NULL,
    `department_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`,`department_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `lecture_halls` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 NOT NULL,
    `address` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `schedules` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `semester_id` int(11) NOT NULL,
    `end_day` date NOT NULL,
    `week_day` int(11) NOT NULL,
    `lecturer_id` int(11) NOT NULL,
    `class_id` int(11) NOT NULL,
    `subject_id` int(11) NOT NULL,
    `start_day` date NOT NULL,
    `start_period` int(11) NOT NULL,
    `end_period` int(11) NOT NULL,
    `period_type` int(11) NOT NULL,
    `classroom_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_schedule_lecturer` (`lecturer_id`),
    KEY `fk_schedule_class` (`class_id`),
    KEY `fk_schedule_semester` (`semester_id`),
    KEY `fk_schedule_subject` (`subject_id`),
    KEY `classroom_id` (`classroom_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `semesters` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `term_number` int(11) NOT NULL,
    `start_day` date NOT NULL,
    `end_day` date NOT NULL,
    `session_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `term_number` (`term_number`,`session_id`),
    KEY `fk_semester_session` (`session_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `sessions` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(1000) CHARACTER SET utf8 NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`),
    UNIQUE KEY `name_2` (`name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `students` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `last_name` varchar(50) CHARACTER SET utf8 NOT NULL,
    `first_name` varchar(50) CHARACTER SET utf8 NOT NULL,
    `birth` date NOT NULL,
    `place` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
    `phone_number` varchar(20) CHARACTER SET utf8 NOT NULL,
    `user_id` int(11) NOT NULL,
    `session_id` int(11) NOT NULL,
    `gender` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `fk_student_session` (`session_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `student_class_relations` (
    `student_id` int(11) NOT NULL,
    `class_id` int(11) NOT NULL,
    PRIMARY KEY (`student_id`,`class_id`),
    KEY `fk_student_class_relation_class` (`class_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `subjects` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) CHARACTER SET utf8 NOT NULL,
    `subject_type` int(11) NOT NULL,
    `department_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`,`subject_type`,`department_id`),
    KEY `fk_subjects_departments` (`department_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(500) CHARACTER SET utf8 NOT NULL,
    `password` varchar(500) CHARACTER SET utf8 NOT NULL,
    `is_admin` bit(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`username`)
    ) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4;

ALTER TABLE `classes`
    ADD CONSTRAINT `fk_classes_sessions` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`),
  ADD CONSTRAINT `fk_classes_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`);

ALTER TABLE `classrooms`
    ADD CONSTRAINT `fk_classrooms_lecture_halls` FOREIGN KEY (`lecture_hall_id`) REFERENCES `lecture_halls` (`id`);

ALTER TABLE `schedules`
    ADD CONSTRAINT `fk_schedule_class` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  ADD CONSTRAINT `fk_schedule_lecturer` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`id`),
  ADD CONSTRAINT `fk_schedule_semester` FOREIGN KEY (`semester_id`) REFERENCES `semesters` (`id`),
  ADD CONSTRAINT `fk_schedule_subject` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`),
  ADD CONSTRAINT `fk_schedules_semesters` FOREIGN KEY (`semester_id`) REFERENCES `semesters` (`id`),
  ADD CONSTRAINT `fk_schedules_classrooms` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`);

ALTER TABLE `semesters`
    ADD CONSTRAINT `fk_semester_session` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`);

ALTER TABLE `students`
    ADD CONSTRAINT `fk_student_session` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`),
  ADD CONSTRAINT `fk_students_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `student_class_relations`
    ADD CONSTRAINT `fk_student_class_relation_class` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  ADD CONSTRAINT `fk_student_class_relation_student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

ALTER TABLE `subjects`
    ADD CONSTRAINT `fk_subjects_departments` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`);