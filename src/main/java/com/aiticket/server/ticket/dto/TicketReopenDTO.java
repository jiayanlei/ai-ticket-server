package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "重新打开工单请求")
public class TicketReopenDTO {

    @NotBlank(message = "重新打开原因不能为空")
    @Size(max = 500, message = "重新打开原因不能超过500个字符")
    @Schema(description = "重新打开原因", example = "用户确认问题仍未解决")
    private String reopenReason;
}
