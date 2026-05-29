package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "驳回工单请求")
public class TicketRejectDTO {

    @NotBlank(message = "驳回原因不能为空")
    @Size(max = 500, message = "驳回原因不能超过500个字符")
    @Schema(description = "驳回原因", example = "问题描述不完整")
    private String rejectReason;
}
