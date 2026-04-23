package com.aiticket.server.ticket.service;

import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketQueryRequest;
import com.aiticket.server.ticket.dto.TicketUpdateRequest;
import com.aiticket.server.ticket.entity.TicketOrder;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TicketOrderService extends IService<TicketOrder> {

    PageResult<TicketOrderVO> pageTickets(TicketQueryRequest request);

    Long createTicket(TicketCreateRequest request);

    void updateTicket(Long id, TicketUpdateRequest request);

    TicketOrderVO getTicket(Long id);

    void deleteTicket(Long id);

    PageResult<TicketOrderVO> pageRecycleTickets(TicketQueryRequest request);

    void restoreTicket(Long id);
}
