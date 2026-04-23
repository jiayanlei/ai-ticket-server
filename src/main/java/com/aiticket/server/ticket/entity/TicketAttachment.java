package com.aiticket.server.ticket.entity;

import com.aiticket.server.common.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ticket_attachment")
@Schema(description = "工单附件")
public class TicketAttachment extends BaseEntity {

    private Long ticketId;

    private String fileName;

    private String originalName;

    private String fileUrl;

    private Long fileSize;

    private String contentType;

    private String storageProvider;
}
