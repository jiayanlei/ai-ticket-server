🚀 AI Ticket Server

AI 智能工单分析平台后端服务
一个面向企业运维 / 客服场景的 AIOps 智能工单中台系统

🧠 项目简介

AI Ticket Server 是一个基于大模型（LLM）的智能工单分析系统，致力于将传统工单处理流程从“人工驱动”升级为“AI驱动”。

系统通过引入自然语言理解（NLP）、知识库检索（RAG）以及自动化决策能力，实现以下核心能力：

+ 自动理解工单内容（语义分析）
+ 自动分类与优先级判断
+ 智能推荐处理人
+ 自动生成处理建议
+ 逐步向“自动处理工单”演进
🎯 业务定位

本项目属于以下领域的交叉融合：
`AIOps（智能运维）
智能客服系统
企业级AI应用中台
⚙️ 解决的核心问题
🚨 传统工单系统痛点
人工分类效率低
依赖经验，标准不统一
工单分配错误率高
重复劳动严重
数据无法沉淀利用
🤖 AI智能工单系统优化后`

用户提工单
    ↓
AI语义分析
    ↓
自动分类 / 紧急程度判断
    ↓
智能推荐处理人
    ↓
生成处理建议
    ↓
（未来可自动处理）

📈 核心价值
降本：减少人工参与
提效：秒级分析工单
提质：统一处理标准
数据化：沉淀企业知识库

🔮 未来演进方向
从“AI辅助” → “AI自动处理（Agent）”
从“单点AI能力” → “企业AI中台”
从“规则驱动” → “数据驱动 + 模型学习”
从“人处理问题” → “AI处理问题”

🏗️ 技术架构
前端（Vue3 + Element Plus）
    ↓
API 网关
    ↓
Spring Boot 后端
    ↓
AI 服务（OpenAI / 通义 / Claude）
    ↓
知识库（PostgreSQL + 向量检索）
    ↓
缓存 / 消息 / 调度（Redis / MQ / 定时任务）

🧰 技术栈
Java 21
Spring Boot 3
Maven
PostgreSQL
Redis
MyBatis-Plus
Sa-Token（鉴权）
Spring Validation
Lombok
MapStruct
Springdoc OpenAPI

📦 模块结构
当前为 第一阶段：单体模块化架构
auth            # 认证模块（登录 / Token / 鉴权）
system          # 系统管理（用户 / 角色 / 菜单 / 部门 / 日志）
ticket          # 工单模块（核心业务）
knowledge       # 知识库（RAG预留）
ai              # AI分析模块（LLM接入预留）
report          # 报表分析模块（数据洞察）
infrastructure  # 基础设施（DB / Redis / MQ / 向量检索）

🧩 核心模块说明
🧾 ticket（工单模块）
支持：
工单新增 / 编辑 / 删除（逻辑删除）
工单详情
回收站 / 恢复
工单生命周期管理
🤖 ai（AI分析模块）

预留能力：
+ 工单语义分析
+ 分类识别
+ 优先级判断
+ 处理建议生成
+ 智能分配
+ 
📚 knowledge（知识库）

用于：
存储历史工单经验
支持 RAG（检索增强生成）
提供 AI 决策依据
📊 report（报表模块）

未来支持：

工单趋势分析
部门效率分析
高频问题统计
🧱 infrastructure（基础设施）

统一抽象：

数据访问（MyBatis）
缓存（Redis）
消息队列（MQ）
定时任务
向量检索（为 AI 做准备）
🚀 本地启动
1️⃣ 创建数据库
create database ai_ticket;
2️⃣ 初始化数据
psql -d ai_ticket -f src/main/resources/db/init/schema.sql
psql -d ai_ticket -f src/main/resources/db/init/data.sql
3️⃣ 修改配置

编辑：

src/main/resources/application-dev.yml

配置：
PostgreSQL 连接
Redis 连接
4️⃣ 启动项目
mvn spring-boot:run
🌐 访问入口
功能	地址
API 前缀	/api
Swagger UI	/api/swagger-ui/index.html
OpenAPI JSON	/api/v3/api-docs

🔐 登录方式
登录接口
POST /api/auth/login
默认账号
admin / admin123
Token 使用

登录成功后，在请求头中添加：

Authorization: Bearer <token>
🧠 技术亮点（重点）

本项目不仅是 CRUD 系统，而是面向 AI 演进的架构设计：

支持 LLM（大模型）接入
预留 RAG知识库能力
支持 流式响应（SSE/WebSocket）
模块化设计，便于拆分微服务
清晰的领域划分（领域驱动思想雏形）
🎯 项目目标

打造一个可演进的：

企业级 AI 工单中台系统（AIOps Platform）

最终形态：
用户提问 → AI分析 → AI决策 → AI处理 → 人只做复杂判断
📌 总结

AI Ticket Server 不只是一个工单系统，而是：

👉 让“人处理问题” → 变成“AI处理问题”