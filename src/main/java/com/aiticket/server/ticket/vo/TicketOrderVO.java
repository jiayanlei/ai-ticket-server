package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单响应")
public class TicketOrderVO {

    @Schema(description = "工单 ID", example = "1")
    private Long id;

    @Schema(description = "工单编号", example = "TK202604230900001234")
    private String ticketNo;

    @Schema(description = "工单标题", example = "无法登录系统")
    private String title;

    @Schema(description = "工单描述")
    private String description;

    @Schema(description = "优先级", allowableValues = {"LOW", "NORMAL", "HIGH", "URGENT"}, example = "NORMAL")
    private String priority;

    @Schema(description = "工单状态", allowableValues = {"NEW", "PROCESSING", "PENDING", "RESOLVED", "CLOSED"}, example = "NEW")
    private String status;

    @Schema(description = "来源", example = "WEB")
    private String source;

    @Schema(description = "分类", example = "IT")
    private String category;

    @Schema(description = "申请人 ID", example = "1")
    private Long applicantId;

    @Schema(description = "申请人姓名", example = "系统管理员")
    private String applicantName;

    @Schema(description = "处理人 ID", example = "1")
    private Long assigneeId;

    @Schema(description = "处理人姓名", example = "系统管理员")
    private String assigneeName;

    @Schema(description = "期望完成时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-30 18:00:00")
    private LocalDateTime dueTime;

    @Schema(description = "解决时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime resolvedTime;

    @Schema(description = "关闭时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closedTime;

    @Schema(description = "AI 摘要")
    private String aiSummary;

    @Schema(description = "AI 风险等级", example = "LOW")
    private String aiRiskLevel;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime updateTime;
}
