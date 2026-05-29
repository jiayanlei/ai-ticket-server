package com.aiticket.server.agent.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.agent.dto.AiActionConfirmRequest;
import com.aiticket.server.agent.dto.AiChatRequest;
import com.aiticket.server.agent.dto.AiLogQueryRequest;
import com.aiticket.server.agent.service.AiAgentService;
import com.aiticket.server.agent.vo.AiActionConfirmResponse;
import com.aiticket.server.agent.vo.AiChatResponse;
import com.aiticket.server.agent.vo.AiProjectStatusVO;
import com.aiticket.server.agent.vo.AiRecentLogVO;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.config.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Tag(name = "AI Agent")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai-agent")
public class AiAgentController {

    private final AiAgentService aiAgentService;

    @SaCheckPermission("ai:agent:chat")
    @Operation(summary = "AI 聊天", description = "根据任务类型返回规则模拟的 AI 回复和结构化执行计划。第一阶段不修改文件、不执行命令。")
    @PostMapping("/chat")
    public ApiResponse<AiChatResponse> chat(@Valid @RequestBody AiChatRequest request) {
        return ApiResponse.ok(aiAgentService.chat(request));
    }

    @SaCheckPermission("ai:agent:status")
    @Operation(summary = "项目状态", description = "返回前后端项目路径、分支、环境和最近提交等状态。第一阶段使用 mock Git 数据。")
    @GetMapping("/project/status")
    public ApiResponse<AiProjectStatusVO> projectStatus() {
        return ApiResponse.ok(aiAgentService.getProjectStatus());
    }

    @SaCheckPermission("ai:agent:logs")
    @Operation(summary = "最近日志", description = "按 frontend/backend 类型返回最近日志。第一阶段使用 mock 日志，后续可接入真实日志文件读取。")
    @GetMapping("/logs/recent")
    public ApiResponse<AiRecentLogVO> recentLogs(@ParameterObject @Valid AiLogQueryRequest request) {
        return ApiResponse.ok(aiAgentService.getRecentLogs(request));
    }

    @SaCheckPermission("ai:agent:confirm")
    @Operation(summary = "操作确认", description = "记录人工确认动作。第一阶段只记录确认结果，不执行真实命令、文件写入、SQL 或发布操作。")
    @PostMapping("/action/confirm")
    public ApiResponse<AiActionConfirmResponse> confirmAction(@Valid @RequestBody AiActionConfirmRequest request) {
        return ApiResponse.ok(aiAgentService.confirmAction(request));
    }
}
