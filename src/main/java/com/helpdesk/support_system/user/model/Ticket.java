package com.helpdesk.support_system.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Ticket extends SoftDeletable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;

    @ElementCollection(targetClass = Status.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "ticket_status",
            joinColumns = @JoinColumn(name = "ticket_id")
    )
    @Column(name = "status", length = (20))
    private Set<Status> status = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

}
