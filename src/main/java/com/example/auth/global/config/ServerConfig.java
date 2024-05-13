package com.example.auth.global.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ServerConfig {
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expiration}")
    private Long expiration;

}
