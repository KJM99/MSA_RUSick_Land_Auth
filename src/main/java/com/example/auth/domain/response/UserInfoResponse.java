package com.example.auth.domain.response;

import java.util.UUID;

public record UserInfoResponse(
        UUID userId,
        String userName
) {
}
