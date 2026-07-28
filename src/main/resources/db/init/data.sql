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
    (100, 0, '服务中心', 'DIR', null, null, null, 'customer-service', 2, true, 'ENABLED', now(), now(), 0),
    (200, 0, '全渠道中心', 'DIR', null, null, null, 'inbox', 3, true, 'ENABLED', now(), now(), 0),
    (300, 0, '坐席运营', 'DIR', null, null, null, 'team', 4, true, 'ENABLED', now(), now(), 0),
    (400, 0, 'AI 数据中心', 'DIR', null, null, null, 'robot', 5, true, 'ENABLED', now(), now(), 0),
    (500, 0, '客户中心', 'DIR', null, null, null, 'user', 6, true, 'ENABLED', now(), now(), 0),
    (600, 0, '租户管理', 'DIR', null, null, null, 'deployment-unit', 7, true, 'ENABLED', now(), now(), 0),
    (700, 0, '知识中心', 'DIR', null, null, null, 'book', 8, true, 'ENABLED', now(), now(), 0),
    (800, 0, '数据分析', 'DIR', null, null, null, 'bar-chart', 9, true, 'ENABLED', now(), now(), 0),
    (900, 0, '系统管理', 'DIR', null, null, null, 'setting', 10, true, 'ENABLED', now(), now(), 0),
    (10, 0, '工作台', 'MENU', '/dashboard/workbench', 'dashboard/workbench/index', 'dashboard:workbench:view', 'dashboard', 1, true, 'ENABLED', now(), now(), 0),
    (110, 100, '工单中心', 'MENU', '/service/tickets', 'service/tickets/index', 'service:ticket:view', 'ticket', 1, true, 'ENABLED', now(), now(), 0),
    (111, 100, '创建工单', 'MENU', '/service/tickets/create', 'ticket/create/index', 'service:ticket:create', 'plus-circle', 2, false, 'ENABLED', now(), now(), 0),
    (112, 100, '呼叫中心', 'MENU', '/service/calls', 'service/calls/index', 'service:call:view', 'phone', 3, true, 'ENABLED', now(), now(), 0),
    (113, 100, '在线会话中心', 'MENU', '/service/live-chat', 'service/live-chat/index', 'service:chat:view', 'message', 4, true, 'ENABLED', now(), now(), 0),
    (210, 200, '邮件中心', 'MENU', '/omnichannel/email', 'omnichannel/email/index', 'omnichannel:email:view', 'mail', 1, true, 'ENABLED', now(), now(), 0),
    (211, 200, '短信中心', 'MENU', '/omnichannel/sms', 'omnichannel/sms/index', 'omnichannel:sms:view', 'mobile', 2, true, 'ENABLED', now(), now(), 0),
    (310, 300, '坐席中心', 'MENU', '/operations/agents', 'operations/agents/index', 'operations:agent:view', 'team', 1, true, 'ENABLED', now(), now(), 0),
    (311, 300, '排班管理', 'MENU', '/operations/scheduling', 'operations/scheduling/index', 'operations:schedule:view', 'schedule', 2, true, 'ENABLED', now(), now(), 0),
    (312, 300, '绩效中心', 'MENU', '/operations/performance', 'operations/performance/index', 'operations:performance:view', 'trophy', 3, true, 'ENABLED', now(), now(), 0),
    (313, 300, 'AI 质检', 'MENU', '/operations/quality', 'operations/quality/index', 'operations:quality:view', 'safety', 4, true, 'ENABLED', now(), now(), 0),
    (314, 300, '培训中心', 'MENU', '/operations/training', 'operations/training/index', 'operations:training:view', 'read', 5, true, 'ENABLED', now(), now(), 0),
    (410, 400, 'AI 问答', 'MENU', '/ai/chat', 'console/codex/index', 'ai:agent:chat', 'robot', 1, true, 'ENABLED', now(), now(), 0),
    (411, 400, '工作流中心', 'MENU', '/ai/workflows', 'ai/workflows/index', 'ai:workflow:view', 'branches', 3, true, 'ENABLED', now(), now(), 0),
    (412, 400, '提示词中心', 'MENU', '/ai/prompts', 'ai/prompts/index', 'ai:prompt:view', 'code', 4, true, 'ENABLED', now(), now(), 0),
    (413, 400, '模型中心', 'MENU', '/ai/models', 'ai/models/index', 'ai:model:view', 'experiment', 5, true, 'ENABLED', now(), now(), 0),
    (414, 400, '分析结果', 'MENU', '/ai/result', 'ai/result/index', 'ai:result:view', 'file-search', 6, true, 'ENABLED', now(), now(), 0),
    (415, 400, '对话记录', 'MENU', '/ai/conversation', 'conversation/records/index', 'ai:conversation:list', 'profile', 7, true, 'ENABLED', now(), now(), 0),
    (510, 500, '客户 360', 'MENU', '/customers/360', 'customers/360/index', 'customer:360:view', 'user', 1, true, 'ENABLED', now(), now(), 0),
    (511, 500, '客户旅程', 'MENU', '/customers/journey', 'customers/journey/index', 'customer:journey:view', 'deployment-unit', 2, true, 'ENABLED', now(), now(), 0),
    (710, 700, '知识库', 'MENU', '/knowledge/base', 'knowledge/base/index', 'knowledge:base:view', 'book', 1, true, 'ENABLED', now(), now(), 0),
    (711, 700, '知识库管理', 'MENU', '/knowledge/manage', 'knowledge/manage/index', 'knowledge:document:list', 'read', 2, true, 'ENABLED', now(), now(), 0),
    (712, 700, '文档中心', 'MENU', '/knowledge/documents', 'knowledge/documents/index', 'knowledge:document:list', 'file-search', 3, true, 'ENABLED', now(), now(), 0),
    (713, 700, 'FAQ 管理', 'MENU', '/knowledge/faq', 'knowledge/faq/index', 'knowledge:faq:view', 'question-circle', 4, false, 'ENABLED', now(), now(), 0),
    (714, 700, '旧版知识库入口', 'MENU', '/knowledge', 'knowledge/base/index', 'knowledge:base:view', 'book', 90, false, 'ENABLED', now(), now(), 0),
    (810, 800, '运营分析', 'MENU', '/analytics/operations', 'analytics/operations/index', 'analytics:operations:view', 'line-chart', 1, true, 'ENABLED', now(), now(), 0),
    (811, 800, 'BI 报表', 'MENU', '/analytics/bi', 'analytics/bi/index', 'analytics:bi:view', 'bar-chart', 2, true, 'ENABLED', now(), now(), 0),
    (812, 800, '数据驾驶舱', 'MENU', '/analytics/cockpit', 'analytics/cockpit/index', 'analytics:cockpit:view', 'dashboard', 3, true, 'ENABLED', now(), now(), 0),
    (813, 800, 'SLA 管理', 'MENU', '/analytics/sla', 'analytics/sla/index', 'analytics:sla:view', 'clock', 4, true, 'ENABLED', now(), now(), 0),
    (814, 800, '风险预警', 'MENU', '/analytics/risk', 'analytics/risk/index', 'analytics:risk:view', 'warning', 5, true, 'ENABLED', now(), now(), 0),
    (815, 800, '系统监控', 'MENU', '/analytics/monitoring', 'analytics/monitoring/index', 'analytics:monitoring:view', 'monitor', 6, true, 'ENABLED', now(), now(), 0),
    (816, 800, '告警中心', 'MENU', '/analytics/alerts', 'analytics/alerts/index', 'analytics:alert:view', 'alert', 7, true, 'ENABLED', now(), now(), 0),
    (610, 600, '租户中心', 'MENU', '/tenants', 'system/tenants/index', 'tenant:center:view', 'deployment-unit', 1, true, 'ENABLED', now(), now(), 0),
    (910, 900, '权限中心', 'MENU', '/system/permissions', 'system/permissions/index', 'system:permission:view', 'shield', 1, true, 'ENABLED', now(), now(), 0),
    (911, 900, '审计中心', 'MENU', '/system/audit', 'system/audit/index', 'system:audit:view', 'file-search', 2, true, 'ENABLED', now(), now(), 0),
    (912, 900, '系统管理总览', 'MENU', '/system/management', 'system/management/index', 'system:management:view', 'setting', 3, true, 'ENABLED', now(), now(), 0),
    (913, 900, '开放平台', 'MENU', '/system/open-platform', 'system/open-platform/index', 'system:open-platform:view', 'api', 4, true, 'ENABLED', now(), now(), 0),
    (914, 900, '用户管理', 'MENU', '/system/users', 'system/users/index', 'system:user:list', 'user', 5, true, 'ENABLED', now(), now(), 0),
    (915, 900, '角色管理', 'MENU', '/system/roles', 'system/roles/index', 'system:role:list', 'team', 6, true, 'ENABLED', now(), now(), 0),
    (916, 900, '部门管理', 'MENU', '/system/depts', 'system/depts/index', 'system:dept:list', 'apartment', 7, true, 'ENABLED', now(), now(), 0),
    (917, 900, '菜单管理', 'MENU', '/system/menus', 'system/menus/index', 'system:menu:list', 'menu', 8, true, 'ENABLED', now(), now(), 0),
    (918, 900, '系统配置', 'MENU', '/system/settings', 'system/settings/index', 'system:settings:view', 'setting', 9, true, 'ENABLED', now(), now(), 0),
    (919, 900, '用户权限', 'MENU', '/system/permission', 'system/permission/index', 'system:permission:view', 'shield', 10, true, 'ENABLED', now(), now(), 0),
    (120, 100, '旧版工单列表', 'MENU', '/ticket/list', 'ticket/list/index', 'service:ticket:view', 'unordered-list', 90, false, 'ENABLED', now(), now(), 0),
    (121, 100, '创建工单', 'MENU', '/ticket/create', 'ticket/create/index', 'service:ticket:create', 'plus-circle', 91, false, 'ENABLED', now(), now(), 0),
    (122, 100, '旧版工单回收站', 'MENU', '/ticket/trash', 'ticket/trash/index', 'service:ticket:delete', 'delete', 92, false, 'ENABLED', now(), now(), 0),
    (1110, 110, '创建工单', 'BUTTON', null, null, 'service:ticket:create', null, 1, false, 'ENABLED', now(), now(), 0),
    (1111, 110, '更新工单', 'BUTTON', null, null, 'service:ticket:update', null, 2, false, 'ENABLED', now(), now(), 0),
    (1112, 110, '审批工单', 'BUTTON', null, null, 'service:ticket:approve', null, 3, false, 'ENABLED', now(), now(), 0),
    (1113, 110, '导出工单', 'BUTTON', null, null, 'service:ticket:export', null, 4, false, 'ENABLED', now(), now(), 0),
    (1120, 112, '监听通话', 'BUTTON', null, null, 'service:call:monitor', null, 1, false, 'ENABLED', now(), now(), 0),
    (1121, 112, '强插通话', 'BUTTON', null, null, 'service:call:barge', null, 2, false, 'ENABLED', now(), now(), 0),
    (1122, 112, '强制断开', 'BUTTON', null, null, 'service:call:disconnect', null, 3, false, 'ENABLED', now(), now(), 0),
    (4110, 411, '发布工作流', 'BUTTON', null, null, 'ai:workflow:publish', null, 1, false, 'ENABLED', now(), now(), 0),
    (4130, 413, '更新模型路由', 'BUTTON', null, null, 'ai:model:route', null, 1, false, 'ENABLED', now(), now(), 0),
    (7110, 711, '知识新增', 'BUTTON', null, null, 'knowledge:document:create', null, 1, false, 'ENABLED', now(), now(), 0),
    (7111, 711, '知识编辑', 'BUTTON', null, null, 'knowledge:document:update', null, 2, false, 'ENABLED', now(), now(), 0),
    (7112, 711, '知识删除', 'BUTTON', null, null, 'knowledge:document:delete', null, 3, false, 'ENABLED', now(), now(), 0),
    (7113, 711, '知识发布', 'BUTTON', null, null, 'knowledge:document:publish', null, 4, false, 'ENABLED', now(), now(), 0),
    (7114, 711, '知识下架', 'BUTTON', null, null, 'knowledge:document:offline', null, 5, false, 'ENABLED', now(), now(), 0),
    (6100, 610, '创建租户', 'BUTTON', null, null, 'tenant:center:create', null, 1, false, 'ENABLED', now(), now(), 0),
    (6101, 610, '更新租户', 'BUTTON', null, null, 'tenant:center:update', null, 2, false, 'ENABLED', now(), now(), 0),
    (6102, 610, '变更租户状态', 'BUTTON', null, null, 'tenant:center:status', null, 3, false, 'ENABLED', now(), now(), 0),
    (6103, 610, '管理租户成员', 'BUTTON', null, null, 'tenant:center:member', null, 4, false, 'ENABLED', now(), now(), 0),
    (6104, 610, '管理租户权限', 'BUTTON', null, null, 'tenant:center:permission', null, 5, false, 'ENABLED', now(), now(), 0),
    (6105, 610, '查看租户审计', 'BUTTON', null, null, 'tenant:center:audit', null, 6, false, 'ENABLED', now(), now(), 0),
    (6106, 610, '导出租户数据', 'BUTTON', null, null, 'tenant:center:export', null, 7, false, 'ENABLED', now(), now(), 0),
    (9110, 911, '导出审计日志', 'BUTTON', null, null, 'system:audit:export', null, 1, false, 'ENABLED', now(), now(), 0)
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

