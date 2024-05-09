package com.example.auth.service;

import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;

    @Override
    public LoginResponse login(String token) {
        return null;
    }
}
