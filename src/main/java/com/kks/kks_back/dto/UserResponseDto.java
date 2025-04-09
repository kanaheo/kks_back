package com.kks.kks_back.dto;

import com.kks.kks_back.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private boolean isSocial;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.isSocial = user.isSocial();
    }
}
