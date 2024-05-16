package com.example.auth.service;

import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserInfoResponse;
import com.example.auth.global.utils.TokenInfo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface AuthService {
    LoginResponse login(String token);
    LoginResponse refresh(TokenInfo tokenInfo);
    UserInfoResponse getUserIdByEmail(String email);
}