insert into sys_dept (
    id, parent_id, dept_name, dept_code, leader, phone, email, sort_order, status, create_time, update_time, deleted
) values
    (2, 1, '平台运维部', 'OPS-PLATFORM', '陈斌', '021-68886601', 'platform@example.com', 1, 'ENABLED', '2025-01-06 09:00:00', now(), 0),
    (3, 1, '知识运营部', 'OPS-KNOWLEDGE', '宋佳', '021-68886602', 'knowledge@example.com', 2, 'ENABLED', '2025-01-07 09:00:00', now(), 0),
    (4, 1, '客户成功部', 'OPS-CS', '刘薇', '021-68886603', 'success@example.com', 3, 'ENABLED', '2025-01-08 09:00:00', now(), 0),
    (5, 2, 'AI 中台组', 'OPS-AI', '郑宁', '021-68886604', 'ai-center@example.com', 1, 'ENABLED', '2025-01-09 09:00:00', now(), 0),
    (6, 2, '基础架构组', 'OPS-INFRA', '谢涛', '021-68886605', 'infra@example.com', 2, 'ENABLED', '2025-01-10 09:00:00', now(), 0),
    (7, 1, '流程治理办', 'OPS-BPM', '黄莹', '021-68886606', 'bpm@example.com', 4, 'DISABLED', '2025-01-11 09:00:00', now(), 0)
