package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "新增工单评论请求")
public class TicketCommentCreateDTO {

    @Schema(description = "评论人 ID", example = "1")
    private Long userId;

    @Schema(description = "评论人姓名", example = "系统管理员")
    private String userName;

    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容", example = "已联系用户补充截图")
    private String content;
}
