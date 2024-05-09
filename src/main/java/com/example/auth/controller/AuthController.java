package com.example.auth.controller;

import com.example.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public String login(@RequestHeader("Authorization") String token) {
        authService.login(token);
        return "test";
    }
}
