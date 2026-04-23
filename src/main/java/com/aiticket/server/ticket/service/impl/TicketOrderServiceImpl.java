package com.aiticket.server.ticket.service.impl;

import com.aiticket.server.common.constant.CommonConstants;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.ticket.convert.TicketOrderConvert;
import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketQueryRequest;
import com.aiticket.server.ticket.dto.TicketUpdateRequest;
import com.aiticket.server.ticket.entity.TicketOrder;
import com.aiticket.server.ticket.mapper.TicketOrderMapper;
import com.aiticket.server.ticket.service.TicketOrderService;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TicketOrderServiceImpl extends ServiceImpl<TicketOrderMapper, TicketOrder> implements TicketOrderService {

    private static final DateTimeFormatter TICKET_NO_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final TicketOrderConvert ticketConvert;

    @Override
    public PageResult<TicketOrderVO> pageTickets(TicketQueryRequest request) {
        Page<TicketOrder> page = page(new Page<>(request.getPageNum(), request.getPageSize()),
                Wrappers.<TicketOrder>lambdaQuery()
                        .and(StringUtils.hasText(request.getKeyword()), wrapper -> wrapper
                                .like(TicketOrder::getTitle, request.getKeyword())
                                .or()
                                .like(TicketOrder::getTicketNo, request.getKeyword()))
                        .eq(StringUtils.hasText(request.getStatus()), TicketOrder::getStatus, request.getStatus())
                        .eq(StringUtils.hasText(request.getPriority()), TicketOrder::getPriority, request.getPriority())
                        .eq(StringUtils.hasText(request.getCategory()), TicketOrder::getCategory, request.getCategory())
                        .eq(request.getAssigneeId() != null, TicketOrder::getAssigneeId, request.getAssigneeId())
                        .eq(request.getApplicantId() != null, TicketOrder::getApplicantId, request.getApplicantId())
                        .orderByDesc(TicketOrder::getCreateTime));
        List<TicketOrderVO> records = page.getRecords().stream().map(ticketConvert::toVO).toList();
        return PageResult.of(page, records);
    }

    @Override
    public Long createTicket(TicketCreateRequest request) {
        TicketOrder ticket = ticketConvert.toEntity(request);
        ticket.setTicketNo(generateTicketNo());
        if (!StringUtils.hasText(ticket.getStatus())) {
            ticket.setStatus(CommonConstants.TICKET_STATUS_NEW);
        }
        if (!StringUtils.hasText(ticket.getPriority())) {
            ticket.setPriority(CommonConstants.TICKET_PRIORITY_NORMAL);
        }
        save(ticket);
        return ticket.getId();
    }

    @Override
    public void updateTicket(Long id, TicketUpdateRequest request) {
        TicketOrder ticket = getById(id);
        if (ticket == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        ticketConvert.updateEntity(request, ticket);
        updateById(ticket);
    }

    @Override
    public TicketOrderVO getTicket(Long id) {
        TicketOrder ticket = getById(id);
        if (ticket == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return ticketConvert.toVO(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        if (!removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public PageResult<TicketOrderVO> pageRecycleTickets(TicketQueryRequest request) {
        IPage<TicketOrder> page = baseMapper.selectRecyclePage(new Page<>(request.getPageNum(), request.getPageSize()), request);
        List<TicketOrderVO> records = page.getRecords().stream().map(ticketConvert::toVO).toList();
        return PageResult.of(page, records);
    }

    @Override
    public void restoreTicket(Long id) {
        int updated = baseMapper.restoreById(id);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }

    private String generateTicketNo() {
        int random = ThreadLocalRandom.current().nextInt(1000, 10000);
        return "TK" + LocalDateTime.now().format(TICKET_NO_FORMATTER) + random;
    }
}
