-- Align sys_menu with frontend mock routes.
-- Source: /Users/lei/Desktop/ai-ticket-web/src/mock/menu.ts
-- Scope:
--   1. Add missing visible routes.
--   2. Add hidden compatibility routes.
--   3. Add button permission records.
--   4. Update existing same-path records to frontend mock perms/components, with Chinese menu names.
-- This script does not delete existing extra database menus.

begin;

-- Reuse existing top-level directories where the business group matches the mock menu model.
update sys_menu
set menu_name = '服务中心',
    menu_type = 'DIR',
    path = null,
    component = null,
    perms = null,
    icon = 'customer-service',
    sort_order = 2,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 2 and deleted = 0;

update sys_menu
set menu_name = 'AI 数据中心',
    menu_type = 'DIR',
    path = null,
    component = null,
    perms = null,
    icon = 'robot',
    sort_order = 5,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 3 and deleted = 0;

update sys_menu
set menu_name = '知识中心',
    menu_type = 'DIR',
    path = null,
    component = null,
    perms = null,
    icon = 'book',
    sort_order = 8,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 4 and deleted = 0;

update sys_menu
set menu_name = '数据分析',
    menu_type = 'DIR',
    path = null,
    component = null,
    perms = null,
    icon = 'bar-chart',
    sort_order = 9,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 5 and deleted = 0;

update sys_menu
set menu_name = '系统管理',
    menu_type = 'DIR',
    path = null,
    component = null,
    perms = null,
    icon = 'setting',
    sort_order = 10,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 7 and deleted = 0;

-- Update existing routes that already exist in the database but differ from frontend mock.
update sys_menu
set parent_id = 0,
    menu_name = '工作台',
    menu_type = 'MENU',
    path = '/dashboard/workbench',
    component = 'dashboard/workbench/index',
    perms = 'dashboard:workbench:view',
    icon = 'dashboard',
    sort_order = 1,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 20 and deleted = 0;

update sys_menu
set parent_id = 2,
    menu_name = '旧版工单列表',
    menu_type = 'MENU',
    path = '/ticket/list',
    component = 'ticket/list/index',
    perms = 'service:ticket:view',
    icon = 'unordered-list',
    sort_order = 90,
    visible = false,
    status = 'ENABLED',
    update_time = now()
where id = 22 and deleted = 0;

update sys_menu
set parent_id = 2,
    menu_name = '创建工单',
    menu_type = 'MENU',
    path = '/ticket/create',
    component = 'ticket/create/index',
    perms = 'service:ticket:create',
    icon = 'plus-circle',
    sort_order = 91,
    visible = false,
    status = 'ENABLED',
    update_time = now()
where id = 23 and deleted = 0;

update sys_menu
set parent_id = 2,
    menu_name = '旧版工单回收站',
    menu_type = 'MENU',
    path = '/ticket/trash',
    component = 'ticket/trash/index',
    perms = 'service:ticket:delete',
    icon = 'delete',
    sort_order = 92,
    visible = false,
    status = 'ENABLED',
    update_time = now()
where id = 24 and deleted = 0;

update sys_menu
set parent_id = 3,
    menu_name = '分析结果',
    menu_type = 'MENU',
    path = '/ai/result',
    component = 'ai/result/index',
    perms = 'ai:result:view',
    icon = 'file-search',
    sort_order = 6,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 26 and deleted = 0;

update sys_menu
set parent_id = 4,
    menu_name = '文档中心',
    menu_type = 'MENU',
    path = '/knowledge/documents',
    component = 'knowledge/documents/index',
    perms = 'knowledge:document:list',
    icon = 'file-search',
    sort_order = 3,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 27 and deleted = 0;

update sys_menu
set parent_id = 4,
    menu_name = 'FAQ 管理',
    menu_type = 'MENU',
    path = '/knowledge/faq',
    component = 'knowledge/faq/index',
    perms = 'knowledge:faq:view',
    icon = 'question-circle',
    sort_order = 4,
    visible = false,
    status = 'ENABLED',
    update_time = now()
where id = 28 and deleted = 0;

update sys_menu
set parent_id = 5,
    menu_name = '数据驾驶舱',
    menu_type = 'MENU',
    path = '/analytics/cockpit',
    component = 'analytics/cockpit/index',
    perms = 'analytics:cockpit:view',
    icon = 'dashboard',
    sort_order = 3,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 29 and deleted = 0;

update sys_menu
set parent_id = 7,
    menu_name = '用户管理',
    menu_type = 'MENU',
    path = '/system/users',
    component = 'system/users/index',
    perms = 'system:user:list',
    icon = 'user',
    sort_order = 5,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 31 and deleted = 0;

