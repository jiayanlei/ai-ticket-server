update sys_menu
set menu_name = '知识库',
    path = '/knowledge/documents',
    component = 'knowledge/documents/index',
    perms = 'knowledge:documents:view',
    icon = 'read',
    sort_order = 1,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 27
  and parent_id = 4
  and deleted = 0;

update sys_menu
set menu_name = '知识库管理',
    path = '/knowledge/faq',
    component = 'knowledge/faq/index',
    perms = 'knowledge:faq:view',
    icon = 'question-circle',
    sort_order = 2,
    visible = true,
    status = 'ENABLED',
    update_time = now()
where id = 28
  and parent_id = 4
  and deleted = 0;
