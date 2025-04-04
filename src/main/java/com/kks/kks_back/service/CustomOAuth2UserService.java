package com.kks.kks_back.service;

import com.kks.kks_back.entity.User;
import com.kks.kks_back.repository.UserRepository;
import com.kks.kks_back.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public void handleOAuthLogin(OAuth2User oauthUser, HttpServletResponse response) {
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        // ✅ 가짜 이메일 생성
        if (email == null || email.isBlank()) {
            String githubId = oauthUser.getAttribute("id").toString();
            email = "github_" + githubId + "@github.com";
        }

        String finalEmail = email;

        // ✅ DB 확인 후 없으면 저장
        User user = userRepository.findByEmail(finalEmail)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(finalEmail);
                    newUser.setNickname(name != null ? name : "GitHub유저");
                    newUser.setPassword(null);
                    newUser.setSocial(true);
                    return userRepository.save(newUser);
                });

        // ✅ JWT 발급
        String token = jwtUtil.createToken(user.getEmail());

        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }
}
