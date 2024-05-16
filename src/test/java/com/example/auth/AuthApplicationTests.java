package com.example.auth;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.global.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class AuthApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
	}
	@Test
	void 데이터_주입(){
		List<User> users = new ArrayList<>();
		User user1 = User.builder().id(UUID.randomUUID()).email("test1@test.com").nickname("test1").birthDay(LocalDate.now()).gender("남").build();
		User user2 = User.builder().id(UUID.randomUUID()).email("test2@test.com").nickname("test2").birthDay(LocalDate.now()).gender("남").build();
		User user3 = User.builder().id(UUID.randomUUID()).email("test3@test.com").nickname("test3").birthDay(LocalDate.now()).gender("남").build();
		User user4 = User.builder().id(UUID.randomUUID()).email("test4@test.com").nickname("test4").birthDay(LocalDate.now()).gender("남").build();
		User user5 = User.builder().id(UUID.randomUUID()).email("test5@test.com").nickname("test5").birthDay(LocalDate.now()).gender("남").build();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		userRepository.saveAll(users);
		String token = jwtUtil.createToken(user3);
		System.out.println(token);
	}

}
