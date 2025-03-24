package com.kks.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users/register", "/users").permitAll() // 회원가입 및 GET 요청 허용
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            );

        return http.build();
    }
}
