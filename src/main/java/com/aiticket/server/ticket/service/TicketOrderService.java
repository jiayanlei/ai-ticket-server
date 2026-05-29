package com.aiticket.server.ticket.service;

import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.ticket.dto.TicketAcceptDTO;
import com.aiticket.server.ticket.dto.TicketCommentCreateDTO;
import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketDraftCreateDTO;
import com.aiticket.server.ticket.dto.TicketConfirmDTO;
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
import com.aiticket.server.ticket.entity.TicketOrder;
import com.aiticket.server.ticket.vo.TicketAiAnalysisVO;
import com.aiticket.server.ticket.vo.TicketAttachmentVO;
import com.aiticket.server.ticket.vo.TicketCommentVO;
import com.aiticket.server.ticket.vo.TicketFlowRecordVO;
import com.aiticket.server.ticket.vo.TicketOrderDetailVO;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TicketOrderService extends IService<TicketOrder> {

    PageResult<TicketOrderVO> pageTickets(TicketQueryRequest request);

    Long createTicket(TicketCreateRequest request);

    Long createDraft(TicketDraftCreateDTO request);

    void submit(Long id, TicketSubmitDTO request);

    void accept(Long id, TicketAcceptDTO request);

    void startProcess(Long id, TicketStartProcessDTO request);

    void suspend(Long id, TicketSuspendDTO request);

    void resume(Long id, TicketResumeDTO request);

    void finish(Long id, TicketFinishDTO request);

    void confirm(Long id, TicketConfirmDTO request);

    void reopen(Long id, TicketReopenDTO request);

    void transfer(Long id, TicketTransferDTO request);

    void reject(Long id, TicketRejectDTO request);

    void close(Long id);

    void updateTicket(Long id, TicketUpdateRequest request);

    TicketOrderVO getTicket(Long id);

    TicketOrderDetailVO getTicketDetail(Long id);

    List<TicketFlowRecordVO> listFlowRecords(Long id);

    List<TicketCommentVO> listComments(Long id);

    Long addComment(Long id, TicketCommentCreateDTO request);

    List<TicketAttachmentVO> listAttachments(Long id);

    TicketAiAnalysisVO analyze(Long id);

    void deleteTicket(Long id);

    PageResult<TicketOrderVO> pageRecycleTickets(TicketQueryRequest request);

    void restoreTicket(Long id);
}
