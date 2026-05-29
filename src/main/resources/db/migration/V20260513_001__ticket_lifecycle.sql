create table if not exists ticket_order (
    id bigint primary key,
    ticket_no varchar(64) not null,
    title varchar(255) not null,
    description text not null,
    status varchar(32) not null default 'DRAFT',
    priority varchar(32) not null default 'NORMAL',
    source varchar(32),
    category varchar(64),
    applicant_id bigint,
    applicant_name varchar(64),
    handler_id bigint,
    handler_name varchar(64),
    expected_finish_time timestamp,
    submit_time timestamp,
    accept_time timestamp,
    start_process_time timestamp,
    finish_time timestamp,
    close_time timestamp,
    suspend_time timestamp,
    resume_time timestamp,
    sla_deadline timestamp,
    is_timeout boolean not null default false,
    ai_category varchar(64),
    ai_risk_level varchar(32),
    ai_recommend_dept varchar(64),
    ai_recommend_handler varchar(64),
    ai_estimated_time varchar(64),
    ai_summary text,
    ai_suggestion text,
    reject_reason varchar(500),
    suspend_reason varchar(500),
    reopen_reason varchar(500),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

alter table ticket_order add column if not exists status varchar(32);
alter table ticket_order add column if not exists priority varchar(32);
alter table ticket_order add column if not exists source varchar(32);
alter table ticket_order add column if not exists category varchar(64);
alter table ticket_order add column if not exists applicant_id bigint;
alter table ticket_order add column if not exists applicant_name varchar(64);
alter table ticket_order add column if not exists handler_id bigint;
alter table ticket_order add column if not exists handler_name varchar(64);
alter table ticket_order add column if not exists expected_finish_time timestamp;
alter table ticket_order add column if not exists submit_time timestamp;
alter table ticket_order add column if not exists accept_time timestamp;
alter table ticket_order add column if not exists start_process_time timestamp;
alter table ticket_order add column if not exists finish_time timestamp;
alter table ticket_order add column if not exists close_time timestamp;
alter table ticket_order add column if not exists suspend_time timestamp;
alter table ticket_order add column if not exists resume_time timestamp;
alter table ticket_order add column if not exists sla_deadline timestamp;
alter table ticket_order add column if not exists is_timeout boolean default false;
alter table ticket_order add column if not exists ai_category varchar(64);
alter table ticket_order add column if not exists ai_risk_level varchar(32);
alter table ticket_order add column if not exists ai_recommend_dept varchar(64);
alter table ticket_order add column if not exists ai_recommend_handler varchar(64);
alter table ticket_order add column if not exists ai_estimated_time varchar(64);
alter table ticket_order add column if not exists ai_summary text;
alter table ticket_order add column if not exists ai_suggestion text;
alter table ticket_order add column if not exists reject_reason varchar(500);
alter table ticket_order add column if not exists suspend_reason varchar(500);
alter table ticket_order add column if not exists reopen_reason varchar(500);

alter table ticket_order alter column status set default 'DRAFT';
alter table ticket_order alter column priority set default 'NORMAL';
alter table ticket_order alter column is_timeout set default false;
update ticket_order set status = 'DRAFT' where status = 'NEW';
update ticket_order set status = 'WAIT_CONFIRM' where status = 'RESOLVED';
update ticket_order set status = 'DRAFT' where status is null or status = '';
update ticket_order set priority = 'NORMAL' where priority is null or priority = '';
update ticket_order set is_timeout = false where is_timeout is null;
alter table ticket_order alter column status set not null;
alter table ticket_order alter column priority set not null;
alter table ticket_order alter column is_timeout set not null;

do $$
begin
    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_order' and column_name = 'assignee_id'
    ) then
        execute 'update ticket_order set handler_id = assignee_id where handler_id is null and assignee_id is not null';
    end if;

    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_order' and column_name = 'assignee_name'
    ) then
        execute 'update ticket_order set handler_name = assignee_name where handler_name is null and assignee_name is not null';
    end if;

    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_order' and column_name = 'due_time'
    ) then
        execute 'update ticket_order set expected_finish_time = due_time where expected_finish_time is null and due_time is not null';
    end if;

    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_order' and column_name = 'resolved_time'
    ) then
        execute 'update ticket_order set finish_time = resolved_time where finish_time is null and resolved_time is not null';
    end if;

    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_order' and column_name = 'closed_time'
    ) then
        execute 'update ticket_order set close_time = closed_time where close_time is null and closed_time is not null';
    end if;
