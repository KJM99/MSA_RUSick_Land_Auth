package com.example.auth.service;

import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RestTemplate restTemplate;
    private UserRepository userRepository;

    @Override
    public LoginResponse login(String token) {
        UserResponse response = postRequestParseToken(token);
        System.out.println(response.id());
        System.out.println(response.email());
        System.out.println(response.birthDay());
        System.out.println(response.gender());
        System.out.println(response.nickname());
        return null;
    }


    private UserResponse postRequestParseToken(String token) {
        TeamRequest request = new TeamRequest("안홍범", "7372");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization" , token);
        HttpEntity<TeamRequest> requestEntity = new HttpEntity<>(
                request,
                httpHeaders
        );

        UserResponse response = restTemplate
                .postForEntity(
                        "http://192.168.0.12:8080/api/v1/auth/token"
                        , requestEntity
                        ,UserResponse.class
                ).getBody();
        return response;
    }
}
