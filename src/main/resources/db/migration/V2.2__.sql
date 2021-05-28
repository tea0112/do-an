alter table schedules
    add column if not exists start_period int not null;
alter table schedules
    add column if not exists end_period int not null;