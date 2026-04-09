package com.travelnestpro.dto;

public record AuthResponse(
        Long id,
        String name,
        String email,
        String role,
        String profileImage,
        String status,
        String token
) {
}
