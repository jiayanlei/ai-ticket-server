package com.aiticket.server.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI Agent 最近日志")
public class AiRecentLogVO {

    @Schema(description = "日志类型", allowableValues = {"frontend", "backend"}, example = "backend")
    private String type;

    @Schema(description = "最近日志内容")
    private List<String> logs;
}
