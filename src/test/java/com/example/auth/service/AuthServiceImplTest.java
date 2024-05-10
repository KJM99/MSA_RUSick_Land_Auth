package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserResponse;
import com.netflix.discovery.converters.Auto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

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

    @Nested
    @Transactional
    class 로그인{
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
        @Test
        void 최초_로그인() {
            //given
            SignInResponse response = 외부_서버_로그인();
            String token = response.tokenType + " " + response.token;
            List<User> oldList = userRepository.findAll();
            //when
            authService.login(token);
            //then
            List<User> newList = userRepository.findAll();
            assertEquals(1, newList.size() - oldList.size());
        }
        @Test
        void 두번째_로그인() {
            //given
            SignInResponse response1 = 외부_서버_로그인();
            String token1 = response1.tokenType + " " + response1.token;
            authService.login(token1);
            List<User> oldList = userRepository.findAll();
            SignInResponse response2 = 외부_서버_로그인();
            String token2 = response2.tokenType + " " + response2.token;
            //when
            authService.login(token2);
            //then
            List<User> newList = userRepository.findAll();
            assertEquals(newList.size(), oldList.size());
        }
    }

}