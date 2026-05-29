package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单修改请求")
public class TicketUpdateRequest {

    @NotBlank(message = "工单标题不能为空")
    @Schema(description = "工单标题", example = "无法登录系统")
    private String title;

    @NotBlank(message = "工单描述不能为空")
    @Schema(description = "工单描述", example = "用户反馈输入正确密码后仍提示登录失败")
    private String description;

    @Schema(description = "优先级", allowableValues = {"LOW", "NORMAL", "HIGH", "URGENT"}, example = "HIGH")
    private String priority;

    @Schema(description = "工单状态。状态流转请使用专用接口，此字段在普通修改中会被忽略。", allowableValues = {"DRAFT", "PENDING_ACCEPT", "ACCEPTED", "PROCESSING", "PENDING", "WAIT_CONFIRM", "COMPLETED", "CLOSED", "REJECTED"}, example = "PROCESSING")
    private String status;

    @Schema(description = "来源", example = "WEB")
    private String source;

    @Schema(description = "分类", example = "IT")
    private String category;

    @Schema(description = "处理人 ID", example = "1")
    private Long assigneeId;

    @Schema(description = "处理人姓名", example = "系统管理员")
    private String assigneeName;

    @Schema(description = "处理人 ID", example = "1")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "系统管理员")
    private String handlerName;

    @Schema(description = "期望完成时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-30 18:00:00")
    private LocalDateTime dueTime;

    @Schema(description = "期望完成时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-30 18:00:00")
    private LocalDateTime expectedFinishTime;

    @Schema(description = "解决时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-24 10:00:00")
    private LocalDateTime resolvedTime;

    @Schema(description = "关闭时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-25 10:00:00")
    private LocalDateTime closedTime;

    @Schema(description = "AI 摘要")
    private String aiSummary;

    @Schema(description = "AI 风险等级", example = "LOW")
    private String aiRiskLevel;
}
