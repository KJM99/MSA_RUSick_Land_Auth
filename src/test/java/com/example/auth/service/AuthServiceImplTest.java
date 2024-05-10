package com.example.auth.service;

import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthService authService;

    record SignInRequest(String email, String password) { }
    record SignInResponse(String token, String tokenType) {}
    SignInResponse 외부_서버_로그인(){
        SignInRequest request = new SignInRequest("tes1@tt.com", "1234");
        HttpEntity<SignInRequest> requestEntity = new HttpEntity<>(
                request
        );
        SignInResponse response = restTemplate
                .postForEntity(
                        "http://192.168.0.12:8080/api/v1/auth/signin"
                        , requestEntity
                        ,SignInResponse.class
                ).getBody();
        return response;
    }

    @Test
    void 로그인_성공() {
        //given
        SignInResponse response = 외부_서버_로그인();
        String token = response.tokenType + " " + response.token;
        //when
        LoginResponse loginResponse = authService.login(token);
        //then
        assertNotNull(loginResponse.token());
        assertEquals("Bearer", loginResponse.tokenType());
    }
}