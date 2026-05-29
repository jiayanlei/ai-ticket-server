package com.aiticket.server.agent.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "AI Agent 最近日志查询请求")
public class AiLogQueryRequest {

    @Schema(description = "日志类型", allowableValues = {"frontend", "backend"}, example = "backend")
    private String type;
}
