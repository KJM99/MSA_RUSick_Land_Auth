package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.LoginResponse;
import com.example.auth.domain.response.UserResponse;
import com.example.auth.global.utils.JwtUtil;
import com.example.auth.global.utils.TokenInfo;
import com.netflix.discovery.converters.Auto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceImplTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

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
    String 토큰_발급(){
        User user = User.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .email("tes1@tt.com")
                .nickname("test")
                .gender("남")
                .birthDay(LocalDate.parse("2020-05-12"))
                .build();
        userRepository.save(user);
        String token = jwtUtil.createToken(user);
        return token;
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
    @Nested
    @Transactional
    class 토큰_재발급{
        @Test
        void 토큰_재발급_성공(){
            //given
            String token1 = 토큰_발급();
            TokenInfo info1 = jwtUtil.parseToken(token1);
            //when
            LoginResponse loginResponse2 = authService.refresh(info1);
            TokenInfo info2 = jwtUtil.parseToken(loginResponse2.token());
            //then
            assertEquals(info1.id(), info2.id());
            assertEquals(info1.nickname(), info2.nickname());
        }
    }

    @Nested
    class UUID_요청{
        @Test
        void 성공(){
            //given
            String email = "test1@test.com";
            //when
            UUID uuid = authService.getUserIdByEmail(email);
            User user = userRepository.findById(uuid).get();
            //then
            assertNotNull(uuid);
            assertEquals(email, user.getEmail());
        }
        @Test
        void 실패(){
            //given
            String email = "test123456@test.com";
            //when
            UUID uuid = authService.getUserIdByEmail(email);
            //then
            assertNull(uuid);
        }
    }
    @Test
    void 테스트(){
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("tes1@tt.com")
                .nickname("test")
                .gender("남")
                .birthDay(LocalDate.parse("2020-05-12"))
                .build();
        userRepository.save(user);
        List<User> list1 = userRepository.findAll();
        System.out.println(list1.get(list1.size()-1).getId());
    }
}