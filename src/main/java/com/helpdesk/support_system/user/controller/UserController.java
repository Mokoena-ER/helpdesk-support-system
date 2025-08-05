package com.helpdesk.support_system.user.controller;

import com.helpdesk.support_system.user.dto.UserPromoted;
import com.helpdesk.support_system.user.dto.UserPromotion;
import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.model.Roles;
import com.helpdesk.support_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @PostMapping("auth/admin/register")
    public ResponseEntity<UserResponse> admin(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request, Set.of(Roles.ADMIN, Roles.AGENT)));
    }

    @PostMapping("auth/register")
    public ResponseEntity<UserResponse> user(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request, Set.of(Roles.CUSTOMER)));
    }

    @PutMapping("admin/promote")
    public ResponseEntity<UserPromoted> promoteUser(@RequestBody UserPromotion username) {
        return ResponseEntity.ok(userService.promote(username));
    }

}
