package com.helpdesk.support_system.user.dto;

import com.helpdesk.support_system.user.model.Roles;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class UserResponse {

    private String username;
    private String email;
    private LocalDateTime createdAt;
    private Set<Roles> role;

}
