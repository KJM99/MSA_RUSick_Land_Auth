package com.example.auth.service;

import com.example.auth.domain.response.LoginResponse;
import com.example.auth.global.utils.TokenInfo;

public interface AuthService {
    LoginResponse login(String token);
    LoginResponse refresh(TokenInfo tokenInfo);
}
