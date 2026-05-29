package com.aiticket.server.ticket.convert;

import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketUpdateRequest;
import com.aiticket.server.ticket.entity.TicketOrder;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketOrderConvert {

    @Mapping(target = "handlerId", expression = "java(request.getHandlerId() != null ? request.getHandlerId() : request.getAssigneeId())")
    @Mapping(target = "handlerName", expression = "java(request.getHandlerName() != null ? request.getHandlerName() : request.getAssigneeName())")
    @Mapping(target = "expectedFinishTime", expression = "java(request.getExpectedFinishTime() != null ? request.getExpectedFinishTime() : request.getDueTime())")
    TicketOrder toEntity(TicketCreateRequest request);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "handlerId", expression = "java(request.getHandlerId() != null ? request.getHandlerId() : request.getAssigneeId())")
    @Mapping(target = "handlerName", expression = "java(request.getHandlerName() != null ? request.getHandlerName() : request.getAssigneeName())")
    @Mapping(target = "expectedFinishTime", expression = "java(request.getExpectedFinishTime() != null ? request.getExpectedFinishTime() : request.getDueTime())")
    void updateEntity(TicketUpdateRequest request, @MappingTarget TicketOrder entity);

    @Mapping(target = "assigneeId", source = "handlerId")
    @Mapping(target = "assigneeName", source = "handlerName")
    @Mapping(target = "dueTime", source = "expectedFinishTime")
    TicketOrderVO toVO(TicketOrder entity);
}
