package com.example.auth.global.utils;

import com.example.auth.domain.entity.User;
import com.example.auth.global.config.ServerConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final Long expiration;
    private final SecretKey secretKey;

    public String createToken(User user) {
        String token = Jwts.builder()
                .claim("id", user.getId())
                .claim("nickname", user.getNickname())
                .claim("birthDay", user.getBirthDay().toString())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
        return token;
    }
    public TokenInfo parseToken(String token) {
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token)
                .getPayload();
        return TokenInfo.fromClaims(payload);
    }
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }



    @Autowired
    public JwtUtil(ServerConfig serverConfig) {
        this.expiration = serverConfig.getExpiration();
        this.secretKey = Keys.hmacShaKeyFor(serverConfig.getSecret().getBytes());
    }
}
