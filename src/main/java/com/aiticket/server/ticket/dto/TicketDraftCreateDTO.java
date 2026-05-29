package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "工单草稿创建请求")
public class TicketDraftCreateDTO {

    @NotBlank(message = "工单标题不能为空")
    @Size(max = 255, message = "工单标题不能超过255个字符")
    @Schema(description = "工单标题", example = "无法登录系统")
    private String title;

    @Schema(description = "优先级", allowableValues = {"LOW", "NORMAL", "HIGH", "URGENT"}, example = "NORMAL")
    private String priority;

    @Schema(description = "来源", example = "WEB")
    private String source;

    @Schema(description = "分类", example = "IT")
    private String category;

    @Schema(description = "申请人 ID", example = "1")
    private Long applicantId;

    @Schema(description = "申请人姓名", example = "系统管理员")
    private String applicantName;

    @Schema(description = "处理人 ID", example = "2")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "运维工程师")
    private String handlerName;

    @Schema(description = "期望完成时间，格式 yyyy-MM-dd HH:mm:ss", example = "2026-05-14 18:00:00")
    private LocalDateTime expectedFinishTime;

    @NotBlank(message = "工单描述不能为空")
    @Schema(description = "工单描述", example = "用户反馈输入正确密码后仍提示登录失败")
    private String description;
}
