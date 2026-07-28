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

create unique index if not exists uk_ticket_order_no on ticket_order (ticket_no);
create index if not exists idx_ticket_order_status on ticket_order (status);
create index if not exists idx_ticket_order_handler on ticket_order (handler_id);
create index if not exists idx_ticket_order_deleted on ticket_order (deleted);

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

create index if not exists idx_ticket_flow_ticket on ticket_flow_record (ticket_id);

create table if not exists ticket_comment (
    id bigserial primary key,
    ticket_id bigint not null,
    user_id bigint,
    user_name varchar(64),
    content text not null,
    create_time timestamp not null default now()
);

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

create index if not exists idx_ticket_attachment_ticket on ticket_attachment (ticket_id);

create table if not exists business_records (
    id bigint primary key,
    module varchar(64) not null,
    title varchar(255) not null,
    code varchar(64) not null,
    owner varchar(64),
    customer varchar(128),
    channel varchar(64),
    status varchar(32) not null,
    priority varchar(32) not null,
    metric varchar(64),
    risk varchar(64),
    description text,
    ai_suggestion text,
    tags jsonb not null default '[]'::jsonb,
    timeline jsonb not null default '[]'::jsonb,
    update_time timestamp not null default now(),
    create_time timestamp not null default now(),
    deleted smallint not null default 0
);

create unique index if not exists uk_business_records_code on business_records (code) where deleted = 0;
create index if not exists idx_business_records_module on business_records (module);
create index if not exists idx_business_records_status on business_records (status);

create table if not exists sys_tenant (
    id bigint primary key,
    tenant_name varchar(128) not null,
    tenant_code varchar(64) not null,
    status varchar(32) not null,
    service_status varchar(32) not null,
    administrator varchar(64),
    administrator_email varchar(128),
    default_organization varchar(128),
    enabled_modules jsonb not null default '[]'::jsonb,
    organization_count int not null default 0,
    user_count int not null default 0,
    agent_count int not null default 0,
    ai_agent_count int not null default 0,
    settings jsonb not null default '{}'::jsonb,
    permissions jsonb not null default '[]'::jsonb,
    remark varchar(512),
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    deleted smallint not null default 0
);

create unique index if not exists uk_sys_tenant_code on sys_tenant (tenant_code) where deleted = 0;

create table if not exists knowledge_category (
    id bigint primary key,
    parent_id bigint,
    title varchar(128) not null,
    sort_order int not null default 0,
    status varchar(32) not null default 'ENABLED',
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    deleted smallint not null default 0
);

create index if not exists idx_knowledge_category_parent on knowledge_category (parent_id);

create table if not exists knowledge_document (
    id bigint primary key,
    category_id bigint,
    category_name varchar(128),
    title varchar(255) not null,
    summary text,
    content text,
    status varchar(32) not null default 'DRAFT',
    tags jsonb not null default '[]'::jsonb,
    version varchar(32),
    owner varchar(64),
    view_count int not null default 0,
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    deleted smallint not null default 0
);

create index if not exists idx_knowledge_document_category on knowledge_document (category_id);
create index if not exists idx_knowledge_document_status on knowledge_document (status);

create table if not exists document_center (
    id bigint primary key,
    file_name varchar(255) not null,
    category varchar(64),
    owner varchar(64),
    file_size varchar(32),
    format varchar(32),
    parse_status varchar(32) not null,
    summary text,
    create_time timestamp not null default now(),
    update_time timestamp not null default now(),
    deleted smallint not null default 0
);

create index if not exists idx_document_center_category on document_center (category);
