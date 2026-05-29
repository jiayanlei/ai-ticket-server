package com.aiticket.server.ticket.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("ticket_flow_record")
@Schema(description = "工单流转记录")
public class TicketFlowRecord {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "工单ID")
    private Long ticketId;

    @Schema(description = "操作人ID")
    private Long operatorId;

    @Schema(description = "操作人姓名")
    private String operatorName;

    @Schema(description = "动作")
    private String action;

    @Schema(description = "变更前状态")
    private String beforeStatus;

    @Schema(description = "变更后状态")
    private String afterStatus;

    @Schema(description = "备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
