package com.example.auth.global.config;

import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServerConfigTest {
    @Autowired
    private ServerConfig serverConfig;

    @Test
    void config_server_연결_성공() {
        //given
        String secret = "asjdkfnof4241085931nklasf1n1032nlkdsfmi1m2k2";
        Long expiration = 600000L;
        //when
        String getSecret = serverConfig.getSecret();
        Long getExpiration = serverConfig.getExpiration();
        //then
        assertEquals(secret, getSecret);
        assertEquals(expiration, getExpiration);
    }
}