package com.helpdesk.support_system.user.service;

import com.helpdesk.support_system.user.dto.CheckTicketRequest;
import com.helpdesk.support_system.user.dto.TicketRequest;
import com.helpdesk.support_system.user.dto.TicketRequestId;
import com.helpdesk.support_system.user.dto.TicketResponse;
import com.helpdesk.support_system.user.mapper.TicketMapper;
import com.helpdesk.support_system.user.model.Roles;
import com.helpdesk.support_system.user.model.Status;
import com.helpdesk.support_system.user.model.Ticket;
import com.helpdesk.support_system.user.repo.TicketRepository;
import com.helpdesk.support_system.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper mapper;

    public TicketResponse writeTicket(TicketRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    Ticket ticket = mapper.entity(request);
                    ticket.setStatus(Set.of(Status.SUBMITTED));
                    ticket.setCreatedBy(user);
                    return mapper.response(ticketRepository.save(ticket));
                })
                .orElseThrow(()-> new RuntimeException("Only Registered Users can write tickets!"));
    }

    public List<TicketResponse> myTickets(CheckTicketRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    List<Ticket> tickets = ticketRepository.findByCreatedBy(user);
                    if (tickets.isEmpty()){
                        throw new RuntimeException("No tickets found under user: '"+user.getUsername()+"'.");
                    }
                    return tickets.stream()
                            .map(mapper::response)
                            .collect(Collectors.toList());
                })
                .orElseThrow(()-> new RuntimeException("User Not Found!"));
    }

    public TicketResponse attend(CheckTicketRequest request, TicketRequestId requestId) {
            return userRepository.findByUsername(request.getUsername())
                    .filter(user -> user.getRoles().contains(Roles.AGENT))
                    .map(user -> {
                        Ticket ticket = ticketRepository.findById(requestId.getId())
                                .orElseThrow(()-> new RuntimeException("No ticket with id: '"+requestId.getId()+"' found."));
                        if (ticket.getStatus().contains(Status.RESOLVED)){
                            throw new RuntimeException("This ticket is already attended!");
                        }
                        ticket.getStatus().remove(Status.SUBMITTED);
                        ticket.getStatus().add(Status.RESOLVED);
                        ticket.setResolvedAt(LocalDateTime.now().withNano(0));
                        return mapper.response(ticketRepository.save(ticket));
                    })
                    .orElseThrow(()-> new RuntimeException("User Not Found"));
    }

    public List<TicketResponse> tickets(){
        return ticketRepository.findAll().stream()
                .map(mapper::response)
                .collect(Collectors.toList());
    }

    public void clear(){
        ticketRepository.deleteAll();
    }

}
