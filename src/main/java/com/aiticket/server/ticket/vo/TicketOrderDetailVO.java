package com.aiticket.server.ticket.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "工单详情响应")
public class TicketOrderDetailVO {

    @Schema(description = "工单主信息")
    private TicketOrderVO ticket;

    @Schema(description = "流程记录")
    private List<TicketFlowRecordVO> flowRecords;

    @Schema(description = "评论列表")
    private List<TicketCommentVO> comments;

    @Schema(description = "附件列表")
    private List<TicketAttachmentVO> attachments;

    @Schema(description = "AI 分析结果")
    private TicketAiAnalysisVO aiAnalysis;
}
