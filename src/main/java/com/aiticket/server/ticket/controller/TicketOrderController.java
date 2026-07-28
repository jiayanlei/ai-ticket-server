package com.aiticket.server.ticket.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.config.OpenApiConfig;
import com.aiticket.server.ticket.dto.TicketAcceptDTO;
import com.aiticket.server.ticket.dto.TicketCommentCreateDTO;
import com.aiticket.server.ticket.dto.TicketConfirmDTO;
import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketDraftCreateDTO;
import com.aiticket.server.ticket.dto.TicketFinishDTO;
import com.aiticket.server.ticket.dto.TicketQueryRequest;
import com.aiticket.server.ticket.dto.TicketRejectDTO;
import com.aiticket.server.ticket.dto.TicketReopenDTO;
import com.aiticket.server.ticket.dto.TicketResumeDTO;
import com.aiticket.server.ticket.dto.TicketStartProcessDTO;
import com.aiticket.server.ticket.dto.TicketSubmitDTO;
import com.aiticket.server.ticket.dto.TicketSuspendDTO;
import com.aiticket.server.ticket.dto.TicketTransferDTO;
import com.aiticket.server.ticket.dto.TicketUpdateRequest;
import com.aiticket.server.ticket.service.TicketOrderService;
import com.aiticket.server.ticket.vo.TicketActionResultVO;
import com.aiticket.server.ticket.vo.TicketAiAnalysisVO;
import com.aiticket.server.ticket.vo.TicketAttachmentVO;
import com.aiticket.server.ticket.vo.TicketCommentVO;
import com.aiticket.server.ticket.vo.TicketFlowRecordVO;
import com.aiticket.server.ticket.vo.TicketOrderDetailVO;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@Tag(name = "工单流程管理")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping({"/ticket/order", "/tickets"})
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    @SaCheckPermission("ticket:order:list")
    @Operation(summary = "工单分页列表", description = "查询未删除工单，支持关键词、状态、优先级、分类、申请人、处理人筛选。")
    @GetMapping
    public ApiResponse<PageResult<TicketOrderVO>> list(@ParameterObject @Valid TicketQueryRequest request) {
        return ApiResponse.ok(ticketOrderService.pageTickets(request));
    }

    @SaCheckPermission("ticket:order:create")
    @Operation(summary = "新增工单", description = "创建工单草稿并返回新工单 ID。兼容前端 /tickets 创建路径。")
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody TicketCreateRequest request) {
        return ApiResponse.ok(ticketOrderService.createTicket(request));
    }

    @SaCheckPermission("ticket:order:create")
    @Operation(summary = "新建草稿", description = "创建工单草稿，状态为 DRAFT，并写入流程记录。")
    @PostMapping("/draft")
    public ApiResponse<Long> createDraft(@Valid @RequestBody TicketDraftCreateDTO request) {
        return ApiResponse.ok(ticketOrderService.createDraft(request));
    }

    @SaCheckPermission("ticket:order:submit")
    @Operation(summary = "提交工单", description = "仅允许 DRAFT 提交为 PENDING_ACCEPT，写入提交时间、SLA 截止时间，并触发 mock AI 分析。")
    @PostMapping("/{id}/submit")
    public ApiResponse<TicketActionResultVO> submit(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                    @Valid @RequestBody(required = false) TicketSubmitDTO request) {
        ticketOrderService.submit(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:accept")
    @Operation(summary = "立即受理", description = "仅允许 PENDING_ACCEPT 状态受理为 ACCEPTED，写入受理时间和处理人。")
    @PostMapping("/{id}/accept")
    public ApiResponse<TicketActionResultVO> accept(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                    @Valid @RequestBody(required = false) TicketAcceptDTO request) {
        ticketOrderService.accept(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "开始处理", description = "仅允许 ACCEPTED 状态进入 PROCESSING。")
    @PostMapping("/{id}/start-process")
    public ApiResponse<TicketActionResultVO> startProcess(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                          @Valid @RequestBody(required = false) TicketStartProcessDTO request) {
        ticketOrderService.startProcess(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "挂起工单", description = "仅允许 PROCESSING 状态挂起为 PENDING。")
    @PostMapping("/{id}/suspend")
    public ApiResponse<TicketActionResultVO> suspend(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                     @Valid @RequestBody(required = false) TicketSuspendDTO request) {
        ticketOrderService.suspend(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "恢复处理", description = "仅允许 PENDING 状态恢复为 PROCESSING。")
    @PostMapping("/{id}/resume")
    public ApiResponse<TicketActionResultVO> resume(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                    @Valid @RequestBody(required = false) TicketResumeDTO request) {
        ticketOrderService.resume(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:finish")
    @Operation(summary = "处理完成", description = "仅允许 PROCESSING 状态进入 WAIT_CONFIRM。")
    @PostMapping("/{id}/finish")
    public ApiResponse<TicketActionResultVO> finish(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                    @Valid @RequestBody(required = false) TicketFinishDTO request) {
        ticketOrderService.finish(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:confirm")
    @Operation(summary = "确认完成", description = "仅允许 WAIT_CONFIRM 状态确认为 COMPLETED，并判断是否超时。")
    @PostMapping("/{id}/confirm")
    public ApiResponse<TicketActionResultVO> confirm(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                     @Valid @RequestBody(required = false) TicketConfirmDTO request) {
        ticketOrderService.confirm(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "重新打开", description = "仅允许 WAIT_CONFIRM 或 COMPLETED 状态重新打开为 PROCESSING。")
    @PostMapping("/{id}/reopen")
    public ApiResponse<TicketActionResultVO> reopen(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                    @Valid @RequestBody(required = false) TicketReopenDTO request) {
        ticketOrderService.reopen(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:transfer")
    @Operation(summary = "转派工单", description = "允许 PENDING_ACCEPT、ACCEPTED、PROCESSING 状态转派，状态保持不变。")
    @PostMapping("/{id}/transfer")
    public ApiResponse<TicketActionResultVO> transfer(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                      @Valid @RequestBody TicketTransferDTO request) {
        ticketOrderService.transfer(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:accept")
    @Operation(summary = "驳回工单", description = "仅允许 PENDING_ACCEPT 状态驳回为 REJECTED。")
    @PostMapping("/{id}/reject")
    public ApiResponse<TicketActionResultVO> reject(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                                    @Valid @RequestBody TicketRejectDTO request) {
        ticketOrderService.reject(id, request);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:confirm")
    @Operation(summary = "关闭工单", description = "仅允许 COMPLETED 状态关闭为 CLOSED，用于完成 COMPLETED -> CLOSED 生命周期。")
    @PostMapping("/{id}/close")
    public ApiResponse<TicketActionResultVO> close(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        ticketOrderService.close(id);
        return ApiResponse.ok(buildActionResult(id));
    }

    @SaCheckPermission("ticket:order:edit")
    @Operation(summary = "修改工单", description = "修改工单基础信息。状态流转请使用专用生命周期接口。")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                    @Valid @RequestBody TicketUpdateRequest request) {
        ticketOrderService.updateTicket(id, request);
        return ApiResponse.ok();
    }

    @SaCheckPermission("ticket:order:detail")
    @Operation(summary = "工单详情", description = "返回工单主信息、流程记录、评论列表、附件列表和 AI 分析结果。")
    @GetMapping("/{id}")
    public ApiResponse<TicketOrderDetailVO> detail(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(ticketOrderService.getTicketDetail(id));
    }

    @SaCheckPermission("ticket:order:detail")
    @Operation(summary = "流程记录", description = "查询工单的全部流程记录。")
    @GetMapping("/{id}/flow-records")
    public ApiResponse<List<TicketFlowRecordVO>> flowRecords(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(ticketOrderService.listFlowRecords(id));
    }

    @SaCheckPermission("ticket:order:detail")
    @Operation(summary = "评论列表", description = "查询工单评论列表。")
    @GetMapping("/{id}/comments")
    public ApiResponse<List<TicketCommentVO>> comments(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(ticketOrderService.listComments(id));
    }

    @SaCheckPermission("ticket:order:comment")
    @Operation(summary = "新增评论", description = "给工单新增评论。")
    @PostMapping("/{id}/comments")
    public ApiResponse<Long> addComment(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                        @Valid @RequestBody TicketCommentCreateDTO request) {
        return ApiResponse.ok(ticketOrderService.addComment(id, request));
    }

    @SaCheckPermission("ticket:order:detail")
    @Operation(summary = "附件列表", description = "查询工单附件列表。")
    @GetMapping("/{id}/attachments")
    public ApiResponse<List<TicketAttachmentVO>> attachments(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(ticketOrderService.listAttachments(id));
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "AI 分析", description = "使用 mock AI 生成分类、风险等级、推荐部门、推荐处理人、预估处理时长、摘要和处理建议。")
    @PostMapping("/{id}/ai/analyze")
    public ApiResponse<TicketAiAnalysisVO> analyze(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(ticketOrderService.analyze(id));
    }

    @SaCheckPermission("ticket:order:detail")
    @Operation(summary = "工单回收站", description = "查询已逻辑删除工单，分页结构与工单列表一致。")
    @GetMapping("/recycle-bin")
    public ApiResponse<PageResult<TicketOrderVO>> recycleBin(@ParameterObject @Valid TicketQueryRequest request) {
        return ApiResponse.ok(ticketOrderService.pageRecycleTickets(request));
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "恢复已删除工单", description = "恢复回收站中的逻辑删除工单。")
    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restore(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        ticketOrderService.restoreTicket(id);
        return ApiResponse.ok();
    }

    @SaCheckPermission("ticket:order:process")
    @Operation(summary = "逻辑删除工单", description = "逻辑删除工单，删除后可在回收站查询。")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        ticketOrderService.deleteTicket(id);
        return ApiResponse.ok();
    }

    private TicketActionResultVO buildActionResult(Long id) {
        TicketOrderVO ticket = ticketOrderService.getTicket(id);
        TicketActionResultVO result = new TicketActionResultVO();
        result.setId(ticket.getId());
        result.setTicketNo(ticket.getTicketNo());
        result.setTitle(ticket.getTitle());
        result.setStatus(ticket.getStatus());
        result.setPriority(ticket.getPriority());
        result.setAssigneeId(ticket.getAssigneeId());
        result.setAssigneeName(ticket.getAssigneeName());
        result.setHandlerId(ticket.getHandlerId());
        result.setHandlerName(ticket.getHandlerName());
        result.setAcceptedTime(ticket.getAcceptTime());
        result.setStartProcessTime(ticket.getStartProcessTime());
        result.setFinishTime(ticket.getFinishTime());
        result.setCompletedTime(ticket.getCloseTime());
        result.setCloseTime(ticket.getCloseTime());
        result.setUpdateTime(ticket.getUpdateTime());
        return result;
    }
}
