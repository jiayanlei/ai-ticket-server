create table if not exists sys_user (
    id bigint primary key,
    username varchar(64) not null,
    password varchar(255) not null,
    nickname varchar(64) not null,
    email varchar(128),
    mobile varchar(32),
    avatar varchar(512),
    dept_id bigint,
    status varchar(32) not null default 'ENABLED',
    last_login_time timestamp,
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create unique index if not exists uk_sys_user_username on sys_user (username) where deleted = 0;
create index if not exists idx_sys_user_dept on sys_user (dept_id);

create table if not exists sys_role (
    id bigint primary key,
    role_name varchar(64) not null,
    role_code varchar(64) not null,
    sort_order int not null default 0,
    status varchar(32) not null default 'ENABLED',
    remark varchar(512),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create unique index if not exists uk_sys_role_code on sys_role (role_code) where deleted = 0;

create table if not exists sys_user_role (
    id bigint primary key,
    user_id bigint not null,
    role_id bigint not null,
    create_time timestamp not null default now()
);

create unique index if not exists uk_sys_user_role on sys_user_role (user_id, role_id);
create index if not exists idx_sys_user_role_role on sys_user_role (role_id);

create table if not exists sys_menu (
    id bigint primary key,
    parent_id bigint not null default 0,
    menu_name varchar(64) not null,
    menu_type varchar(32) not null,
    path varchar(255),
    component varchar(255),
    perms varchar(128),
    icon varchar(128),
    sort_order int not null default 0,
    visible boolean not null default true,
    status varchar(32) not null default 'ENABLED',
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create index if not exists idx_sys_menu_parent on sys_menu (parent_id);
create index if not exists idx_sys_menu_perms on sys_menu (perms);

create table if not exists sys_dept (
    id bigint primary key,
    parent_id bigint not null default 0,
    dept_name varchar(64) not null,
    dept_code varchar(64) not null,
    leader varchar(64),
    phone varchar(32),
    email varchar(128),
    sort_order int not null default 0,
    status varchar(32) not null default 'ENABLED',
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create unique index if not exists uk_sys_dept_code on sys_dept (dept_code) where deleted = 0;
create index if not exists idx_sys_dept_parent on sys_dept (parent_id);

create table if not exists sys_login_log (
    id bigint primary key,
    username varchar(64),
    login_ip varchar(64),
    user_agent varchar(512),
    status varchar(32),
    message varchar(512),
    login_time timestamp not null default now(),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create index if not exists idx_sys_login_log_username on sys_login_log (username);
create index if not exists idx_sys_login_log_time on sys_login_log (login_time);

create table if not exists ticket_order (
    id bigint primary key,
    ticket_no varchar(64) not null,
    title varchar(255) not null,
    description text not null,
    priority varchar(32) not null default 'NORMAL',
    status varchar(32) not null default 'NEW',
    source varchar(64),
    category varchar(64),
    applicant_id bigint,
    applicant_name varchar(64),
    assignee_id bigint,
    assignee_name varchar(64),
    due_time timestamp,
    resolved_time timestamp,
    closed_time timestamp,
    ai_summary text,
    ai_risk_level varchar(32),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create unique index if not exists uk_ticket_order_no on ticket_order (ticket_no);
create index if not exists idx_ticket_order_status on ticket_order (status);
create index if not exists idx_ticket_order_assignee on ticket_order (assignee_id);
create index if not exists idx_ticket_order_deleted on ticket_order (deleted);

create table if not exists ticket_flow_record (
    id bigint primary key,
    ticket_id bigint not null,
    from_status varchar(32),
    to_status varchar(32),
    operator_id bigint,
    operator_name varchar(64),
    action varchar(64),
    remark varchar(512),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create index if not exists idx_ticket_flow_ticket on ticket_flow_record (ticket_id);

create table if not exists ticket_comment (
    id bigint primary key,
    ticket_id bigint not null,
    user_id bigint,
    username varchar(64),
    content text not null,
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create index if not exists idx_ticket_comment_ticket on ticket_comment (ticket_id);

create table if not exists ticket_attachment (
    id bigint primary key,
    ticket_id bigint not null,
    file_name varchar(255) not null,
    original_name varchar(255),
    file_url varchar(1024) not null,
    file_size bigint,
    content_type varchar(128),
    storage_provider varchar(32),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    create_by bigint,
    update_by bigint,
    deleted smallint not null default 0
);

create index if not exists idx_ticket_attachment_ticket on ticket_attachment (ticket_id);
