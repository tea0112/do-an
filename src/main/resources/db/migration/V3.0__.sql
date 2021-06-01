alter table semesters
    add constraint unique (term_number, session_id);