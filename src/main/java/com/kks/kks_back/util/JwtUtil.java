package com.kks.kks_back.util;

import com.kks.kks_back.entity.User;
import com.kks.kks_back.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1일
    private final Key key;
    private final UserRepository userRepository;

    public JwtUtil(@Value("${jwt.secret}") String secretKey, UserRepository userRepository) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.userRepository = userRepository;
    }

    public String createToken(String email, String nickname) {
        return Jwts.builder()
                .setSubject(email)
                .claim("nickname", nickname)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public User getUserFromEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));
    }
}