update sys_menu
set parent_id = 7,
    menu_name = '角色管理',
    menu_type = 'MENU',
    path = '/system/roles',
    component = 'system/roles/index',
    perms = 'system:role:list',
    icon = 'team',
    sort_order = 6,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 32 and deleted = 0;

update sys_menu
set parent_id = 7,
    menu_name = '部门管理',
    menu_type = 'MENU',
    path = '/system/depts',
    component = 'system/depts/index',
    perms = 'system:dept:list',
    icon = 'apartment',
    sort_order = 7,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 33 and deleted = 0;

update sys_menu
set parent_id = 7,
    menu_name = '菜单管理',
    menu_type = 'MENU',
    path = '/system/menus',
    component = 'system/menus/index',
    perms = 'system:menu:list',
    icon = 'menu',
    sort_order = 8,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 34 and deleted = 0;

update sys_menu
set parent_id = 4,
    menu_name = '知识库',
    menu_type = 'MENU',
    path = '/knowledge/base',
    component = 'knowledge/base/index',
    perms = 'knowledge:base:view',
    icon = 'book',
    sort_order = 1,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 35 and deleted = 0;

update sys_menu
set parent_id = 4,
    menu_name = '知识库管理',
    menu_type = 'MENU',
    path = '/knowledge/manage',
    component = 'knowledge/manage/index',
    perms = 'knowledge:document:list',
    icon = 'read',
    sort_order = 2,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 36 and deleted = 0;

-- Insert missing top-level directories.
insert into sys_menu (
    id, parent_id, menu_name, menu_type, path, component, perms, icon, sort_order, visible, status, create_time, update_time, deleted
) values
    (1000, 0, '全渠道中心', 'DIR', null, null, null, 'inbox', 3, true, 'ENABLED', now(), now(), 0),
    (1010, 0, '坐席运营', 'DIR', null, null, null, 'team', 4, true, 'ENABLED', now(), now(), 0),
    (1020, 0, '客户中心', 'DIR', null, null, null, 'user', 6, true, 'ENABLED', now(), now(), 0),
    (1030, 0, '租户管理', 'DIR', null, null, null, 'deployment-unit', 7, true, 'ENABLED', now(), now(), 0)
on conflict (id) do update set
    parent_id = excluded.parent_id,
    menu_name = excluded.menu_name,
    menu_type = excluded.menu_type,
    path = excluded.path,
    component = excluded.component,
    perms = excluded.perms,
    icon = excluded.icon,
    sort_order = excluded.sort_order,
    visible = excluded.visible,
    status = excluded.status,
    update_time = now(),
    deleted = 0;

