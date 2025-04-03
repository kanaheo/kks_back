package com.kks.kks_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.kks.kks_back.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import com.kks.kks_back.util.JwtUtil;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/signup",
                                "/api/users/login",
                                "/api/users/logout",
                                "/login/oauth2/**"
                        ).permitAll()  // ✅ 여기 경로도 전부 수정!
                        .anyRequest().authenticated()
                )
                .oauth2Login()  // OAuth2 로그인 처리
                .loginPage("/login")  // 사용자 정의 로그인 페이지 (필요 시)
                .defaultSuccessUrl("/home")  // 로그인 성공 후 리디렉션 경로
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
