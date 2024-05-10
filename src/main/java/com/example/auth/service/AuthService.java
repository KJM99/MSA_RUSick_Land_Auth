package com.example.auth.service;

import com.example.auth.domain.response.LoginResponse;

public interface AuthService {
    LoginResponse login(String token);
}
