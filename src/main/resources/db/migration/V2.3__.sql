alter table schedules
    drop column if exists start_time;
alter table schedules
    drop column if exists end_time;
alter table schedules
    add column if not exists is_morning bit;