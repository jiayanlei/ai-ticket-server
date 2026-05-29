package com.aiticket.server.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI Agent 操作确认响应")
public class AiActionConfirmResponse {

    @Schema(description = "是否成功", example = "true")
    private Boolean success;

    @Schema(description = "提示消息", example = "操作已确认，第一阶段仅记录确认结果，暂不执行真实命令。")
    private String message;
}
