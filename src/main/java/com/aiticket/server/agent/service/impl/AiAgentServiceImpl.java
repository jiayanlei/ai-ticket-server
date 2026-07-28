package com.aiticket.server.agent.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.aiticket.server.agent.dto.AiActionConfirmRequest;
import com.aiticket.server.agent.dto.AiChatRequest;
import com.aiticket.server.agent.dto.AiLogQueryRequest;
import com.aiticket.server.agent.enums.AiFileChangeTypeEnum;
import com.aiticket.server.agent.enums.AiRiskLevelEnum;
import com.aiticket.server.agent.enums.AiTaskTypeEnum;
import com.aiticket.server.agent.service.AiAgentService;
import com.aiticket.server.agent.tools.ProjectLogReadTool;
import com.aiticket.server.agent.tools.ProjectStatusTool;
import com.aiticket.server.agent.tools.SafeCommandTool;
import com.aiticket.server.agent.vo.AiActionConfirmResponse;
import com.aiticket.server.agent.vo.AiActionPlanVO;
import com.aiticket.server.agent.vo.AiAffectedFileVO;
import com.aiticket.server.agent.vo.AiChatResponse;
import com.aiticket.server.agent.vo.AiProjectStatusVO;
import com.aiticket.server.agent.vo.AiRecentLogVO;
import com.aiticket.server.ai.DeepSeekChatClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiAgentServiceImpl implements AiAgentService {

    private final ProjectStatusTool projectStatusTool;
    private final ProjectLogReadTool projectLogReadTool;
    private final SafeCommandTool safeCommandTool;
    private final DeepSeekChatClient deepSeekChatClient;

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        String sessionId = resolveSessionId(request.getSessionId());
        AiTaskTypeEnum taskType = AiTaskTypeEnum.of(request.getTaskType());
        AiActionPlanVO plan = buildPlan(taskType, request.getMessage());
        String reply = buildAiReply(taskType, request.getMessage(), plan);
        logOperation(sessionId, taskType.name(), request.getMessage(), plan.getRiskLevel(), null);
        return new AiChatResponse(sessionId, reply, plan, true);
    }

    @Override
    public AiProjectStatusVO getProjectStatus() {
        return projectStatusTool.getStatus();
    }

    @Override
    public AiRecentLogVO getRecentLogs(AiLogQueryRequest request) {
        return projectLogReadTool.recentLogs(request == null ? null : request.getType());
    }

    @Override
    public AiActionConfirmResponse confirmAction(AiActionConfirmRequest request) {
        String sessionId = resolveSessionId(request.getSessionId());
        request.setSessionId(sessionId);
        String actionType = request.getActionType();
        AiRiskLevelEnum riskLevel = resolveActionRiskLevel(actionType);
        logOperation(sessionId, actionType, "人工确认动作", riskLevel.name(), request.getConfirm());
        return safeCommandTool.confirmOnly(request);
    }

    private AiActionPlanVO buildPlan(AiTaskTypeEnum taskType, String message) {
        return switch (taskType) {
            case FRONTEND_ERROR -> frontendErrorPlan();
            case BACKEND_API -> backendApiPlan();
            case BACKEND_LOG -> backendLogPlan();
            case SQL_GENERATE -> sqlGeneratePlan();
            case FRONTEND_DEPLOY -> frontendDeployPlan();
            case BACKEND_DEPLOY -> backendDeployPlan();
            case GIT_CHECK -> gitCheckPlan();
            case CHANGE_PLAN -> changePlan(message);
            case NORMAL_CHAT -> normalChatPlan();
        };
    }

    private AiActionPlanVO frontendErrorPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.FRONTEND_ERROR.getDescription())
                .riskLevel(AiRiskLevelEnum.LOW.name())
                .summary("分析前端报错并给出修复建议")
                .scope(List.of("ai-ticket-web"))
                .affectedFiles(List.of(
                        affectedFile("src/views/dashboard/workbench/index.vue", AiFileChangeTypeEnum.CHECK, "检查大屏页面组件和图表初始化逻辑"),
                        affectedFile("src/router/index.ts", AiFileChangeTypeEnum.CHECK, "检查路由配置和页面缓存配置"),
                        affectedFile("src/api/index.ts", AiFileChangeTypeEnum.CHECK, "检查接口封装和错误响应处理")
                ))
                .steps(List.of(
                        "收集前端报错信息",
                        "定位相关页面文件",
                        "分析组件依赖和路由配置",
                        "输出修复方案",
                        "执行 pnpm build 验证"
                ))
                .needConfirm(false)
                .build();
    }

    private AiActionPlanVO backendApiPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.BACKEND_API.getDescription())
                .riskLevel(AiRiskLevelEnum.MEDIUM.name())
                .summary("分析新增后端接口需求，并规划 controller、service、mapper、VO 与 mapper.xml 变更")
                .scope(List.of("ai-ticket-server"))
                .affectedFiles(List.of(
                        affectedFile("src/main/java/com/aiticket/server/**/controller", AiFileChangeTypeEnum.CREATE, "新增或扩展接口入口"),
                        affectedFile("src/main/java/com/aiticket/server/**/service", AiFileChangeTypeEnum.CREATE, "新增业务接口和实现"),
                        affectedFile("src/main/java/com/aiticket/server/**/mapper", AiFileChangeTypeEnum.CREATE, "预留数据访问层变更"),
                        affectedFile("src/main/java/com/aiticket/server/**/vo", AiFileChangeTypeEnum.CREATE, "新增接口响应结构"),
                        affectedFile("src/main/resources/mapper/**/*.xml", AiFileChangeTypeEnum.CREATE, "预留复杂 SQL 映射文件变更")
                ))
                .steps(List.of(
                        "澄清接口入参、出参与权限标识",
                        "设计 DTO、VO 和 Swagger 文档",
                        "规划 Service 与 Controller 分层",
                        "评估数据库访问和事务边界",
                        "人工确认后再进入真实代码变更"
                ))
                .needConfirm(true)
                .build();
    }

    private AiActionPlanVO backendLogPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.BACKEND_LOG.getDescription())
                .riskLevel(AiRiskLevelEnum.LOW.name())
                .summary("读取后端最近日志并分析异常线索")
                .scope(List.of("ai-ticket-server"))
                .affectedFiles(List.of(
                        affectedFile("./logs", AiFileChangeTypeEnum.CHECK, "检查应用日志目录和最近错误日志"),
                        affectedFile("src/main/resources/application.yml", AiFileChangeTypeEnum.CHECK, "确认日志级别和输出路径配置")
                ))
                .steps(List.of(
                        "获取最近后端日志",
                        "提取 ERROR 和 WARN 关键行",
                        "判断异常类型和可能影响面",
                        "输出排查建议"
                ))
                .needConfirm(false)
                .build();
    }

    private AiActionPlanVO sqlGeneratePlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.SQL_GENERATE.getDescription())
                .riskLevel(AiRiskLevelEnum.HIGH.name())
                .summary("生成 SQL 草案和风险提示，第一阶段不执行数据库 SQL")
                .scope(List.of("PostgreSQL"))
                .affectedFiles(List.of(
                        affectedFile("src/main/resources/db/init/schema.sql", AiFileChangeTypeEnum.CHECK, "检查表结构初始化脚本"),
                        affectedFile("src/main/resources/db/init/data.sql", AiFileChangeTypeEnum.CHECK, "检查初始化数据脚本")
                ))
                .steps(List.of(
                        "理解字段、索引和约束需求",
                        "生成 SQL 草案",
                        "标注潜在锁表、数据变更和回滚风险",
                        "等待人工确认",
                        "第一阶段不连接数据库、不执行 SQL"
                ))
                .needConfirm(true)
                .build();
    }

    private AiActionPlanVO frontendDeployPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.FRONTEND_DEPLOY.getDescription())
                .riskLevel(AiRiskLevelEnum.HIGH.name())
                .summary("规划前端构建发布步骤，第一阶段不执行构建和发布")
                .scope(List.of("ai-ticket-web", "Nginx"))
                .affectedFiles(List.of(
                        affectedFile("package.json", AiFileChangeTypeEnum.CHECK, "确认构建脚本"),
                        affectedFile("vite.config.ts", AiFileChangeTypeEnum.CHECK, "确认构建配置"),
                        affectedFile("nginx.conf", AiFileChangeTypeEnum.CHECK, "确认静态资源部署配置")
                ))
                .steps(List.of(
                        "确认前端分支和构建环境",
                        "检查依赖和构建脚本",
                        "生成发布前检查清单",
                        "等待人工确认",
                        "第一阶段不执行 pnpm build、不发布到 Nginx"
                ))
                .needConfirm(true)
                .build();
    }

    private AiActionPlanVO backendDeployPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.BACKEND_DEPLOY.getDescription())
                .riskLevel(AiRiskLevelEnum.HIGH.name())
                .summary("规划后端构建发布步骤，第一阶段不打包、不重启 Jar 服务")
                .scope(List.of("ai-ticket-server", "Jar 服务"))
                .affectedFiles(List.of(
                        affectedFile("pom.xml", AiFileChangeTypeEnum.CHECK, "确认打包配置和依赖"),
                        affectedFile("src/main/resources/application-prod.yml", AiFileChangeTypeEnum.CHECK, "确认生产环境配置"),
                        affectedFile("Dockerfile", AiFileChangeTypeEnum.CHECK, "确认镜像构建配置")
                ))
                .steps(List.of(
                        "确认后端分支和运行环境",
                        "检查 Maven 构建配置",
                        "生成发布前检查清单",
                        "等待人工确认",
                        "第一阶段不执行 mvn package、不重启服务"
                ))
                .needConfirm(true)
                .build();
    }

    private AiActionPlanVO gitCheckPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.GIT_CHECK.getDescription())
                .riskLevel(AiRiskLevelEnum.LOW.name())
                .summary("检查 Git 分支、最近提交和工作区状态")
                .scope(List.of("Git"))
                .affectedFiles(List.of())
                .steps(List.of(
                        "获取当前分支",
                        "检查工作区是否存在未提交变更",
                        "读取最近提交摘要",
                        "输出状态说明"
                ))
                .needConfirm(false)
                .build();
    }

    private AiActionPlanVO changePlan(String message) {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.CHANGE_PLAN.getDescription())
                .riskLevel(AiRiskLevelEnum.LOW.name())
                .summary("根据用户描述生成修改计划，不进入真实代码变更")
                .scope(resolveChangePlanScope(message))
                .affectedFiles(List.of(
                        affectedFile("待确认的相关模块文件", AiFileChangeTypeEnum.CHECK, "根据进一步需求定位具体影响文件")
                ))
                .steps(List.of(
                        "拆解用户需求",
                        "判断影响范围和风险等级",
                        "列出候选文件和验证方式",
                        "输出可执行计划",
                        "等待用户进一步确认是否进入实现"
                ))
                .needConfirm(false)
                .build();
    }

    private AiActionPlanVO normalChatPlan() {
        return AiActionPlanVO.builder()
                .taskType(AiTaskTypeEnum.NORMAL_CHAT.getDescription())
                .riskLevel(AiRiskLevelEnum.LOW.name())
                .summary("普通 AI 对话，仅提供分析建议")
                .scope(List.of("Codex 工作台"))
                .affectedFiles(List.of())
                .steps(List.of(
                        "理解用户问题",
                        "基于任务类型给出分析",
                        "必要时生成结构化执行计划"
                ))
                .needConfirm(false)
                .build();
    }

    private String buildReply(AiTaskTypeEnum taskType) {
        return switch (taskType) {
            case FRONTEND_ERROR -> "这是 AI 分析结果。当前问题可能与组件导入、依赖版本或路由缓存有关。建议先检查相关页面、接口封装和构建日志。";
            case BACKEND_API -> "我已生成后端接口新增的结构化计划。该任务可能涉及接口、业务层和数据访问层，属于中风险变更，需要人工确认后再进入真实代码修改。";
            case BACKEND_LOG -> "我会优先关注最近日志中的 ERROR 和 WARN，结合启动日志、SQL 异常和接口调用链给出排查建议。第一阶段仅返回 mock 日志分析入口。";
            case SQL_GENERATE -> "SQL 生成属于高风险动作。第一阶段只会提供 SQL 草案、风险说明和回滚建议，不会连接数据库，也不会执行任何 SQL。";
            case FRONTEND_DEPLOY -> "前端发布属于高风险动作。第一阶段只生成发布前检查计划和风险提示，不执行构建、不上传文件、不修改 Nginx。";
            case BACKEND_DEPLOY -> "后端发布属于高风险动作。第一阶段只生成发布计划和检查清单，不执行打包、不停止或重启 Jar 服务。";
            case GIT_CHECK -> "我会返回 Git 状态检查计划。第一阶段项目状态接口先使用 mock 数据，后续可接入真实 Git 检查。";
            case CHANGE_PLAN -> "我已根据你的描述生成修改计划。当前阶段只做分析和计划，不会自动改代码。";
            case NORMAL_CHAT -> "收到。我会先以安全的规则模式提供分析建议；如果需要执行计划，可以指定 taskType 生成结构化步骤和风险提示。";
        };
    }

    private String buildAiReply(AiTaskTypeEnum taskType, String message, AiActionPlanVO plan) {
        if (!deepSeekChatClient.isEnabled()) {
            return buildReply(taskType);
        }

        try {
            return deepSeekChatClient.chat(buildSystemPrompt(taskType, plan), message);
        } catch (Exception ex) {
            log.warn("DeepSeek AI 回复失败，使用本地兜底回复: {}", ex.getMessage());
            return buildReply(taskType) + "\n\n> DeepSeek 暂时不可用，已返回本地安全兜底方案。";
        }
    }

    private String buildSystemPrompt(AiTaskTypeEnum taskType, AiActionPlanVO plan) {
        return """
                你是 AI Ticket OS 的 AI 问答助手，面向前后端开发、运维和工单系统排查场景。
                当前任务类型：%s。
                当前计划摘要：%s。

                回复要求：
                - 使用简体中文。
                - 优先给可落地、低风险、最小改动的建议。
                - 不要声称已经修改代码、执行 SQL、发布系统或读取了你没有实际读取的文件。
                - 遇到高风险动作时，先说明风险和确认点，不要鼓励直接执行。
                - 可以使用 Markdown、列表和代码块，但保持简洁清楚。
                """.formatted(taskType.getDescription(), plan.getSummary());
    }

    private List<String> resolveChangePlanScope(String message) {
        List<String> scope = new ArrayList<>();
        String normalizedMessage = message == null ? "" : message.toLowerCase(Locale.ROOT);
        if (message != null && (message.contains("前端") || normalizedMessage.contains("vue") || normalizedMessage.contains("web"))) {
            scope.add("ai-ticket-web");
        }
        if (message != null && (message.contains("后端") || normalizedMessage.contains("java") || normalizedMessage.contains("api"))) {
            scope.add("ai-ticket-server");
        }
        if (message != null && (message.contains("数据库") || normalizedMessage.contains("sql") || normalizedMessage.contains("postgresql"))) {
            scope.add("PostgreSQL");
        }
        if (scope.isEmpty()) {
            scope.add("ai-ticket-web");
            scope.add("ai-ticket-server");
        }
        return scope;
    }

    private AiAffectedFileVO affectedFile(String filePath, AiFileChangeTypeEnum changeType, String description) {
        return new AiAffectedFileVO(filePath, changeType.name(), description);
    }

    private String resolveSessionId(String sessionId) {
        if (StringUtils.hasText(sessionId)) {
            return sessionId;
        }
        return UUID.randomUUID().toString();
    }

    private AiRiskLevelEnum resolveActionRiskLevel(String actionType) {
        if (!StringUtils.hasText(actionType)) {
            return AiRiskLevelEnum.MEDIUM;
        }
        String normalizedActionType = actionType.trim().toUpperCase(Locale.ROOT);
        if (normalizedActionType.contains("SQL") || normalizedActionType.contains("DEPLOY") || normalizedActionType.contains("DELETE")) {
            return AiRiskLevelEnum.HIGH;
        }
        if (normalizedActionType.contains("DIFF") || normalizedActionType.contains("WRITE")) {
            return AiRiskLevelEnum.MEDIUM;
        }
        return AiRiskLevelEnum.LOW;
    }

    private void logOperation(String sessionId, String taskType, String message, String riskLevel, Boolean confirm) {
        log.info("AI Agent 操作记录: operator={}, sessionId={}, taskType={}, message={}, riskLevel={}, confirm={}, createTime={}",
                currentOperator(),
                sessionId,
                taskType,
                message,
                riskLevel,
                confirm,
                LocalDateTime.now());
    }

    private String currentOperator() {
        try {
            if (StpUtil.isLogin()) {
                return String.valueOf(StpUtil.getLoginId());
            }
        } catch (Exception ex) {
            log.debug("获取当前登录用户失败", ex);
        }
        return "anonymous";
    }
}
