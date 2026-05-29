package com.aiticket.server.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI 聊天响应")
public class AiChatResponse {

    @Schema(description = "会话 ID", example = "codex-session-001")
    private String sessionId;

    @Schema(description = "AI 回复内容")
    private String reply;

    @Schema(description = "结构化执行计划")
    private AiActionPlanVO plan;

    @Schema(description = "是否成功", example = "true")
    private Boolean success;
}
