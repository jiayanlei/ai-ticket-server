package com.aiticket.server.ticket.dto;

import com.aiticket.server.common.page.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "工单查询参数")
public class TicketQueryRequest extends PageQuery {

    @Schema(description = "关键词，匹配标题或编号", example = "网络")
    private String keyword;

    @Schema(description = "工单状态", allowableValues = {"DRAFT", "PENDING_ACCEPT", "ACCEPTED", "PROCESSING", "PENDING", "WAIT_CONFIRM", "COMPLETED", "CLOSED", "REJECTED"}, example = "DRAFT")
    private String status;

    @Schema(description = "优先级", allowableValues = {"LOW", "NORMAL", "HIGH", "URGENT"}, example = "NORMAL")
    private String priority;

    @Schema(description = "分类", example = "IT")
    private String category;

    @Schema(description = "处理人 ID", example = "1")
    private Long assigneeId;

    @Schema(description = "处理人 ID", example = "1")
    private Long handlerId;

    @Schema(description = "申请人 ID", example = "1")
    private Long applicantId;
}