-- Insert missing page routes and hidden compatibility routes.
insert into sys_menu (
    id, parent_id, menu_name, menu_type, path, component, perms, icon, sort_order, visible, status, create_time, update_time, deleted
) values
    (1101, 2, '工单中心', 'MENU', '/service/tickets', 'service/tickets/index', 'service:ticket:view', 'ticket', 1, true, 'ENABLED', now(), now(), 0),
    (1102, 2, '创建工单', 'MENU', '/service/tickets/create', 'ticket/create/index', 'service:ticket:create', 'plus-circle', 2, false, 'ENABLED', now(), now(), 0),
    (1103, 2, '呼叫中心', 'MENU', '/service/calls', 'service/calls/index', 'service:call:view', 'phone', 3, true, 'ENABLED', now(), now(), 0),
    (1104, 2, '在线会话中心', 'MENU', '/service/live-chat', 'service/live-chat/index', 'service:chat:view', 'message', 4, true, 'ENABLED', now(), now(), 0),
    (1201, 1000, '邮件中心', 'MENU', '/omnichannel/email', 'omnichannel/email/index', 'omnichannel:email:view', 'mail', 1, true, 'ENABLED', now(), now(), 0),
    (1202, 1000, '短信中心', 'MENU', '/omnichannel/sms', 'omnichannel/sms/index', 'omnichannel:sms:view', 'mobile', 2, true, 'ENABLED', now(), now(), 0),
    (1301, 1010, '坐席中心', 'MENU', '/operations/agents', 'operations/agents/index', 'operations:agent:view', 'team', 1, true, 'ENABLED', now(), now(), 0),
    (1302, 1010, '排班管理', 'MENU', '/operations/scheduling', 'operations/scheduling/index', 'operations:schedule:view', 'schedule', 2, true, 'ENABLED', now(), now(), 0),
    (1303, 1010, '绩效中心', 'MENU', '/operations/performance', 'operations/performance/index', 'operations:performance:view', 'trophy', 3, true, 'ENABLED', now(), now(), 0),
    (1304, 1010, 'AI 质检', 'MENU', '/operations/quality', 'operations/quality/index', 'operations:quality:view', 'safety', 4, true, 'ENABLED', now(), now(), 0),
    (1305, 1010, '培训中心', 'MENU', '/operations/training', 'operations/training/index', 'operations:training:view', 'read', 5, true, 'ENABLED', now(), now(), 0),
    (1401, 3, 'AI 问答', 'MENU', '/ai/chat', 'console/codex/index', 'ai:agent:chat', 'robot', 1, true, 'ENABLED', now(), now(), 0),
    (1402, 3, '工作流中心', 'MENU', '/ai/workflows', 'ai/workflows/index', 'ai:workflow:view', 'branches', 3, true, 'ENABLED', now(), now(), 0),
    (1403, 3, '提示词中心', 'MENU', '/ai/prompts', 'ai/prompts/index', 'ai:prompt:view', 'code', 4, true, 'ENABLED', now(), now(), 0),
    (1404, 3, '模型中心', 'MENU', '/ai/models', 'ai/models/index', 'ai:model:view', 'experiment', 5, true, 'ENABLED', now(), now(), 0),
    (1405, 3, '对话记录', 'MENU', '/ai/conversation', 'conversation/records/index', 'ai:conversation:list', 'profile', 7, true, 'ENABLED', now(), now(), 0),
    (1501, 1020, '客户 360', 'MENU', '/customers/360', 'customers/360/index', 'customer:360:view', 'user', 1, true, 'ENABLED', now(), now(), 0),
    (1502, 1020, '客户旅程', 'MENU', '/customers/journey', 'customers/journey/index', 'customer:journey:view', 'deployment-unit', 2, true, 'ENABLED', now(), now(), 0),
    (1601, 4, '旧版知识库入口', 'MENU', '/knowledge', 'knowledge/base/index', 'knowledge:base:view', 'book', 90, false, 'ENABLED', now(), now(), 0),
    (1701, 5, '运营分析', 'MENU', '/analytics/operations', 'analytics/operations/index', 'analytics:operations:view', 'line-chart', 1, true, 'ENABLED', now(), now(), 0),
    (1702, 5, 'BI 报表', 'MENU', '/analytics/bi', 'analytics/bi/index', 'analytics:bi:view', 'bar-chart', 2, true, 'ENABLED', now(), now(), 0),
    (1703, 5, 'SLA 管理', 'MENU', '/analytics/sla', 'analytics/sla/index', 'analytics:sla:view', 'clock', 4, true, 'ENABLED', now(), now(), 0),
    (1704, 5, '风险预警', 'MENU', '/analytics/risk', 'analytics/risk/index', 'analytics:risk:view', 'warning', 5, true, 'ENABLED', now(), now(), 0),
    (1705, 5, '系统监控', 'MENU', '/analytics/monitoring', 'analytics/monitoring/index', 'analytics:monitoring:view', 'monitor', 6, true, 'ENABLED', now(), now(), 0),
    (1706, 5, '告警中心', 'MENU', '/analytics/alerts', 'analytics/alerts/index', 'analytics:alert:view', 'alert', 7, true, 'ENABLED', now(), now(), 0),
    (1801, 1030, '租户中心', 'MENU', '/tenants', 'system/tenants/index', 'tenant:center:view', 'deployment-unit', 1, true, 'ENABLED', now(), now(), 0),
    (1901, 7, '权限中心', 'MENU', '/system/permissions', 'system/permissions/index', 'system:permission:view', 'shield', 1, true, 'ENABLED', now(), now(), 0),
    (1902, 7, '审计中心', 'MENU', '/system/audit', 'system/audit/index', 'system:audit:view', 'file-search', 2, true, 'ENABLED', now(), now(), 0),
    (1903, 7, '系统管理总览', 'MENU', '/system/management', 'system/management/index', 'system:management:view', 'setting', 3, true, 'ENABLED', now(), now(), 0),
    (1904, 7, '开放平台', 'MENU', '/system/open-platform', 'system/open-platform/index', 'system:open-platform:view', 'api', 4, true, 'ENABLED', now(), now(), 0),
    (1905, 7, '系统配置', 'MENU', '/system/settings', 'system/settings/index', 'system:settings:view', 'setting', 9, true, 'ENABLED', now(), now(), 0),
    (1906, 7, '用户权限', 'MENU', '/system/permission', 'system/permission/index', 'system:permission:view', 'shield', 10, true, 'ENABLED', now(), now(), 0)
