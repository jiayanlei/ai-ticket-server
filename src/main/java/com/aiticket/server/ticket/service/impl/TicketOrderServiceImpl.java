package com.aiticket.server.ticket.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.aiticket.server.common.exception.BusinessException;
import com.aiticket.server.common.exception.ErrorCode;
import com.aiticket.server.common.page.PageResult;
import com.aiticket.server.ticket.convert.TicketOrderConvert;
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
import com.aiticket.server.ticket.entity.TicketAttachment;
import com.aiticket.server.ticket.entity.TicketComment;
import com.aiticket.server.ticket.entity.TicketFlowRecord;
import com.aiticket.server.ticket.entity.TicketOrder;
import com.aiticket.server.ticket.enums.TicketActionEnum;
import com.aiticket.server.ticket.enums.TicketPriorityEnum;
import com.aiticket.server.ticket.enums.TicketStatusEnum;
import com.aiticket.server.ticket.mapper.TicketAttachmentMapper;
import com.aiticket.server.ticket.mapper.TicketCommentMapper;
import com.aiticket.server.ticket.mapper.TicketFlowRecordMapper;
import com.aiticket.server.ticket.mapper.TicketOrderMapper;
import com.aiticket.server.ticket.service.TicketOrderService;
import com.aiticket.server.ticket.vo.TicketAiAnalysisVO;
import com.aiticket.server.ticket.vo.TicketAttachmentVO;
import com.aiticket.server.ticket.vo.TicketCommentVO;
import com.aiticket.server.ticket.vo.TicketFlowRecordVO;
import com.aiticket.server.ticket.vo.TicketOrderDetailVO;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TicketOrderServiceImpl extends ServiceImpl<TicketOrderMapper, TicketOrder> implements TicketOrderService {

    private static final DateTimeFormatter TICKET_NO_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final String STATUS_ACTION_ERROR = "当前状态不允许执行该操作";

    private final TicketOrderConvert ticketConvert;
    private final TicketFlowRecordMapper flowRecordMapper;
    private final TicketCommentMapper commentMapper;
    private final TicketAttachmentMapper attachmentMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResult<TicketOrderVO> pageTickets(TicketQueryRequest request) {
        Long handlerId = request.getHandlerId() != null ? request.getHandlerId() : request.getAssigneeId();
        String status = StringUtils.hasText(request.getStatus()) ? TicketStatusEnum.of(request.getStatus()).getCode() : null;
        Page<TicketOrder> page = page(new Page<>(request.getPageNum(), request.getPageSize()),
                Wrappers.<TicketOrder>lambdaQuery()
                        .and(StringUtils.hasText(request.getKeyword()), wrapper -> wrapper
                                .like(TicketOrder::getTitle, request.getKeyword())
                                .or()
                                .like(TicketOrder::getTicketNo, request.getKeyword()))
                        .eq(status != null, TicketOrder::getStatus, status)
                        .eq(StringUtils.hasText(request.getPriority()), TicketOrder::getPriority, request.getPriority())
                        .eq(StringUtils.hasText(request.getCategory()), TicketOrder::getCategory, request.getCategory())
                        .eq(handlerId != null, TicketOrder::getHandlerId, handlerId)
                        .eq(request.getApplicantId() != null, TicketOrder::getApplicantId, request.getApplicantId())
                        .orderByDesc(TicketOrder::getCreateTime));
        List<TicketOrderVO> records = page.getRecords().stream().map(ticketConvert::toVO).toList();
        return PageResult.of(page, records);
    }

    @Override
    @Transactional
    public Long createTicket(TicketCreateRequest request) {
        TicketDraftCreateDTO draft = new TicketDraftCreateDTO();
        draft.setTitle(request.getTitle());
        draft.setDescription(request.getDescription());
        draft.setPriority(request.getPriority());
        draft.setSource(request.getSource());
        draft.setCategory(request.getCategory());
        draft.setApplicantId(request.getApplicantId());
        draft.setApplicantName(request.getApplicantName());
        draft.setHandlerId(request.getHandlerId() != null ? request.getHandlerId() : request.getAssigneeId());
        draft.setHandlerName(request.getHandlerName() != null ? request.getHandlerName() : request.getAssigneeName());
        draft.setExpectedFinishTime(request.getExpectedFinishTime() != null ? request.getExpectedFinishTime() : request.getDueTime());
        return createDraft(draft);
    }

    @Override
    @Transactional
    public Long createDraft(TicketDraftCreateDTO request) {
        TicketOrder ticket = new TicketOrder();
        ticket.setTicketNo(generateTicketNo());
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(TicketStatusEnum.DRAFT.getCode());
        ticket.setPriority(normalizePriority(request.getPriority()).getCode());
        ticket.setSource(request.getSource());
        ticket.setCategory(request.getCategory());
        ticket.setApplicantId(request.getApplicantId());
        ticket.setApplicantName(request.getApplicantName());
        ticket.setHandlerId(request.getHandlerId());
        ticket.setHandlerName(request.getHandlerName());
        ticket.setExpectedFinishTime(request.getExpectedFinishTime());
        ticket.setIsTimeout(false);
        save(ticket);
        recordFlow(ticket.getId(), null, ticket.getStatus(), TicketActionEnum.CREATE_DRAFT, null);
        return ticket.getId();
    }

    @Override
    @Transactional
    public void submit(Long id, TicketSubmitDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.DRAFT);
        String beforeStatus = ticket.getStatus();
        LocalDateTime now = LocalDateTime.now();
        ticket.setStatus(TicketStatusEnum.PENDING_ACCEPT.getCode());
        ticket.setSubmitTime(now);
        ticket.setSlaDeadline(now.plusHours(normalizePriority(ticket.getPriority()).getSlaHours()));
        applyMockAiAnalysis(ticket);
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.SUBMIT, request == null ? null : request.getRemark());
    }

    @Override
    @Transactional
    public void accept(Long id, TicketAcceptDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.PENDING_ACCEPT);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.ACCEPTED.getCode());
        ticket.setAcceptTime(LocalDateTime.now());
        ticket.setHandlerId(request.getHandlerId());
        ticket.setHandlerName(request.getHandlerName());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.ACCEPT, request.getRemark());
    }

    @Override
    @Transactional
    public void startProcess(Long id, TicketStartProcessDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.ACCEPTED);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.PROCESSING.getCode());
        ticket.setStartProcessTime(LocalDateTime.now());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.START_PROCESS, request == null ? null : request.getRemark());
    }

    @Override
    @Transactional
    public void suspend(Long id, TicketSuspendDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.PROCESSING);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.PENDING.getCode());
        ticket.setSuspendTime(LocalDateTime.now());
        ticket.setSuspendReason(request.getSuspendReason());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.SUSPEND, request.getSuspendReason());
    }

    @Override
    @Transactional
    public void resume(Long id, TicketResumeDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.PENDING);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.PROCESSING.getCode());
        ticket.setResumeTime(LocalDateTime.now());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.RESUME, request == null ? null : request.getRemark());
    }

    @Override
    @Transactional
    public void finish(Long id, TicketFinishDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.PROCESSING);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.WAIT_CONFIRM.getCode());
        ticket.setFinishTime(LocalDateTime.now());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.FINISH, request == null ? null : request.getRemark());
    }

    @Override
    @Transactional
    public void confirm(Long id, TicketConfirmDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.WAIT_CONFIRM);
        String beforeStatus = ticket.getStatus();
        LocalDateTime now = LocalDateTime.now();
        ticket.setStatus(TicketStatusEnum.COMPLETED.getCode());
        ticket.setCloseTime(now);
        ticket.setIsTimeout(isTimeout(ticket, now));
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.CONFIRM, request == null ? null : request.getRemark());
    }

    @Override
    @Transactional
    public void reopen(Long id, TicketReopenDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.WAIT_CONFIRM, TicketStatusEnum.COMPLETED);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.PROCESSING.getCode());
        ticket.setCloseTime(null);
        ticket.setReopenReason(request.getReopenReason());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.REOPEN, request.getReopenReason());
    }

    @Override
    @Transactional
    public void transfer(Long id, TicketTransferDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.PENDING_ACCEPT, TicketStatusEnum.ACCEPTED, TicketStatusEnum.PROCESSING);
        String beforeStatus = ticket.getStatus();
        ticket.setHandlerId(request.getHandlerId());
        ticket.setHandlerName(request.getHandlerName());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.TRANSFER, request.getRemark());
    }

    @Override
    @Transactional
    public void reject(Long id, TicketRejectDTO request) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.PENDING_ACCEPT);
        String beforeStatus = ticket.getStatus();
        ticket.setStatus(TicketStatusEnum.REJECTED.getCode());
        ticket.setRejectReason(request.getRejectReason());
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.REJECT, request.getRejectReason());
    }

    @Override
    @Transactional
    public void close(Long id) {
        TicketOrder ticket = requireTicket(id);
        assertStatus(ticket, TicketStatusEnum.COMPLETED);
        String beforeStatus = ticket.getStatus();
        LocalDateTime now = LocalDateTime.now();
        ticket.setStatus(TicketStatusEnum.CLOSED.getCode());
        ticket.setCloseTime(now);
        ticket.setIsTimeout(isTimeout(ticket, now));
        updateById(ticket);
        recordFlow(ticket.getId(), beforeStatus, ticket.getStatus(), TicketActionEnum.CLOSE, null);
    }

    @Override
    @Transactional
    public void updateTicket(Long id, TicketUpdateRequest request) {
        TicketOrder ticket = requireTicket(id);
        ticketConvert.updateEntity(request, ticket);
        if (request.getStatus() != null && !request.getStatus().equals(ticket.getStatus())) {
            throw new BusinessException("状态流转请使用专用接口");
        }
        if (StringUtils.hasText(ticket.getPriority())) {
            ticket.setPriority(normalizePriority(ticket.getPriority()).getCode());
        }
        updateById(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketOrderVO getTicket(Long id) {
        return ticketConvert.toVO(requireTicket(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TicketOrderDetailVO getTicketDetail(Long id) {
        TicketOrderDetailVO detail = new TicketOrderDetailVO();
        TicketOrder ticket = requireTicket(id);
        detail.setTicket(ticketConvert.toVO(ticket));
        detail.setFlowRecords(listFlowRecords(id));
        detail.setComments(listComments(id));
        detail.setAttachments(listAttachments(id));
        detail.setAiAnalysis(toAiAnalysisVO(ticket));
        return detail;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketFlowRecordVO> listFlowRecords(Long id) {
        requireTicket(id);
        return flowRecordMapper.selectList(Wrappers.<TicketFlowRecord>lambdaQuery()
                        .eq(TicketFlowRecord::getTicketId, id)
                        .orderByAsc(TicketFlowRecord::getCreateTime)
                        .orderByAsc(TicketFlowRecord::getId))
                .stream()
                .map(this::toFlowRecordVO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketCommentVO> listComments(Long id) {
        requireTicket(id);
        return commentMapper.selectList(Wrappers.<TicketComment>lambdaQuery()
                        .eq(TicketComment::getTicketId, id)
                        .orderByAsc(TicketComment::getCreateTime)
                        .orderByAsc(TicketComment::getId))
                .stream()
                .map(this::toCommentVO)
                .toList();
    }

    @Override
    @Transactional
    public Long addComment(Long id, TicketCommentCreateDTO request) {
        requireTicket(id);
        TicketComment comment = new TicketComment();
        comment.setTicketId(id);
        comment.setUserId(request.getUserId() != null ? request.getUserId() : currentOperatorId());
        comment.setUserName(StringUtils.hasText(request.getUserName()) ? request.getUserName() : currentOperatorName());
        comment.setContent(request.getContent());
        commentMapper.insert(comment);
        return comment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketAttachmentVO> listAttachments(Long id) {
        requireTicket(id);
        return attachmentMapper.selectList(Wrappers.<TicketAttachment>lambdaQuery()
                        .eq(TicketAttachment::getTicketId, id)
                        .orderByAsc(TicketAttachment::getCreateTime)
                        .orderByAsc(TicketAttachment::getId))
                .stream()
                .map(this::toAttachmentVO)
                .toList();
    }

    @Override
    @Transactional
    public TicketAiAnalysisVO analyze(Long id) {
        TicketOrder ticket = requireTicket(id);
        applyMockAiAnalysis(ticket);
        updateById(ticket);
        return toAiAnalysisVO(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<TicketOrderVO> pageRecycleTickets(TicketQueryRequest request) {
        IPage<TicketOrder> page = baseMapper.selectRecyclePage(new Page<>(request.getPageNum(), request.getPageSize()), request);
        List<TicketOrderVO> records = page.getRecords().stream().map(ticketConvert::toVO).toList();
        return PageResult.of(page, records);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        if (!removeById(id)) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void restoreTicket(Long id) {
        int updated = baseMapper.restoreById(id);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
    }

    private TicketOrder requireTicket(Long id) {
        TicketOrder ticket = getById(id);
        if (ticket == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND);
        }
        return ticket;
    }

    private void assertStatus(TicketOrder ticket, TicketStatusEnum... allowedStatuses) {
        TicketStatusEnum currentStatus = TicketStatusEnum.of(ticket.getStatus());
        Set<TicketStatusEnum> allowed = Set.copyOf(Arrays.asList(allowedStatuses));
        if (!allowed.contains(currentStatus)) {
            throw new BusinessException(STATUS_ACTION_ERROR);
        }
    }

    private void recordFlow(Long ticketId, String beforeStatus, String afterStatus, TicketActionEnum action, String remark) {
        TicketFlowRecord record = new TicketFlowRecord();
        record.setTicketId(ticketId);
        record.setOperatorId(currentOperatorId());
        record.setOperatorName(currentOperatorName());
        record.setAction(action.getCode());
        record.setBeforeStatus(beforeStatus);
        record.setAfterStatus(afterStatus);
        record.setRemark(StringUtils.hasText(remark) ? remark : action.getDescription());
        flowRecordMapper.insert(record);
    }

    private TicketPriorityEnum normalizePriority(String priority) {
        if (!StringUtils.hasText(priority)) {
            return TicketPriorityEnum.NORMAL;
        }
        for (TicketPriorityEnum priorityEnum : TicketPriorityEnum.values()) {
            if (priorityEnum.getCode().equals(priority)) {
                return priorityEnum;
            }
        }
        throw new BusinessException("工单优先级不存在");
    }

    private boolean isTimeout(TicketOrder ticket, LocalDateTime finishMoment) {
        return ticket.getSlaDeadline() != null && finishMoment.isAfter(ticket.getSlaDeadline());
    }

    private void applyMockAiAnalysis(TicketOrder ticket) {
        String text = ((ticket.getTitle() == null ? "" : ticket.getTitle()) + " "
                + (ticket.getDescription() == null ? "" : ticket.getDescription())).toLowerCase();
        TicketPriorityEnum priority = normalizePriority(ticket.getPriority());
        String category = StringUtils.hasText(ticket.getCategory()) ? ticket.getCategory() : inferCategory(text);

        ticket.setAiCategory(category);
        ticket.setAiRiskLevel(inferRiskLevel(text, priority));
        ticket.setAiRecommendDept(inferRecommendDept(category));
        ticket.setAiRecommendHandler(StringUtils.hasText(ticket.getHandlerName()) ? ticket.getHandlerName() : "值班工程师");
        ticket.setAiEstimatedTime(switch (priority) {
            case URGENT -> "4小时内";
            case HIGH -> "8小时内";
            case NORMAL -> "1个工作日";
            case LOW -> "3个工作日";
        });
        ticket.setAiSummary("Mock AI 分析：" + ticket.getTitle());
        ticket.setAiSuggestion(buildSuggestion(text, priority));
    }

    private String inferCategory(String text) {
        if (text.contains("login") || text.contains("password") || text.contains("登录") || text.contains("账号") || text.contains("权限")) {
            return "账号与权限";
        }
        if (text.contains("network") || text.contains("网络") || text.contains("连接")) {
            return "网络连接";
        }
        if (text.contains("payment") || text.contains("支付") || text.contains("扣费") || text.contains("订单")) {
            return "交易与订单";
        }
        return "综合问题";
    }

    private String inferRiskLevel(String text, TicketPriorityEnum priority) {
        if (priority == TicketPriorityEnum.URGENT
                || text.contains("宕机")
                || text.contains("不可用")
                || text.contains("大面积")) {
            return "HIGH";
        }
        if (priority == TicketPriorityEnum.HIGH
                || text.contains("无法")
                || text.contains("失败")
                || text.contains("异常")) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private String inferRecommendDept(String category) {
        return switch (category) {
            case "账号与权限", "网络连接" -> "信息技术部";
            case "交易与订单" -> "业务运营部";
            default -> "客户服务部";
        };
    }

    private String buildSuggestion(String text, TicketPriorityEnum priority) {
        if (priority == TicketPriorityEnum.URGENT) {
            return "建议立即升级到值班负责人，先恢复核心服务，再补充根因分析。";
        }
        if (text.contains("登录") || text.contains("账号") || text.contains("password")) {
            return "建议先核验账号状态、密码策略、权限配置和近期登录日志。";
        }
        return "建议补充影响范围、复现步骤和关键截图后按 SLA 推进处理。";
    }

    private TicketAiAnalysisVO toAiAnalysisVO(TicketOrder ticket) {
        TicketAiAnalysisVO vo = new TicketAiAnalysisVO();
        vo.setAiCategory(ticket.getAiCategory());
        vo.setAiRiskLevel(ticket.getAiRiskLevel());
        vo.setAiRecommendDept(ticket.getAiRecommendDept());
        vo.setAiRecommendHandler(ticket.getAiRecommendHandler());
        vo.setAiEstimatedTime(ticket.getAiEstimatedTime());
        vo.setAiSummary(ticket.getAiSummary());
        vo.setAiSuggestion(ticket.getAiSuggestion());
        return vo;
    }

    private TicketFlowRecordVO toFlowRecordVO(TicketFlowRecord record) {
        TicketFlowRecordVO vo = new TicketFlowRecordVO();
        vo.setId(record.getId());
        vo.setTicketId(record.getTicketId());
        vo.setOperatorId(record.getOperatorId());
        vo.setOperatorName(record.getOperatorName());
        vo.setAction(record.getAction());
        vo.setActionName(resolveActionName(record.getAction()));
        vo.setBeforeStatus(record.getBeforeStatus());
        vo.setAfterStatus(record.getAfterStatus());
        vo.setRemark(record.getRemark());
        vo.setCreateTime(record.getCreateTime());
        return vo;
    }

    private TicketCommentVO toCommentVO(TicketComment comment) {
        TicketCommentVO vo = new TicketCommentVO();
        vo.setId(comment.getId());
        vo.setTicketId(comment.getTicketId());
        vo.setUserId(comment.getUserId());
        vo.setUserName(comment.getUserName());
        vo.setContent(comment.getContent());
        vo.setCreateTime(comment.getCreateTime());
        return vo;
    }

    private TicketAttachmentVO toAttachmentVO(TicketAttachment attachment) {
        TicketAttachmentVO vo = new TicketAttachmentVO();
        vo.setId(attachment.getId());
        vo.setTicketId(attachment.getTicketId());
        vo.setFileName(attachment.getFileName());
        vo.setFileUrl(attachment.getFileUrl());
        vo.setFileSize(attachment.getFileSize());
        vo.setFileType(attachment.getFileType());
        vo.setUploadUserId(attachment.getUploadUserId());
        vo.setUploadUserName(attachment.getUploadUserName());
        vo.setCreateTime(attachment.getCreateTime());
        return vo;
    }

    private String resolveActionName(String action) {
        for (TicketActionEnum actionEnum : TicketActionEnum.values()) {
            if (actionEnum.getCode().equals(action)) {
                return actionEnum.getDescription();
            }
        }
        return action;
    }

    private Long currentOperatorId() {
        try {
            return StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null;
        } catch (Exception ex) {
            return null;
        }
    }

    private String currentOperatorName() {
        try {
            if (!StpUtil.isLogin()) {
                return null;
            }
            Object nickname = StpUtil.getSession().get("nickname");
            if (nickname != null && StringUtils.hasText(String.valueOf(nickname))) {
                return String.valueOf(nickname);
            }
            Object username = StpUtil.getSession().get("username");
            return username == null ? null : String.valueOf(username);
        } catch (Exception ex) {
            return null;
        }
    }

    private String generateTicketNo() {
        int random = ThreadLocalRandom.current().nextInt(1000, 10000);
        return "TK" + LocalDateTime.now().format(TICKET_NO_FORMATTER) + random;
    }
}
