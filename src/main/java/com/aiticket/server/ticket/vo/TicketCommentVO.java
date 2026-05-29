package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单评论响应")
public class TicketCommentVO {

    @Schema(description = "评论 ID", example = "1")
    private Long id;

    @Schema(description = "工单 ID", example = "1")
    private Long ticketId;

    @Schema(description = "评论人 ID", example = "1")
    private Long userId;

    @Schema(description = "评论人姓名", example = "系统管理员")
    private String userName;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "创建时间，格式 yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
