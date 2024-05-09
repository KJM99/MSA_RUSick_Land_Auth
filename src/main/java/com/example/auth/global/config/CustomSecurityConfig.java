package com.example.auth.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(s -> s.disable());
        http.authorizeHttpRequests(req ->
                req.requestMatchers("/api/v1/auth/refresh")
                        .authenticated()
                    .requestMatchers("/api/v1/auth/login")
                        .permitAll()
        );
        return http.build();
    }
}
