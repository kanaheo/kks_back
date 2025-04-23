package com.kks.kks_back.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long productId;
    private String paymentMethodId;
    private String address;
    private String recipient;
    private String phone;
}
