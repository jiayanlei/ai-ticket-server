package com.aiticket.server.ticket.entity;

import com.aiticket.server.common.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ticket_order")
@Schema(description = "工单")
public class TicketOrder extends BaseEntity {

    @Schema(description = "工单编号")
    private String ticketNo;

    @Schema(description = "工单标题")
    private String title;

    @Schema(description = "工单描述")
    private String description;

    @Schema(description = "状态：DRAFT/PENDING_ACCEPT/ACCEPTED/PROCESSING/PENDING/WAIT_CONFIRM/COMPLETED/CLOSED/REJECTED")
    private String status;

    @Schema(description = "优先级：LOW/NORMAL/HIGH/URGENT")
    private String priority;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "分类")
    private String category;

    @Schema(description = "申请人ID")
    private Long applicantId;

    @Schema(description = "申请人")
    private String applicantName;

    @Schema(description = "处理人ID")
    private Long handlerId;

    @Schema(description = "处理人")
    private String handlerName;

    @Schema(description = "期望完成时间")
    private LocalDateTime expectedFinishTime;

    @Schema(description = "提交时间")
    private LocalDateTime submitTime;

    @Schema(description = "受理时间")
    private LocalDateTime acceptTime;

    @Schema(description = "开始处理时间")
    private LocalDateTime startProcessTime;

    @Schema(description = "处理完成时间")
    private LocalDateTime finishTime;

    @Schema(description = "关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "挂起时间")
    private LocalDateTime suspendTime;

    @Schema(description = "恢复处理时间")
    private LocalDateTime resumeTime;

    @Schema(description = "SLA 截止时间")
    private LocalDateTime slaDeadline;

    @Schema(description = "是否超时")
    private Boolean isTimeout;

    @Schema(description = "AI 分类")
    private String aiCategory;

    @Schema(description = "AI 风险等级")
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
}
