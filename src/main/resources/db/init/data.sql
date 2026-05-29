insert into sys_dept (
    id, parent_id, dept_name, dept_code, leader, sort_order, status, create_time, update_time, deleted
) values (
    1, 0, '总部', 'HQ', 'admin', 0, 'ENABLED', now(), now(), 0
) on conflict (id) do nothing;

insert into sys_role (
    id, role_name, role_code, sort_order, status, remark, create_time, update_time, deleted
) values (
    1, '超级管理员', 'admin', 0, 'ENABLED', '系统内置管理员角色', now(), now(), 0
) on conflict (id) do nothing;

insert into sys_user (
    id, username, password, nickname, email, dept_id, status, create_time, update_time, deleted
) values (
    1,
    'admin',
    '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW',
    '系统管理员',
    'admin@example.com',
    1,
    'ENABLED',
    now(),
    now(),
    0
) on conflict (id) do nothing;

insert into sys_user_role (id, user_id, role_id, create_time)
values (1, 1, 1, now())
on conflict (id) do nothing;

insert into sys_menu (
    id, parent_id, menu_name, menu_type, path, component, perms, icon, sort_order, visible, status, create_time, update_time, deleted
) values
    (100, 0, '系统管理', 'DIR', '/system', null, null, 'settings', 10, true, 'ENABLED', now(), now(), 0),
    (110, 100, '用户管理', 'MENU', '/system/users', 'system/user/index', 'system:user:list', 'user', 1, true, 'ENABLED', now(), now(), 0),
    (111, 110, '用户新增', 'BUTTON', null, null, 'system:user:add', null, 1, false, 'ENABLED', now(), now(), 0),
    (112, 110, '用户修改', 'BUTTON', null, null, 'system:user:edit', null, 2, false, 'ENABLED', now(), now(), 0),
    (113, 110, '用户删除', 'BUTTON', null, null, 'system:user:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (114, 110, '用户详情', 'BUTTON', null, null, 'system:user:query', null, 4, false, 'ENABLED', now(), now(), 0),
    (120, 100, '角色管理', 'MENU', '/system/roles', 'system/role/index', 'system:role:list', 'shield', 2, true, 'ENABLED', now(), now(), 0),
    (121, 120, '角色新增', 'BUTTON', null, null, 'system:role:add', null, 1, false, 'ENABLED', now(), now(), 0),
    (122, 120, '角色修改', 'BUTTON', null, null, 'system:role:edit', null, 2, false, 'ENABLED', now(), now(), 0),
    (123, 120, '角色删除', 'BUTTON', null, null, 'system:role:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (124, 120, '角色详情', 'BUTTON', null, null, 'system:role:query', null, 4, false, 'ENABLED', now(), now(), 0),
    (130, 100, '菜单管理', 'MENU', '/system/menus', 'system/menu/index', 'system:menu:list', 'menu', 3, true, 'ENABLED', now(), now(), 0),
    (131, 130, '菜单新增', 'BUTTON', null, null, 'system:menu:add', null, 1, false, 'ENABLED', now(), now(), 0),
    (132, 130, '菜单修改', 'BUTTON', null, null, 'system:menu:edit', null, 2, false, 'ENABLED', now(), now(), 0),
    (133, 130, '菜单删除', 'BUTTON', null, null, 'system:menu:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (134, 130, '菜单详情', 'BUTTON', null, null, 'system:menu:query', null, 4, false, 'ENABLED', now(), now(), 0),
    (140, 100, '部门管理', 'MENU', '/system/depts', 'system/dept/index', 'system:dept:list', 'building', 4, true, 'ENABLED', now(), now(), 0),
    (141, 140, '部门新增', 'BUTTON', null, null, 'system:dept:add', null, 1, false, 'ENABLED', now(), now(), 0),
    (142, 140, '部门修改', 'BUTTON', null, null, 'system:dept:edit', null, 2, false, 'ENABLED', now(), now(), 0),
    (143, 140, '部门删除', 'BUTTON', null, null, 'system:dept:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (144, 140, '部门详情', 'BUTTON', null, null, 'system:dept:query', null, 4, false, 'ENABLED', now(), now(), 0),
    (200, 0, '工单管理', 'DIR', '/tickets', null, null, 'ticket', 20, true, 'ENABLED', now(), now(), 0),
    (210, 200, '工单列表', 'MENU', '/tickets/orders', 'ticket/order/index', 'ticket:order:list', 'list', 1, true, 'ENABLED', now(), now(), 0),
    (211, 210, '工单新增', 'BUTTON', null, null, 'ticket:order:add', null, 1, false, 'ENABLED', now(), now(), 0),
    (212, 210, '工单修改', 'BUTTON', null, null, 'ticket:order:edit', null, 2, false, 'ENABLED', now(), now(), 0),
    (213, 210, '工单删除', 'BUTTON', null, null, 'ticket:order:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (214, 210, '工单详情', 'BUTTON', null, null, 'ticket:order:query', null, 4, false, 'ENABLED', now(), now(), 0),
    (215, 210, '工单回收站', 'BUTTON', null, null, 'ticket:order:recycle', null, 5, false, 'ENABLED', now(), now(), 0),
    (216, 210, '工单恢复', 'BUTTON', null, null, 'ticket:order:restore', null, 6, false, 'ENABLED', now(), now(), 0),
    (217, 210, '工单创建草稿', 'BUTTON', null, null, 'ticket:order:create', null, 7, false, 'ENABLED', now(), now(), 0),
    (218, 210, '工单提交', 'BUTTON', null, null, 'ticket:order:submit', null, 8, false, 'ENABLED', now(), now(), 0),
    (219, 210, '工单受理', 'BUTTON', null, null, 'ticket:order:accept', null, 9, false, 'ENABLED', now(), now(), 0),
    (220, 210, '工单处理', 'BUTTON', null, null, 'ticket:order:process', null, 10, false, 'ENABLED', now(), now(), 0),
    (221, 210, '工单处理完成', 'BUTTON', null, null, 'ticket:order:finish', null, 11, false, 'ENABLED', now(), now(), 0),
    (222, 210, '工单确认', 'BUTTON', null, null, 'ticket:order:confirm', null, 12, false, 'ENABLED', now(), now(), 0),
    (223, 210, '工单转派', 'BUTTON', null, null, 'ticket:order:transfer', null, 13, false, 'ENABLED', now(), now(), 0),
    (224, 210, '工单评论', 'BUTTON', null, null, 'ticket:order:comment', null, 14, false, 'ENABLED', now(), now(), 0),
    (225, 210, '工单详情新版', 'BUTTON', null, null, 'ticket:order:detail', null, 15, false, 'ENABLED', now(), now(), 0),
    (300, 0, 'AI 工作台', 'DIR', '/ai-agent', null, null, 'bot', 30, true, 'ENABLED', now(), now(), 0),
    (310, 300, 'Codex 工作台', 'MENU', '/ai-agent/codex', 'agent/codex/index', 'ai:agent:chat', 'bot', 1, true, 'ENABLED', now(), now(), 0),
    (311, 310, 'AI 聊天', 'BUTTON', null, null, 'ai:agent:chat', null, 1, false, 'ENABLED', now(), now(), 0),
    (312, 310, '项目状态', 'BUTTON', null, null, 'ai:agent:status', null, 2, false, 'ENABLED', now(), now(), 0),
    (313, 310, '最近日志', 'BUTTON', null, null, 'ai:agent:logs', null, 3, false, 'ENABLED', now(), now(), 0),
    (314, 310, '操作确认', 'BUTTON', null, null, 'ai:agent:confirm', null, 4, false, 'ENABLED', now(), now(), 0)
on conflict (id) do nothing;
