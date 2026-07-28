package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单动作结果")
public class TicketActionResultVO {

    @Schema(description = "工单 ID", example = "1")
    private Long id;

    @Schema(description = "工单编号", example = "TK202604230900001234")
    private String ticketNo;

    @Schema(description = "工单标题")
    private String title;

    @Schema(description = "工单状态")
    private String status;

    @Schema(description = "优先级")
    private String priority;

    @Schema(description = "处理人 ID")
    private Long assigneeId;

    @Schema(description = "处理人姓名")
    private String assigneeName;

    @Schema(description = "处理人 ID")
    private Long handlerId;

    @Schema(description = "处理人姓名")
    private String handlerName;

    @Schema(description = "受理时间")
    private LocalDateTime acceptedTime;

    @Schema(description = "开始处理时间")
    private LocalDateTime startProcessTime;

    @Schema(description = "处理完成时间")
    private LocalDateTime finishTime;

    @Schema(description = "确认完成时间")
    private LocalDateTime completedTime;

    @Schema(description = "关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
