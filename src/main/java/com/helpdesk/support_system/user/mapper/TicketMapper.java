package com.helpdesk.support_system.user.mapper;

import com.helpdesk.support_system.user.dto.TicketRequest;
import com.helpdesk.support_system.user.dto.TicketResponse;
import com.helpdesk.support_system.user.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket entity(TicketRequest request);
    TicketResponse response(Ticket entity);
}
