ALTER TABLE subjects
    DROP CONSTRAINT IF EXISTS subjects_ibfk_2;
ALTER TABLE subjects
    DROP CONSTRAINT IF EXISTS subjects_ibfk_3;
ALTER TABLE subjects
    DROP COLUMN IF EXISTS retraining_id;
ALTER TABLE subjects
    DROP COLUMN IF EXISTS fee_id;