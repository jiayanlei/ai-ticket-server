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

    @Schema(description = "工单状态", allowableValues = {"DRAFT", "PENDING_ACCEPT", "ACCEPTED", "PROCESSING", "PENDING", "WAIT_CONFIRM", "COMPLETED", "CLOSED", "REJECTED"}, example = "DRAFT")
    private String status;

    @Schema(description = "优先级", allowableValues = {"LOW", "NORMAL", "HIGH", "URGENT"}, example = "NORMAL")
    private String priority;

    @Schema(description = "来源", example = "WEB")
    private String source;

    @Schema(description = "分类", example = "IT")
    private String category;

    @Schema(description = "申请人 ID", example = "1")
    private Long applicantId;

    @Schema(description = "申请人姓名", example = "系统管理员")
    private String applicantName;

    @Schema(description = "处理人 ID", example = "1")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "系统管理员")
    private String handlerName;

    @Schema(description = "期望完成时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-30 18:00:00")
    private LocalDateTime expectedFinishTime;

    @Schema(description = "提交时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submitTime;

    @Schema(description = "受理时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acceptTime;

    @Schema(description = "开始处理时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startProcessTime;

    @Schema(description = "处理完成时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finishTime;

    @Schema(description = "关闭时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @Schema(description = "挂起时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime suspendTime;

    @Schema(description = "恢复时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime resumeTime;

    @Schema(description = "SLA 截止时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime slaDeadline;

    @Schema(description = "是否超时", example = "false")
    private Boolean isTimeout;

    @Schema(description = "AI 分类")
    private String aiCategory;

    @Schema(description = "AI 风险等级", example = "LOW")
    private String aiRiskLevel;

    @Schema(description = "AI 推荐部门")
    private String aiRecommendDept;

    @Schema(description = "AI 推荐处理人")
    private String aiRecommendHandler;

    @Schema(description = "AI 预估处理时长")
    private String aiEstimatedTime;

    @Schema(description = "AI 摘要")
    private String aiSummary;

    @Schema(description = "AI 处理建议")
    private String aiSuggestion;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "挂起原因")
    private String suspendReason;

    @Schema(description = "重新打开原因")
    private String reopenReason;

    @Schema(description = "兼容字段：处理人 ID")
    private Long assigneeId;

    @Schema(description = "兼容字段：处理人姓名")
    private String assigneeName;

    @Schema(description = "兼容字段：期望完成时间")
    private LocalDateTime dueTime;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-04-23 09:00:00")
    private LocalDateTime updateTime;
}
