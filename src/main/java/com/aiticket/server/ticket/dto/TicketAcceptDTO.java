package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "受理工单请求")
public class TicketAcceptDTO {

    @NotNull(message = "处理人ID不能为空")
    @Schema(description = "处理人 ID", example = "2")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "运维工程师")
    private String handlerName;

    @Size(max = 500, message = "备注不能超过500个字符")
    @Schema(description = "受理备注")
    private String remark;
}
