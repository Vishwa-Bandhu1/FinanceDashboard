package com.finance.dashboard.controller;

import com.finance.dashboard.dto.UserResponse;
import com.finance.dashboard.model.Role;
import com.finance.dashboard.model.UserStatus;
import com.finance.dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/role")
    public UserResponse updateRole(@PathVariable String id, @RequestParam Role role) {
        return userService.updateRole(id, role);
    }

    @PutMapping("/{id}/status")
    public UserResponse updateStatus(@PathVariable String id, @RequestParam UserStatus status) {
        return userService.updateStatus(id, status);
    }
}
