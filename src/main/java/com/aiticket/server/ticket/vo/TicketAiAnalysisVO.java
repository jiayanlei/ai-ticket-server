package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "工单 AI 分析结果")
public class TicketAiAnalysisVO {

    @Schema(description = "AI 分类", example = "账号与权限")
    private String aiCategory;

    @Schema(description = "AI 风险等级", example = "MEDIUM")
    private String aiRiskLevel;

    @Schema(description = "AI 推荐部门", example = "信息技术部")
    private String aiRecommendDept;

    @Schema(description = "AI 推荐处理人", example = "运维工程师")
    private String aiRecommendHandler;

    @Schema(description = "AI 预估处理时长", example = "4小时")
    private String aiEstimatedTime;

    @Schema(description = "AI 摘要")
    private String aiSummary;

    @Schema(description = "AI 处理建议")
    private String aiSuggestion;
}
