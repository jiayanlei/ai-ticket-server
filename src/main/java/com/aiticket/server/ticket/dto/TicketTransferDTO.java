package com.aiticket.server.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "转派工单请求")
public class TicketTransferDTO {

    @NotNull(message = "处理人ID不能为空")
    @Schema(description = "新处理人 ID", example = "3")
    private Long handlerId;

    @Schema(description = "新处理人姓名", example = "二线工程师")
    private String handlerName;

    @Size(max = 500, message = "备注不能超过500个字符")
    @Schema(description = "转派备注")
    private String remark;
}
