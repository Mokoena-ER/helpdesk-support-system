package com.helpdesk.support_system.user.controller;

import com.helpdesk.support_system.user.dto.TicketRequest;
import com.helpdesk.support_system.user.dto.TicketResponse;
import com.helpdesk.support_system.user.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/submit")
    public ResponseEntity<TicketResponse> writeTicket(@RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.writeTicket(request));
    }
}
