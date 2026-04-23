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

    @Schema(description = "优先级：LOW/NORMAL/HIGH/URGENT")
    private String priority;

    @Schema(description = "状态：NEW/PROCESSING/PENDING/RESOLVED/CLOSED")
    private String status;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "分类")
    private String category;

    @Schema(description = "申请人ID")
    private Long applicantId;

    @Schema(description = "申请人")
    private String applicantName;

    @Schema(description = "处理人ID")
    private Long assigneeId;

    @Schema(description = "处理人")
    private String assigneeName;

    @Schema(description = "期望完成时间")
    private LocalDateTime dueTime;

    @Schema(description = "解决时间")
    private LocalDateTime resolvedTime;

    @Schema(description = "关闭时间")
    private LocalDateTime closedTime;

    @Schema(description = "AI 摘要")
    private String aiSummary;

    @Schema(description = "AI 风险等级")
    private String aiRiskLevel;
}
