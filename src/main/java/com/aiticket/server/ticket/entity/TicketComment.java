package com.aiticket.server.ticket.entity;

import com.aiticket.server.common.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ticket_comment")
@Schema(description = "工单评论")
public class TicketComment extends BaseEntity {

    private Long ticketId;

    private Long userId;

    private String username;

    private String content;
}
