package com.helpdesk.support_system.user.controller;

import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.model.Roles;
import com.helpdesk.support_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @PostMapping("auth/register")
    public ResponseEntity<UserResponse> user(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request, Set.of(Roles.CUSTOMER)));
    }

    @GetMapping("users")
    public ResponseEntity<List<UserResponse>> users(){
        return ResponseEntity.ok(userService.users());
    }

}