on conflict (id) do update set
    parent_id = excluded.parent_id,
    dept_name = excluded.dept_name,
    dept_code = excluded.dept_code,
    leader = excluded.leader,
    phone = excluded.phone,
    email = excluded.email,
    sort_order = excluded.sort_order,
    status = excluded.status,
    update_time = now(),
    deleted = 0;

insert into sys_role (
    id, role_name, role_code, sort_order, status, remark, create_time, update_time, deleted
) values
    (2, '运维主管', 'ops_manager', 2, 'ENABLED', '负责工单统筹、升级和调度', '2025-01-13 09:00:00', now(), 0),
    (3, '知识库管理员', 'knowledge_admin', 3, 'ENABLED', '维护知识文档、图谱与文档中心', '2025-01-14 09:00:00', now(), 0),
    (4, '客服坐席', 'agent', 4, 'ENABLED', '处理待办工单和知识检索', '2025-01-15 09:00:00', now(), 0),
    (5, '审计访客', 'auditor', 5, 'DISABLED', '只读审计账号', '2025-01-16 09:00:00', now(), 0)
on conflict (id) do update set
    role_name = excluded.role_name,
    role_code = excluded.role_code,
    sort_order = excluded.sort_order,
    status = excluded.status,
    remark = excluded.remark,
    update_time = now(),
    deleted = 0;

