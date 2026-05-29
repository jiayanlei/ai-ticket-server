package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单流程记录响应")
public class TicketFlowRecordVO {

    @Schema(description = "记录 ID", example = "1")
    private Long id;

    @Schema(description = "工单 ID", example = "1")
    private Long ticketId;

    @Schema(description = "操作人 ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作人姓名", example = "系统管理员")
    private String operatorName;

    @Schema(description = "动作编码", example = "SUBMIT")
    private String action;

    @Schema(description = "动作名称", example = "提交工单")
    private String actionName;

    @Schema(description = "变更前状态", example = "DRAFT")
    private String beforeStatus;

    @Schema(description = "变更后状态", example = "PENDING_ACCEPT")
    private String afterStatus;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
