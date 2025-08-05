package com.helpdesk.support_system.user.dto;

import com.helpdesk.support_system.user.model.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class UserPromoted {

    private String username;
    private Set<Roles> roles;

}
