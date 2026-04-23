package com.aiticket.server.ticket.entity;

import com.aiticket.server.common.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ticket_flow_record")
@Schema(description = "工单流转记录")
public class TicketFlowRecord extends BaseEntity {

    private Long ticketId;

    private String fromStatus;

    private String toStatus;

    private Long operatorId;

    private String operatorName;

    private String action;

    private String remark;
}
