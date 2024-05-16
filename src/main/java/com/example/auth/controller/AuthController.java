package com.example.auth.controller;

import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserInfoResponse;
import com.example.auth.global.utils.TokenInfo;
import com.example.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public LoginResponse login(@RequestHeader("Authorization") String token) {
        return authService.login(token);
    }
    @GetMapping("refresh")
    public LoginResponse refresh(@AuthenticationPrincipal TokenInfo tokenInfo) {
        return authService.refresh(tokenInfo);
    }
    @PostMapping()
    public UserInfoResponse getUserIdByEmail(@RequestBody String email){
        return authService.getUserIdByEmail(email);
    }
}
