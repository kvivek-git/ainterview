package com.ainterview.controller;

import com.ainterview.dto.CreateUserRequest;
import com.ainterview.dto.UserResponse;
import com.ainterview.dto.stats.UserStatsResponse;
import com.ainterview.service.UserService;
import com.ainterview.service.UserStatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserStatsService userStatsService;

    @PostMapping
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request){
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping("/me/stats")
    public UserStatsResponse getMyStats(Authentication authentication) {
        return userStatsService.getStatsForUser(authentication.getName());
    }
}
