package com.kks.kks_back.security;

import com.kks.kks_back.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 권한 안 쓸 거면 null 또는 List.of() 사용
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 로그인할 때 비교할 비밀번호
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // 로그인 ID 기준 (이메일)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
