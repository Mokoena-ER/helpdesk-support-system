package com.helpdesk.support_system.user.controller;

import com.helpdesk.support_system.user.dto.UserPromoRequest;
import com.helpdesk.support_system.user.dto.UserPromoResponse;
import com.helpdesk.support_system.user.dto.UserRequest;
import com.helpdesk.support_system.user.dto.UserResponse;
import com.helpdesk.support_system.user.model.Roles;
import com.helpdesk.support_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("api/")
public class AdminController {

    private final UserService userService;

    @PostMapping("admin/auth/register")
    public ResponseEntity<UserResponse> admin(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.register(request, Set.of(Roles.ADMIN, Roles.AGENT)));
    }

    @PostMapping("agent/login")
    public String login(UserRequest request) {
        return userService.verify(request);
    }

    @GetMapping("agent/users")
    public ResponseEntity<List<UserResponse>> users(){
        return ResponseEntity.ok(userService.users());
    }

    @PutMapping("admin/promote")
    public ResponseEntity<UserPromoResponse> promoteCustomer(@RequestBody UserPromoRequest username) {
        return ResponseEntity.ok(userService.promote(username));
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("admin/re-activate/{id}")
    public ResponseEntity<Void> reactivateUser(Long id) {
        userService.reActivateUser(id);
        return ResponseEntity.accepted().build();
    }

}
