package com.example.dto.request;

public record UpdateUserRequest(
        String username,
        String email,
        String password,
        String avatarUrl,
        String bio
) {}