insert into sys_user (
    id, username, password, nickname, email, mobile, dept_id, status, last_login_time, create_time, update_time, deleted
) values
    (2, 'ops.manager', '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW', '陈沐阳', 'ops.manager@example.com', '13800010002', 2, 'ENABLED', '2026-06-07 08:17:09', '2025-02-03 09:00:00', now(), 0),
    (3, 'knowledge.lead', '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW', '宋之言', 'knowledge.lead@example.com', '13800010003', 3, 'ENABLED', '2026-06-06 17:35:44', '2025-02-05 09:00:00', now(), 0),
    (4, 'agent.zhang', '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW', '张若一', 'agent.zhang@example.com', '13800010004', 4, 'ENABLED', '2026-06-07 09:02:55', '2025-02-07 09:00:00', now(), 0),
    (5, 'agent.li', '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW', '李心禾', 'agent.li@example.com', '13800010005', 4, 'ENABLED', '2026-06-07 08:51:12', '2025-02-08 09:00:00', now(), 0),
    (6, 'ai.ops', '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW', '郑宁', 'ai.ops@example.com', '13800010006', 5, 'ENABLED', '2026-06-06 21:16:43', '2025-02-10 09:00:00', now(), 0),
    (7, 'infra.xie', '$2a$10$EFEwzT0ew.A9QTT6YvfGlejaeub5P2dcM05F8kIZ/kbMrvwMplRQW', '谢砚青', 'infra.xie@example.com', '13800010007', 6, 'DISABLED', '2026-05-18 20:45:31', '2025-02-11 09:00:00', now(), 0)
on conflict (id) do update set
    username = excluded.username,
    nickname = excluded.nickname,
    email = excluded.email,
    mobile = excluded.mobile,
    dept_id = excluded.dept_id,
    status = excluded.status,
    last_login_time = excluded.last_login_time,
    update_time = now(),
    deleted = 0;

insert into sys_user_role (id, user_id, role_id, create_time)
values
    (2, 2, 2, now()),
    (3, 3, 3, now()),
    (4, 4, 4, now()),
    (5, 5, 4, now()),
    (6, 6, 2, now()),
    (7, 7, 2, now())
on conflict (id) do nothing;

