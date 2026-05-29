package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "挂起工单请求")
public class TicketSuspendDTO {

    @NotBlank(message = "挂起原因不能为空")
    @Size(max = 500, message = "挂起原因不能超过500个字符")
    @Schema(description = "挂起原因", example = "等待第三方系统反馈")
    private String suspendReason;
}
