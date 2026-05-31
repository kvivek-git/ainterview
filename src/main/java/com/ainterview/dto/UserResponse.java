package com.ainterview.dto;

import com.ainterview.model.Role;
import com.ainterview.model.User;

import java.time.LocalDateTime;
import java.util.Date;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        LocalDateTime date
) {
    public static UserResponse from(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getUpdatedAt()
        );
    }
}
