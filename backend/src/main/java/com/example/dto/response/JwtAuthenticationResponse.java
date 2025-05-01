package com.example.dto.response;

public record JwtAuthenticationResponse(
        String accessToken,
        String tokenType,
        UserResponse user
) {}
