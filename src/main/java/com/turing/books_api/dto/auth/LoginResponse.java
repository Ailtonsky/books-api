package com.turing.books_api.dto.auth;

import com.turing.books_api.dto.user.UserResponse;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserResponse user
) {
}