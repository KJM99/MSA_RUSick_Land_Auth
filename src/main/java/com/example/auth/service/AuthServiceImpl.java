package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserInfoResponse;
import com.example.auth.domain.response.UserResponse;
import com.example.auth.global.utils.JwtUtil;
import com.example.auth.global.utils.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(String token) {
        User user = postRequestParseToken(token).toEntity();
        Optional<User> optional = userRepository.findById(user.getId());
        if (optional.isEmpty()) {
            userRepository.save(user);
        }
        return LoginResponse.from(
                jwtUtil.createToken(user)
        );
    }

    @Override
    public LoginResponse refresh(TokenInfo tokenInfo) {
        User user = userRepository.findById(UUID.fromString(tokenInfo.id())).orElseThrow();
        String token = jwtUtil.createToken(user);
        return LoginResponse.from(token);
    }

    @Override
    public UserInfoResponse getUserIdByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null) return null;
        return new UserInfoResponse(user.getId(), user.getNickname());
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
