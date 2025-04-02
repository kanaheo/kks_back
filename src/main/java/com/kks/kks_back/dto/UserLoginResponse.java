package com.kks.kks_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private String token;
    // 필요시 다른 사용자 정보도 포함 (예: 이메일, 닉네임)
    private String email;
    private String nickname;  // 비밀번호는 제외
}
