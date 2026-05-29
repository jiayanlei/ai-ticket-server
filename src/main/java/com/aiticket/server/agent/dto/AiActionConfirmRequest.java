package com.aiticket.server.agent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "AI Agent 操作确认请求")
public class AiActionConfirmRequest {

    @Schema(description = "会话 ID", example = "codex-session-001")
    private String sessionId;

    @NotBlank(message = "操作类型不能为空")
    @Schema(description = "确认的操作类型", example = "GENERATE_DIFF")
    private String actionType;

    @NotNull(message = "确认结果不能为空")
    @Schema(description = "是否确认执行", example = "true")
    private Boolean confirm;
}
