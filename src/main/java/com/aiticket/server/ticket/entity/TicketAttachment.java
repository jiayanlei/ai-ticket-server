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
@TableName("ticket_attachment")
@Schema(description = "工单附件")
public class TicketAttachment {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private Long id;

    @Schema(description = "工单ID")
    private Long ticketId;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "文件地址")
    private String fileUrl;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "上传人ID")
    private Long uploadUserId;

    @Schema(description = "上传人姓名")
    private String uploadUserName;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
