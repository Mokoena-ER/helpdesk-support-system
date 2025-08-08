package com.helpdesk.support_system.user.controller;

import com.helpdesk.support_system.user.dto.CheckTicketRequest;
import com.helpdesk.support_system.user.dto.TicketRequest;
import com.helpdesk.support_system.user.dto.TicketRequestId;
import com.helpdesk.support_system.user.dto.TicketResponse;
import com.helpdesk.support_system.user.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/submit")
    public ResponseEntity<TicketResponse> writeTicket(@RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.writeTicket(request));
    }

    @GetMapping("/check")
    public List<TicketResponse> checkTicket(@RequestBody CheckTicketRequest request) {
        return ticketService.myTickets(request);
    }

    @GetMapping()
    public ResponseEntity<List<TicketResponse>> viewAllTickets(){
        return ResponseEntity.ok(ticketService.tickets());
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<TicketResponse>> unresolvedTicket() {
        return ResponseEntity.ok(ticketService.unresolvedTickets());
    }

    @PutMapping("/attend/{id}")
    public TicketResponse attendTicket(@RequestBody CheckTicketRequest request, @RequestParam TicketRequestId id){
        return ticketService.attend(request, id);
    }

    @DeleteMapping("/delete")
    public void delete(){
        ticketService.clear();
    }

}
