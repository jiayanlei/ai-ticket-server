package com.aiticket.server.ticket.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aiticket.server.common.core.ApiResponse;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.config.OpenApiConfig;
import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketQueryRequest;
import com.aiticket.server.ticket.dto.TicketUpdateRequest;
import com.aiticket.server.ticket.service.TicketOrderService;
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

@Validated
@Tag(name = "工单管理")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    @SaCheckPermission("ticket:order:list")
    @Operation(summary = "工单分页列表", description = "查询未删除工单，支持关键词、状态、优先级、分类、申请人、处理人筛选。")
    @GetMapping
    public ApiResponse<PageResult<TicketOrderVO>> list(@ParameterObject @Valid TicketQueryRequest request) {
        return ApiResponse.ok(ticketOrderService.pageTickets(request));
    }



    @SaCheckPermission("ticket:order:add")
    @Operation(summary = "新增工单", description = "创建工单并返回新工单 ID。ticketNo 服务端生成，status 默认 NEW，priority 默认 NORMAL。")
    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody TicketCreateRequest request) {
        return ApiResponse.ok(ticketOrderService.createTicket(request));
    }

    @SaCheckPermission("ticket:order:recycle")
    @Operation(summary = "工单回收站", description = "查询已逻辑删除工单，分页结构与工单列表一致。")
    @GetMapping("/recycle-bin")
    public ApiResponse<PageResult<TicketOrderVO>> recycleBin(@ParameterObject @Valid TicketQueryRequest request) {
        return ApiResponse.ok(ticketOrderService.pageRecycleTickets(request));
    }

    @SaCheckPermission("ticket:order:query")
    @Operation(summary = "工单详情", description = "根据工单 ID 查询未删除工单详情。")
    @GetMapping("/{id}")
    public ApiResponse<TicketOrderVO> detail(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        return ApiResponse.ok(ticketOrderService.getTicket(id));
    }

    @SaCheckPermission("ticket:order:edit")
    @Operation(summary = "修改工单", description = "根据工单 ID 修改工单信息。当前状态流转未拆分专用接口，status 可在此接口更新。")
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id,
                                    @Valid @RequestBody TicketUpdateRequest request) {
        ticketOrderService.updateTicket(id, request);
        return ApiResponse.ok();
    }

    @SaCheckPermission("ticket:order:delete")
    @Operation(summary = "逻辑删除工单", description = "逻辑删除工单，删除后可在回收站查询。")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        ticketOrderService.deleteTicket(id);
        return ApiResponse.ok();
    }

    @SaCheckPermission("ticket:order:restore")
    @Operation(summary = "恢复已删除工单", description = "恢复回收站中的逻辑删除工单。")
    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restore(@Parameter(description = "工单 ID", required = true, example = "1") @PathVariable Long id) {
        ticketOrderService.restoreTicket(id);
        return ApiResponse.ok();
    }
}
