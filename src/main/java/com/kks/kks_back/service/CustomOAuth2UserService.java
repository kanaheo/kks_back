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

    public void handleOAuthLogin(String registrationId, OAuth2User oauthUser, HttpServletResponse response) {
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String id = oauthUser.getAttribute("id") != null
                ? oauthUser.getAttribute("id").toString()
                : oauthUser.getAttribute("sub").toString();

        if (email == null || email.isBlank()) {
            email = registrationId + "_" + id + "@" + registrationId + ".com";
        } else {
            email = registrationId + "_" + email;
        }

        String finalEmail = email;

        // ✅ DB 확인 후 없으면 저장
        User user = userRepository.findByEmail(finalEmail)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(finalEmail);
                    newUser.setNickname(name != null ? name : registrationId + "유저");
                    newUser.setPassword(null); // 👉 소셜 로그인은 비번 없음!
                    newUser.setSocial(true);
                    return userRepository.save(newUser);
                });

        // ✅ JWT 발급
        String token = jwtUtil.createToken(user.getEmail(), user.getNickname());

        // ✅ 쿠키로 저장
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
    }

}
