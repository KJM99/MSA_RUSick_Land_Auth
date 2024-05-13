package com.example.auth.domain.response;

public record LoginResponse(
        String token,
        String tokenType
) {
    public static LoginResponse from(String token) {
        return new LoginResponse(token, "Bearer");
    }
}
