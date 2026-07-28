# Frontend Mock Data Gap

## Current Backend Gaps

- `sys_menu`: 原种子只覆盖系统管理、旧工单、AI 问答的一小部分，缺少前端 `src/mock/menu.ts` 中的大部分菜单、路由和按钮权限。
- `business_records`: 原 schema 未建表，无法承载前端 `src/mock/business.ts` 的通用业务记录。
- `sys_tenant`: 原 schema 未建表，租户中心缺基础数据承载。
- `knowledge_category` / `knowledge_document` / `document_center`: 原 schema 未建表，知识库、知识库管理、文档中心缺基础数据承载。
- `ticket_order` 相关种子：原 schema 有表，但初始化数据缺少可用于前端工单列表、详情、流转、评论、附件联调的基础记录。
- `sys_dept` / `sys_role` / `sys_user`: 原种子只有总部、超级管理员、admin，缺少前端 mock 中常见的运维、知识、坐席、审计基础账号和部门。

## First Batch Filled

- 菜单/权限种子：按 `src/mock/menu.ts` 补齐目录、页面路由、隐藏兼容路由、按钮权限，菜单名统一中文。
- 通用业务记录：新增 `business_records`，按 `src/mock/business.ts` 覆盖 27 个 module，每个 module 3 条记录。
- 工单相关种子：补 `ticket_order`、`ticket_flow_record`、`ticket_comment`、`ticket_attachment` 基础记录。
- 系统基础种子：补部门、角色、用户、用户角色绑定。
- 租户/知识/文档基础种子：新增并补 `sys_tenant`、`knowledge_category`、`knowledge_document`、`document_center`。

## Static Page Mock To Split Later

| Frontend Page | Current Mock Shape | Suggested Backend API |
| --- | --- | --- |
| `dashboard/screen` | 页面内大屏聚合对象，含核心指标、队列、趋势、风险、SLA、分布图 | `GET /dashboard/screen/overview`，先返回整屏 DTO |
| `dashboard/workbench` | 页面内指标、快捷入口、待办与风险摘要 | `GET /dashboard/workbench/summary` |
| `analytics/bi` | 工作簿、订阅、血缘、导出任务等静态集合 | `GET /analytics/bi/workbooks`、`GET /analytics/bi/subscriptions` |
| `analytics/operations` | 运营异常、渠道指标、趋势和关联工单 | `GET /analytics/operations/overview`、`GET /analytics/operations/exceptions` |
| `analytics/cockpit` | 驾驶舱指标、风险墙、业务记录聚合 | `GET /analytics/cockpit/overview` |
| `ai/prompts` | 提示词版本、评测、变量、护栏 | `GET /ai/prompts`、`GET /ai/prompts/{id}/evaluations` |
| `ai/workflows` | 工作流节点、发布记录、运行统计 | `GET /ai/workflows`、`GET /ai/workflows/{id}` |
| `omnichannel/sms` | 短信批次、模板、发送明细 | `GET /omnichannel/sms/batches`、`GET /omnichannel/sms/templates` |
| `operations/agents` | 坐席技能、在线状态、负载 | `GET /operations/agents`、`GET /operations/agents/summary` |
| `operations/scheduling` | 班表、调班、技能覆盖 | `GET /operations/scheduling/shifts`、`GET /operations/scheduling/requests` |
| `operations/quality` | 质检样本、评分、申诉 | `GET /operations/quality/samples`、`GET /operations/quality/rules` |
| `operations/training` | 培训任务、课程、考试和关联业务 | `GET /operations/training/tasks`、`GET /operations/training/courses` |

## Notes

- 第一批没有为页面内静态 mock 强拆复杂业务表，避免一次性引入过多表结构。
- `business_records` 是迁移阶段通用承载表，后续某个模块逻辑稳定后再拆独立表和接口。
