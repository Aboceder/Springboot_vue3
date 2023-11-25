package com.bopomofo.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void passwordTest() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode("123123123");
        System.out.println("encodePassword = " + encodePassword);
        System.out.printf("123与加密后的密码对比结果：%s", encoder.matches("123", encodePassword));
    }

}
