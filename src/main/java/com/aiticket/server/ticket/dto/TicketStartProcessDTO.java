package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "开始处理工单请求")
public class TicketStartProcessDTO {

    @Size(max = 500, message = "备注不能超过500个字符")
    @Schema(description = "处理备注")
    private String remark;
}
