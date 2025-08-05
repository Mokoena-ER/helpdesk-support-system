package com.helpdesk.support_system.user.dto;

import lombok.Data;

@Data
public class TicketRequest {

    private String username;
    private String subject;
    private String description;

}
