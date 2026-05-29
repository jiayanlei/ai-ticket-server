package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单附件响应")
public class TicketAttachmentVO {

    @Schema(description = "附件 ID", example = "1")
    private Long id;

    @Schema(description = "工单 ID", example = "1")
    private Long ticketId;

    @Schema(description = "文件名", example = "error.png")
    private String fileName;

    @Schema(description = "文件地址")
    private String fileUrl;

    @Schema(description = "文件大小，单位字节", example = "1024")
    private Long fileSize;

    @Schema(description = "文件类型", example = "image/png")
    private String fileType;

    @Schema(description = "上传人 ID", example = "1")
    private Long uploadUserId;

    @Schema(description = "上传人姓名", example = "系统管理员")
    private String uploadUserName;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
