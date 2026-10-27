package com.turing.books_api.dto.user;

import com.turing.books_api.entity.Role;
import com.turing.books_api.entity.User;
import com.turing.books_api.entity.enums.RoleName;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record UserResponse(
        Long id,
        String username,
        String email,
        Set<RoleName> roles,
        Instant createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()),
                user.getCreatedAt()
        );
    }
}
