package com.travelnestpro.dto;

public record AuthResponse(
        Long id,
        String name,
        String email,
        String role,
        String profileImage,
        String status,
        Boolean isVerified,
        Boolean isApproved,
        Boolean onboardingCompleted,
        Boolean isNew,
        String token
) {
}
