package com.aiticket.server.ticket.convert;

import com.aiticket.server.ticket.dto.TicketCreateRequest;
import com.aiticket.server.ticket.dto.TicketUpdateRequest;
import com.aiticket.server.ticket.entity.TicketOrder;
import com.aiticket.server.ticket.vo.TicketOrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketOrderConvert {

    TicketOrder toEntity(TicketCreateRequest request);

    void updateEntity(TicketUpdateRequest request, @MappingTarget TicketOrder entity);

    TicketOrderVO toVO(TicketOrder entity);
}