insert into ticket_order (
    id, ticket_no, title, description, status, priority, source, category, applicant_id, applicant_name, handler_id, handler_name,
    expected_finish_time, submit_time, accept_time, start_process_time, finish_time, close_time, sla_deadline, is_timeout,
    ai_category, ai_risk_level, ai_recommend_dept, ai_recommend_handler, ai_estimated_time, ai_summary, ai_suggestion,
    create_time, update_time, deleted
) values
    (10001, 'TK202606070001', '统一登录后首页菜单缺失', '用户登录后只显示空白页，刷新后菜单树未正常渲染。', 'PROCESSING', 'URGENT', 'WEB', '账号权限', 1, '林知远', 2, '陈沐阳', '2026-06-07 18:00:00', '2026-06-07 08:20:00', '2026-06-07 08:20:00', '2026-06-07 09:05:00', null, null, '2026-06-07 18:00:00', false, '账号权限', 'HIGH', '平台运维部', '陈沐阳', '4小时', '检测到菜单缓存与后端权限数据可能不同步。', '建议先刷新菜单缓存并对齐路由权限种子。', '2026-06-07 08:20:00', '2026-06-07 09:05:00', 0),
    (10002, 'TK202606070002', '知识库检索结果为空', '关键词搜索“员工手册”没有返回文档，但文档中心里可以看到原文。', 'WAIT_CONFIRM', 'HIGH', 'PHONE', '知识库', 3, '宋之言', 6, '郑宁', '2026-06-07 20:00:00', '2026-06-07 07:50:00', '2026-06-07 07:50:00', '2026-06-07 08:48:00', '2026-06-07 08:48:00', null, '2026-06-07 20:00:00', false, '知识库', 'MEDIUM', 'AI 中台组', '郑宁', '2小时', '检索索引可能未覆盖最新文档中心数据。', '建议补齐知识文档种子并重建索引。', '2026-06-07 07:50:00', '2026-06-07 08:48:00', 0),
    (10003, 'TK202606070003', '对话记录页筛选条件失效', '切换任务类型后列表没有重新过滤，仍显示上一次查询结果。', 'PENDING_ACCEPT', 'NORMAL', 'WEB', '对话记录', 6, '郑宁', 4, '刘薇', '2026-06-08 12:00:00', '2026-06-07 09:12:00', null, null, null, null, '2026-06-08 12:00:00', false, '对话记录', 'LOW', '客户成功部', '刘薇', '1天', '筛选参数可能未进入请求。', '建议补充列表查询种子并确认参数透传。', '2026-06-07 09:12:00', '2026-06-07 09:12:00', 0),
    (10004, 'TK202606070004', '大屏趋势图数据抖动', '首页大屏刷新时图表会闪烁，怀疑数据结构重复创建导致。', 'PROCESSING', 'HIGH', 'WECHAT', '数据可视化', 7, '谢砚青', 6, '郑宁', '2026-06-07 22:00:00', '2026-06-07 06:40:00', '2026-06-07 06:40:00', '2026-06-07 08:36:00', null, null, '2026-06-07 22:00:00', false, '数据分析', 'MEDIUM', 'AI 中台组', '郑宁', '6小时', '大屏静态 mock 暂未拆接口。', '建议后续拆大屏聚合接口。', '2026-06-07 06:40:00', '2026-06-07 08:36:00', 0),
    (10005, 'TK202606070005', '部门树新增后未排序', '新增部门成功后，树表格展示顺序没有按照 sortOrder 刷新。', 'ACCEPTED', 'NORMAL', 'MANUAL', '组织架构', 7, '黄莹', 2, '陈沐阳', '2026-06-08 18:00:00', '2026-06-07 08:01:00', '2026-06-07 08:20:00', null, null, null, '2026-06-08 18:00:00', false, '系统管理', 'LOW', '平台运维部', '陈沐阳', '1天', '部门树依赖 sortOrder 排序。', '建议确认新增后重新加载列表。', '2026-06-07 08:01:00', '2026-06-07 08:20:00', 0),
    (10006, 'TK202606070006', '文档解析卡在处理中', '上传 xlsx 后解析状态长时间停留在 parsing。', 'PENDING', 'HIGH', 'APP', '文档中心', 3, '宋之言', 4, '刘薇', '2026-06-08 10:00:00', '2026-06-07 05:30:00', '2026-06-07 05:30:00', '2026-06-07 07:45:00', null, null, '2026-06-08 10:00:00', false, '文档中心', 'MEDIUM', '客户成功部', '刘薇', '4小时', '文档中心解析状态需要基础种子支撑。', '建议补充文档中心记录和解析状态枚举。', '2026-06-07 05:30:00', '2026-06-07 07:45:00', 0)
on conflict (id) do update set
    ticket_no = excluded.ticket_no,
    title = excluded.title,
    description = excluded.description,
    status = excluded.status,
    priority = excluded.priority,
    source = excluded.source,
    category = excluded.category,
    applicant_id = excluded.applicant_id,
    applicant_name = excluded.applicant_name,
    handler_id = excluded.handler_id,
    handler_name = excluded.handler_name,
    expected_finish_time = excluded.expected_finish_time,
    submit_time = excluded.submit_time,
    accept_time = excluded.accept_time,
    start_process_time = excluded.start_process_time,
    finish_time = excluded.finish_time,
    close_time = excluded.close_time,
    sla_deadline = excluded.sla_deadline,
    is_timeout = excluded.is_timeout,
    ai_category = excluded.ai_category,
    ai_risk_level = excluded.ai_risk_level,
    ai_recommend_dept = excluded.ai_recommend_dept,
    ai_recommend_handler = excluded.ai_recommend_handler,
    ai_estimated_time = excluded.ai_estimated_time,
    ai_summary = excluded.ai_summary,
    ai_suggestion = excluded.ai_suggestion,
    update_time = excluded.update_time,
    deleted = 0;

insert into ticket_flow_record (
    id, ticket_id, operator_id, operator_name, action, before_status, after_status, remark, create_time
) values
    (100001, 10001, 1, '林知远', 'SUBMIT', 'DRAFT', 'PENDING_ACCEPT', '提交工单。', '2026-06-07 08:20:00'),
    (100002, 10001, 2, '陈沐阳', 'START_PROCESS', 'ACCEPTED', 'PROCESSING', '开始排查菜单权限。', '2026-06-07 09:05:00'),
    (100003, 10002, 3, '宋之言', 'SUBMIT', 'DRAFT', 'PENDING_ACCEPT', '提交知识库检索问题。', '2026-06-07 07:50:00'),
    (100004, 10002, 6, '郑宁', 'FINISH', 'PROCESSING', 'WAIT_CONFIRM', '已完成索引检查。', '2026-06-07 08:48:00')
on conflict (id) do nothing;

