package com.example.auth.domain.response;

import com.example.auth.domain.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        String id,
        String email,
        String nickname,
        LocalDate birthDay,
        String gender) {
    public User toEntity(){
        return User.builder()
                .id(UUID.fromString(id))
                .email(email)
                .nickname(nickname)
                .birthDay(birthDay)
                .gender(gender)
                .build();
    }
}
