CREATE TABLE IF NOT EXISTS svmt_notes (
    id bigserial not null primary key,
    title text not null default '',
    description text default '',
    created_by varchar not null,
    created_at timestamptz not null default now(),
    updated_by varchar not null,
    updated_at timestamptz not null default now(),
    constraint fk_notes_created_by foreign key (created_by) references SAVESTMENT_USERS(USER_ID),
    constraint fk_notes_updated_by foreign key (updated_by) references SAVESTMENT_USERS(USER_ID)
);