insert into ticket_comment (
    id, ticket_id, user_id, user_name, content, create_time
) values
    (100001, 10001, 2, '陈沐阳', '已收到工单，正在核对系统日志与菜单权限同步链路。', '2026-06-07 09:10:00'),
    (100002, 10001, 1, '林知远', '补充说明：该问题主要出现在早高峰登录时段。', '2026-06-07 09:20:00'),
    (100003, 10002, 6, '郑宁', '已确认文档中心有原文，下一步检查知识库索引。', '2026-06-07 08:30:00')
on conflict (id) do nothing;

insert into ticket_attachment (
    id, ticket_id, file_name, file_url, file_size, file_type, upload_user_id, upload_user_name, create_time
) values
    (100001, 10001, '菜单缺失截图.png', '/mock/files/menu-missing.png', 835584, 'PNG', 1, '林知远', '2026-06-07 08:25:00'),
    (100002, 10002, '知识库检索截图.png', '/mock/files/knowledge-empty.png', 642120, 'PNG', 3, '宋之言', '2026-06-07 08:05:00')
on conflict (id) do nothing;

insert into business_records (
    id, module, title, code, owner, customer, channel, status, priority, metric, risk, description, ai_suggestion, tags, timeline, create_time, update_time, deleted
)
with module_seed(module, prefix, titles, channel, ai_suggestion) as (
    values
        ('service-calls', 'CALL', array['VIP 客户来电排队超时', '退款咨询需要主管监听', '售后热线情绪升高'], '电话', '建议主管开启耳语辅助，并优先接入高价值客户。'),
        ('service-live-chat', 'CHAT', array['机器人未命中密码重置意图', '多会话需要人工接管', '客户催促处理进度'], '在线客服', '建议接管高风险会话，并引用最新密码重置知识。'),
        ('omnichannel-email', 'MAIL', array['账单争议邮件待审批', '附件缺失需要补充', '退款邮件线程需合并'], '邮件', '建议合并重复线程并使用合规模板回复。'),
        ('omnichannel-sms', 'SMS', array['验证码发送失败重试', '营销短信退订风险', '通知模板待审核'], '短信', '建议暂停低互动任务，避开夜间发送窗口。'),
        ('omnichannel-inbox', 'INBOX', array['跨渠道客户待合并', '重复咨询待去重', '高优消息待分派'], '全渠道', '建议按客户和意图聚合后统一指派。'),
        ('operations-agents', 'AGT', array['坐席负载不均', '技能组覆盖不足', '新人需要辅导'], '坐席运营', '建议把退款技能补充到晚班坐席组。'),
        ('operations-scheduling', 'SCH', array['周五晚高峰缺口', '调班申请待审批', '技能排班冲突'], '排班', '建议提前开放售后加班班次。'),
        ('operations-performance', 'PERF', array['处理时长异常升高', '满意度目标复盘', '团队排名待确认'], '绩效', '建议对高时长低风险场景做定向辅导。'),
        ('operations-quality', 'QA', array['退款话术疑似违规', '质检申诉待审核', '脚本偏离样本'], '质检', '建议复核高风险录音并更新质检规则。'),
        ('operations-training', 'TRN', array['新人课程未完成', '账单争议专项训练', '考试结果待发布'], '培训', '建议为低分坐席安排模拟演练。'),
        ('customers-360', 'CUS', array['高价值客户续约风险', '客户画像待补全', '互动历史需回访'], '客户', '建议先回访续约临近且情绪下降的客户。'),
        ('customers-journey', 'JNY', array['首次升级后重复来访', '触点转化下降', '主动关怀节点缺失'], '旅程', '建议在升级后增加主动关怀触点。'),
        ('ai-workflows', 'WF', array['退款审批节点拥堵', '失败流程待重试', '灰度版本待发布'], 'AI 工作流', '建议拆分审批节点并回滚异常版本。'),
        ('ai-prompts', 'PRM', array['退款提示词评测下降', '变量覆盖不足', '安全护栏触发'], '提示词', '建议回滚到上一稳定版本并补充评测集。'),
        ('ai-models', 'MDL', array['摘要任务成本偏高', '模型路由待调整', '降级策略触发'], '模型', '建议低风险摘要路由到低成本模型。'),
        ('analytics-operations', 'OPS', array['邮件队列积压', '渠道成本波动', '效率指标异常'], '运营分析', '建议钻取邮件渠道和账单分类。'),
        ('analytics-bi', 'BI', array['SLA 指标口径冲突', '订阅报表失败', '导出任务排队'], 'BI', '建议统一 SLA 指标口径后重新发布。'),
        ('analytics-sla', 'SLA', array['高价值客户即将超时', '升级规则待启用', '履约率下降'], 'SLA', '建议提前 30 分钟触发预警。'),
        ('analytics-risk', 'RSK', array['异常租户切换', '投诉升级风险', '流失风险升高'], '风险', '建议冻结敏感操作并派单给安全负责人。'),
        ('analytics-monitoring', 'MON', array['知识检索延迟升高', '队列任务积压', '模型调用失败'], '监控', '建议扩容向量检索副本。'),
        ('analytics-alerts', 'ALT', array['重复 SLA 告警', '严重告警待认领', '通知规则过宽'], '告警', '建议合并重复告警并按区域路由。'),
        ('system-permissions', 'RBAC', array['角色数据范围过宽', '敏感权限待审批', '菜单授权待同步'], '权限', '建议把高风险角色收窄到部门级。'),
        ('system-audit', 'AUD', array['夜间导出异常', 'AI 决策审计待查', '登录风险会话'], '审计', '建议复核夜间导出链路。'),
        ('system-management', 'SYS', array['业务开关待发布', '参数变更需审批', '字典项冲突'], '系统', '建议对高风险配置启用双人审批。'),
        ('system-open-platform', 'API', array['Webhook 失败率升高', 'API 密钥待轮换', '应用配额接近上限'], '开放平台', '建议轮换密钥并检查回调地址。'),
        ('dashboard-workbench', 'DSH', array['今日 SLA 风险聚合', '投诉类工单上升', 'AI 介入建议待确认'], '工作台', '建议优先处理投诉上升和技术类时长偏高。'),
        ('analytics-cockpit', 'CKP', array['大屏核心指标待刷新', '区域服务波动', '风险墙需要升级'], '驾驶舱', '建议把 SLA 压力和风险队列提升到首屏。')
), expanded as (
    select
        row_number() over (order by module_seed.module, title_index) as row_no,
        module_seed.module,
        module_seed.prefix,
        title_item.title,
        title_item.title_index,
        module_seed.channel,
        module_seed.ai_suggestion,
        (array['陈沐阳', '郑宁', '刘薇', '谢砚青'])[title_item.title_index] as owner,
        (array['蓝湖集团', '星河科技', '云杉零售', '北辰制造'])[title_item.title_index] as customer,
        (array['待处理', '处理中', '待审核', '已完成'])[title_item.title_index] as record_status,
        (array['中', '高', '紧急', '低'])[title_item.title_index] as record_priority
    from module_seed
    cross join lateral unnest(module_seed.titles) with ordinality as title_item(title, title_index)
)
select
    300000 + row_no,
    module,
    title,
    prefix || '202606' || lpad(title_index::text, 4, '0'),
    owner,
    customer,
    channel,
    record_status,
    record_priority,
    '-',
    case title_index when 1 then '高风险' when 2 then '中风险' else '低风险' end,
    title || '，需要在当前业务工作台内完成分派、处理和复核。',
    ai_suggestion,
    jsonb_build_array(channel, record_status, record_priority),
    jsonb_build_array(
        jsonb_build_object('time', to_char(now() - interval '1 day', 'YYYY-MM-DD HH24:MI:SS'), 'action', 'AI 分析', 'operator', 'AI 助手', 'content', ai_suggestion),
        jsonb_build_object('time', to_char(now() - interval '2 days', 'YYYY-MM-DD HH24:MI:SS'), 'action', '业务接入', 'operator', owner, 'content', channel || '记录已进入工作台。')
    ),
    now() - (title_index || ' days')::interval,
    now() - (title_index || ' days')::interval,
    0
