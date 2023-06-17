package com.example.motocatalog.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler("/motos");

        return http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .successHandler(successHandler)
                        .permitAll())
                .build();
    }

    // アカウント新規作成機能は実装しないためアカウント作成はこの処理に依存
    // 作成したいアカウント分だけlog.warn()で出力すれば良い
    @Bean
    protected PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        log.warn("*******************************************************************");
        log.warn("password: {}", encoder.encode("test"));
        log.warn("password: {}", encoder.encode("admin"));
        log.warn("*******************************************************************");
        return encoder;
    }
}