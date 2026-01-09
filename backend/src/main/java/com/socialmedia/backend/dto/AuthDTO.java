package com.socialmedia.backend.dto;

public class AuthDTO {
    public record RegisterRequest(String username, String email, String password) {}
    public record LoginRequest(String email, String password) {}
    public record AuthResponse(Long userId, String username) {}
}