on conflict (id) do update set
    parent_id = excluded.parent_id,
    menu_name = excluded.menu_name,
    menu_type = excluded.menu_type,
    path = excluded.path,
    component = excluded.component,
    perms = excluded.perms,
    icon = excluded.icon,
    sort_order = excluded.sort_order,
    visible = excluded.visible,
    status = excluded.status,
    update_time = now(),
    deleted = 0;

-- Insert missing button permissions.
insert into sys_menu (
    id, parent_id, menu_name, menu_type, path, component, perms, icon, sort_order, visible, status, create_time, update_time, deleted
) values
    (2101, 1101, '创建工单', 'BUTTON', null, null, 'service:ticket:create', null, 1, false, 'ENABLED', now(), now(), 0),
    (2102, 1101, '更新工单', 'BUTTON', null, null, 'service:ticket:update', null, 2, false, 'ENABLED', now(), now(), 0),
    (2103, 1101, '审批工单', 'BUTTON', null, null, 'service:ticket:approve', null, 3, false, 'ENABLED', now(), now(), 0),
    (2104, 1101, '导出工单', 'BUTTON', null, null, 'service:ticket:export', null, 4, false, 'ENABLED', now(), now(), 0),
    (2111, 1103, '监听通话', 'BUTTON', null, null, 'service:call:monitor', null, 1, false, 'ENABLED', now(), now(), 0),
    (2112, 1103, '强插通话', 'BUTTON', null, null, 'service:call:barge', null, 2, false, 'ENABLED', now(), now(), 0),
    (2113, 1103, '强制断开', 'BUTTON', null, null, 'service:call:disconnect', null, 3, false, 'ENABLED', now(), now(), 0),
    (2121, 1402, '发布工作流', 'BUTTON', null, null, 'ai:workflow:publish', null, 1, false, 'ENABLED', now(), now(), 0),
    (2122, 1404, '更新模型路由', 'BUTTON', null, null, 'ai:model:route', null, 1, false, 'ENABLED', now(), now(), 0),
    (2131, 36, '知识新增', 'BUTTON', null, null, 'knowledge:document:create', null, 1, false, 'ENABLED', now(), now(), 0),
    (2132, 36, '知识编辑', 'BUTTON', null, null, 'knowledge:document:update', null, 2, false, 'ENABLED', now(), now(), 0),
    (2133, 36, '知识删除', 'BUTTON', null, null, 'knowledge:document:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (2134, 36, '知识发布', 'BUTTON', null, null, 'knowledge:document:publish', null, 4, false, 'ENABLED', now(), now(), 0),
    (2135, 36, '知识下架', 'BUTTON', null, null, 'knowledge:document:offline', null, 5, false, 'ENABLED', now(), now(), 0),
    (2141, 1801, '创建租户', 'BUTTON', null, null, 'tenant:center:create', null, 1, false, 'ENABLED', now(), now(), 0),
    (2142, 1801, '更新租户', 'BUTTON', null, null, 'tenant:center:update', null, 2, false, 'ENABLED', now(), now(), 0),
    (2143, 1801, '变更租户状态', 'BUTTON', null, null, 'tenant:center:status', null, 3, false, 'ENABLED', now(), now(), 0),
    (2144, 1801, '管理租户成员', 'BUTTON', null, null, 'tenant:center:member', null, 4, false, 'ENABLED', now(), now(), 0),
    (2145, 1801, '管理租户权限', 'BUTTON', null, null, 'tenant:center:permission', null, 5, false, 'ENABLED', now(), now(), 0),
    (2146, 1801, '查看租户审计', 'BUTTON', null, null, 'tenant:center:audit', null, 6, false, 'ENABLED', now(), now(), 0),
    (2147, 1801, '导出租户数据', 'BUTTON', null, null, 'tenant:center:export', null, 7, false, 'ENABLED', now(), now(), 0),
    (2151, 1902, '导出审计日志', 'BUTTON', null, null, 'system:audit:export', null, 1, false, 'ENABLED', now(), now(), 0)
on conflict (id) do update set
    parent_id = excluded.parent_id,
    menu_name = excluded.menu_name,
    menu_type = excluded.menu_type,
    path = excluded.path,
    component = excluded.component,
    perms = excluded.perms,
    icon = excluded.icon,
    sort_order = excluded.sort_order,
    visible = excluded.visible,
    status = excluded.status,
    update_time = now(),
    deleted = 0;

commit;
