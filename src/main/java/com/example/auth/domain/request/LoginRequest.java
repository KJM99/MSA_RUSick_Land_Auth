package com.example.auth.domain.request;

import java.time.LocalDate;

public record LoginRequest(
        String id,
        String email,
        String nickname,
        LocalDate birthDay,
        String gender
) {
}
