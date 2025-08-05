package com.helpdesk.support_system.user.service;

import com.helpdesk.support_system.user.dto.TicketRequest;
import com.helpdesk.support_system.user.dto.TicketResponse;
import com.helpdesk.support_system.user.mapper.TicketMapper;
import com.helpdesk.support_system.user.model.Status;
import com.helpdesk.support_system.user.model.Ticket;
import com.helpdesk.support_system.user.model.User;
import com.helpdesk.support_system.user.repo.TicketRepository;
import com.helpdesk.support_system.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper mapper;

    public TicketResponse writeTicket(TicketRequest request) {

        User author = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new RuntimeException("Only Registered Users can write tickets!"));

        Ticket ticket = mapper.entity(request);
        ticket.setStatus(Set.of(Status.SUBMITTED));
        ticket.setCreatedBy(author);
        Ticket saveTicket = ticketRepository.save(ticket);

        return mapper.response(saveTicket);
    }

    public TicketResponse write(TicketRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    Ticket ticket = mapper.entity(request);
                    ticket.setStatus(Set.of(Status.SUBMITTED));
                    return mapper.response(ticketRepository.save(ticket));
                })
                .orElseThrow(()-> new RuntimeException("Only Register Users can write tickets!"));
    }

}
