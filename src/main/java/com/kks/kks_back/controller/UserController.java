package com.kks.kks_back.controller;

import com.kks.kks_back.dto.UserLoginRequest;
import com.kks.kks_back.dto.UserLoginResponse;
import com.kks.kks_back.dto.UserSignupRequest;
import com.kks.kks_back.entity.User;
import com.kks.kks_back.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // ✅ 여기가 핵심! 이제 모든 경로는 /api/users/~~
public class UserController {

    private final UserService userService;

    // ✅ 회원가입: POST /api/users/signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
    }

    // ✅ 유저 전체 조회: GET /api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // ✅ 로그인: POST /api/users/login
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @RequestBody UserLoginRequest request,
            HttpServletResponse response) {

        UserLoginResponse loginResponse = userService.login(request);
        String token = loginResponse.getToken();

        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS일 때 true
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7); // 7일

        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponse);
    }

    // ✅ 내 정보 조회: GET /api/users/meinfo
    @GetMapping("/meinfo")
    public ResponseEntity<String> getMyInfo(HttpServletRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok("현재 로그인한 유저: " + email);
    }

    // ✅ 로그아웃: POST /api/users/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 즉시 만료

        response.addCookie(cookie);
        return ResponseEntity.ok("로그아웃 완료");
    }
}
