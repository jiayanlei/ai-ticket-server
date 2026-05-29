package com.aiticket.server.agent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "AI 聊天请求")
public class AiChatRequest {

    @Schema(description = "会话 ID，不传时后端自动生成", example = "codex-session-001")
    private String sessionId;

    @NotBlank(message = "消息内容不能为空")
    @Schema(description = "用户输入的问题或任务描述", example = "帮我分析前端报错")
    private String message;

    @Schema(description = "任务类型", allowableValues = {
            "NORMAL_CHAT", "FRONTEND_ERROR", "BACKEND_API", "BACKEND_LOG",
            "SQL_GENERATE", "FRONTEND_DEPLOY", "BACKEND_DEPLOY", "GIT_CHECK", "CHANGE_PLAN"
    }, example = "FRONTEND_ERROR")
    private String taskType;
}
