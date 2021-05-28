CREATE TABLE IF NOT EXISTS lecturer
(
    id INT NOT NULL AUTO_INCREMENT,
    name NVARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
# delete column schedules
ALTER TABLE schedules
    DROP COLUMN IF EXISTS name;
ALTER TABLE schedules
    DROP COLUMN IF EXISTS start_day;
ALTER TABLE schedules
    DROP COLUMN IF EXISTS end_date;
# add column schedules
# IF NOT EXISTS (
# SELECT null FROM information_schema.COLUMNS
# WHERE table_name = 'fees' AND column_name = 'fee_type') THEN
#
# END IF
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS start_day DATE NOT NULL;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS end_day DATE NOT NULL;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS week_day INT NOT NULL;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS start_time TIME NOT NULL;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS end_time TIME NOT NULL;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS lecturer_id INT NOT NULL;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS class_id INT NOT NULL;;
ALTER TABLE schedules
    ADD COLUMN IF NOT EXISTS subject_id INT NOT NULL;
# add constraint
ALTER TABLE schedules
    ADD CONSTRAINT fk_schedule_lecturer
        FOREIGN KEY IF NOT EXISTS (lecturer_id) REFERENCES lecturer(id);
ALTER TABLE schedules
    ADD CONSTRAINT fk_schedule_class
        FOREIGN KEY IF NOT EXISTS (class_id) REFERENCES classes(id);
ALTER TABLE schedules
    ADD CONSTRAINT fk_schedule_semester
        FOREIGN KEY IF NOT EXISTS (semester_id) REFERENCES semesters(id);
ALTER TABLE schedules
    ADD CONSTRAINT fk_schedule_subject
        FOREIGN KEY IF NOT EXISTS (subject_id) REFERENCES subjects(id);