from expanded
on conflict (id) do update set
    module = excluded.module,
    title = excluded.title,
    code = excluded.code,
    owner = excluded.owner,
    customer = excluded.customer,
    channel = excluded.channel,
    status = excluded.status,
    priority = excluded.priority,
    metric = excluded.metric,
    risk = excluded.risk,
    description = excluded.description,
    ai_suggestion = excluded.ai_suggestion,
    tags = excluded.tags,
    timeline = excluded.timeline,
    update_time = excluded.update_time,
    deleted = 0;

insert into sys_tenant (
    id, tenant_name, tenant_code, status, service_status, administrator, administrator_email, default_organization,
    enabled_modules, organization_count, user_count, agent_count, ai_agent_count, settings, permissions, remark, create_time, update_time, deleted
) values
    (1, '全球企业服务中心', 'GLOBAL-CX', 'ENABLED', 'NORMAL', '林知远', 'admin@example.com', '集团运营中心',
     '["工单", "呼叫", "在线会话", "知识库", "AI", "数据分析"]'::jsonb, 8, 286, 168, 12,
     '{"language":"zh-CN","timezone":"Asia/Shanghai","channels":["电话","在线会话","邮件","短信"],"slaPolicy":"企业客户优先 SLA","aiEnabled":true,"knowledgeScope":"集团知识库","ticketRule":"按客户等级和渠道自动分派"}'::jsonb,
     '["系统管理员","运营管理员","坐席主管","客服坐席","AI 运营","知识管理员","审计员"]'::jsonb,
     '集团级默认租户，承载跨区域服务运营。', '2025-01-05 09:00:00', now(), 0),
    (2, '亚太客户成功中心', 'APAC-CS', 'ENABLED', 'WARNING', '陈沐阳', 'ops.manager@example.com', '亚太客户成功部',
     '["工单", "在线会话", "知识库", "数据分析"]'::jsonb, 5, 96, 64, 5,
     '{"language":"zh-CN","timezone":"Asia/Shanghai","channels":["在线会话","邮件","短信"],"slaPolicy":"区域客户成功 SLA","aiEnabled":true,"knowledgeScope":"亚太业务知识库","ticketRule":"按语言和技能组分派"}'::jsonb,
     '["运营管理员","坐席主管","客服坐席","知识管理员"]'::jsonb,
     '亚太区域服务租户，近期在线会话资源接近阈值。', '2025-03-18 10:30:00', now(), 0),
    (3, '欧洲售后服务中心', 'EMEA-SERVICE', 'FROZEN', 'SUSPENDED', '宋之言', 'knowledge.lead@example.com', '欧洲售后部',
     '["工单", "邮件", "知识库"]'::jsonb, 3, 42, 28, 2,
     '{"language":"zh-CN","timezone":"Europe/Berlin","channels":["邮件"],"slaPolicy":"欧洲售后 SLA","aiEnabled":false,"knowledgeScope":"欧洲售后知识库","ticketRule":"冻结期仅允许只读"}'::jsonb,
     '["审计员","知识管理员"]'::jsonb,
     '演示冻结租户，用于权限与审计页面联调。', '2025-04-12 09:00:00', now(), 0)
