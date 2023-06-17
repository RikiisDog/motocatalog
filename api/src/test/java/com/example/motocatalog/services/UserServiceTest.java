package com.example.motocatalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService service;

    @Test
    @DisplayName("ユーザ取得:正常")
    void userServiceTest01() throws Exception {
        UserDetails user = service.loadUserByUsername("test");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        assertEquals(
                encoder.matches("test", user.getPassword()), true);
    }

    @Test
    @DisplayName("ユーザ取得:異常")
    void userServiceTest02() {
        // DBに存在しないユーザ名を取得しようとすると例外がスローされることを確認
        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("notfound"));
    }
}
