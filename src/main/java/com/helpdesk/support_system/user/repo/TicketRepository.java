package com.helpdesk.support_system.user.repo;

import com.helpdesk.support_system.user.model.Ticket;
import com.helpdesk.support_system.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreatedBy(User username);
}