on conflict (id) do update set
    tenant_name = excluded.tenant_name,
    tenant_code = excluded.tenant_code,
    status = excluded.status,
    service_status = excluded.service_status,
    administrator = excluded.administrator,
    administrator_email = excluded.administrator_email,
    default_organization = excluded.default_organization,
    enabled_modules = excluded.enabled_modules,
    organization_count = excluded.organization_count,
    user_count = excluded.user_count,
    agent_count = excluded.agent_count,
    ai_agent_count = excluded.ai_agent_count,
    settings = excluded.settings,
    permissions = excluded.permissions,
    remark = excluded.remark,
    update_time = now(),
    deleted = 0;

insert into knowledge_category (
    id, parent_id, title, sort_order, status, create_time, update_time, deleted
) values
    (1, null, '企业知识中台', 1, 'ENABLED', now(), now(), 0),
    (2, 1, '制度文档', 1, 'ENABLED', now(), now(), 0),
    (3, 1, '工单运营', 2, 'ENABLED', now(), now(), 0),
    (4, 1, '项目文档', 3, 'ENABLED', now(), now(), 0),
    (5, 1, 'AI 知识运营', 4, 'ENABLED', now(), now(), 0),
    (6, 1, '客服培训', 5, 'ENABLED', now(), now(), 0)
on conflict (id) do update set
    parent_id = excluded.parent_id,
    title = excluded.title,
    sort_order = excluded.sort_order,
    status = excluded.status,
    update_time = now(),
    deleted = 0;

insert into knowledge_document (
    id, category_id, category_name, title, summary, content, status, tags, version, owner, view_count, create_time, update_time, deleted
) values
    (1, 3, '工单运营', '高优工单升级流程', '说明高优工单识别、升级、转派和回访闭环。', '高优工单应在 SLA 前置预警后转派给主管，并保留处理记录。', 'PUBLISHED', '["SLA","升级","工单"]'::jsonb, 'v1.4', '宋之言', 268, '2026-06-01 09:00:00', now(), 0),
    (2, 2, '制度文档', '账号安全与 MFA 处理手册', '覆盖账号开通、MFA 重置、权限复核和审计留痕。', '敏感账号操作必须记录申请人、审批人和执行人。', 'PUBLISHED', '["账号","MFA","安全"]'::jsonb, 'v2.1', '谢砚青', 186, '2026-06-02 09:00:00', now(), 0),
    (3, 6, '客服培训', '一线坐席培训手册', '新人首周服务流程、标准话术和常见问题处理。', '培训内容包含服务礼仪、知识检索、工单升级和回访记录。', 'DRAFT', '["培训","坐席","话术"]'::jsonb, 'v6.0', '刘薇', 92, '2026-06-03 09:00:00', now(), 0)
on conflict (id) do update set
    category_id = excluded.category_id,
    category_name = excluded.category_name,
    title = excluded.title,
    summary = excluded.summary,
    content = excluded.content,
    status = excluded.status,
    tags = excluded.tags,
    version = excluded.version,
    owner = excluded.owner,
    view_count = excluded.view_count,
    update_time = now(),
    deleted = 0;

insert into document_center (
    id, file_name, category, owner, file_size, format, parse_status, summary, create_time, update_time, deleted
) values
    (1, '2026Q2_工单SLA复盘.pdf', '制度文档', '宋之言', '2.4 MB', 'PDF', 'SUCCESS', '已生成 14 段知识摘要并同步到知识库。', '2026-06-06 18:20:00', now(), 0),
    (2, '一线坐席培训手册_v6.docx', '培训资料', '刘薇', '6.8 MB', 'DOCX', 'PARSING', '正在抽取目录、FAQ 和标准话术。', '2026-06-07 09:05:00', now(), 0),
    (3, '高优工单升级流程.png', '操作手册', '陈沐阳', '820 KB', 'PNG', 'UPLOADING', '正在上传原始文件与结构化标注。', '2026-06-07 09:21:00', now(), 0)
on conflict (id) do update set
    file_name = excluded.file_name,
    category = excluded.category,
    owner = excluded.owner,
    file_size = excluded.file_size,
    format = excluded.format,
    parse_status = excluded.parse_status,
    summary = excluded.summary,
    update_time = now(),
    deleted = 0;