end $$;

create table if not exists ticket_flow_record (
    id bigserial primary key,
    ticket_id bigint not null,
    operator_id bigint,
    operator_name varchar(64),
    action varchar(64),
    before_status varchar(32),
    after_status varchar(32),
    remark varchar(500),
    create_time timestamp not null default now()
);

alter table ticket_flow_record add column if not exists ticket_id bigint;
alter table ticket_flow_record add column if not exists operator_id bigint;
alter table ticket_flow_record add column if not exists operator_name varchar(64);
alter table ticket_flow_record add column if not exists action varchar(64);
alter table ticket_flow_record add column if not exists before_status varchar(32);
alter table ticket_flow_record add column if not exists after_status varchar(32);
alter table ticket_flow_record add column if not exists remark varchar(500);
alter table ticket_flow_record add column if not exists create_time timestamp default now();

do $$
begin
    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_flow_record' and column_name = 'from_status'
    ) then
        execute 'update ticket_flow_record set before_status = from_status where before_status is null and from_status is not null';
    end if;

    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_flow_record' and column_name = 'to_status'
    ) then
        execute 'update ticket_flow_record set after_status = to_status where after_status is null and to_status is not null';
    end if;
end $$;

create index if not exists idx_ticket_flow_ticket on ticket_flow_record (ticket_id);

create table if not exists ticket_comment (
    id bigserial primary key,
    ticket_id bigint not null,
    user_id bigint,
    user_name varchar(64),
    content text not null,
    create_time timestamp not null default now()
);

alter table ticket_comment add column if not exists ticket_id bigint;
alter table ticket_comment add column if not exists user_id bigint;
alter table ticket_comment add column if not exists user_name varchar(64);
alter table ticket_comment add column if not exists content text;
alter table ticket_comment add column if not exists create_time timestamp default now();

do $$
begin
    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_comment' and column_name = 'username'
    ) then
        execute 'update ticket_comment set user_name = username where user_name is null and username is not null';
    end if;
end $$;

create index if not exists idx_ticket_comment_ticket on ticket_comment (ticket_id);

create table if not exists ticket_attachment (
    id bigserial primary key,
    ticket_id bigint not null,
    file_name varchar(255),
    file_url varchar(500),
    file_size bigint,
    file_type varchar(64),
    upload_user_id bigint,
    upload_user_name varchar(64),
    create_time timestamp not null default now()
);

alter table ticket_attachment add column if not exists ticket_id bigint;
alter table ticket_attachment add column if not exists file_name varchar(255);
alter table ticket_attachment add column if not exists file_url varchar(500);
alter table ticket_attachment add column if not exists file_size bigint;
alter table ticket_attachment add column if not exists file_type varchar(64);
alter table ticket_attachment add column if not exists upload_user_id bigint;
alter table ticket_attachment add column if not exists upload_user_name varchar(64);
alter table ticket_attachment add column if not exists create_time timestamp default now();

do $$
begin
    if exists (
        select 1 from information_schema.columns
        where table_schema = current_schema() and table_name = 'ticket_attachment' and column_name = 'content_type'
    ) then
        execute 'update ticket_attachment set file_type = content_type where file_type is null and content_type is not null';
    end if;
end $$;

create index if not exists idx_ticket_attachment_ticket on ticket_attachment (ticket_id);
create index if not exists idx_ticket_order_handler on ticket_order (handler_id);
create index if not exists idx_ticket_order_status on ticket_order (status);
