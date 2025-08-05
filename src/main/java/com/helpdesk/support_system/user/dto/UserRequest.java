package com.helpdesk.support_system.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRequest {

    private String username;
    private String password;

}
