package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "恢复处理工单请求")
public class TicketResumeDTO {

    @Size(max = 500, message = "备注不能超过500个字符")
    @Schema(description = "恢复备注")
    private String remark;
}
