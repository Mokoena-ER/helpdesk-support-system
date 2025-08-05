package com.helpdesk.support_system.user.dto;

import com.helpdesk.support_system.user.model.Status;
import com.helpdesk.support_system.user.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TicketResponse {

    private Long id;
    private String subject;
    private String description;
    private LocalDateTime createdAt;
    private Set<Status> status;
    private User createdBy;

}
