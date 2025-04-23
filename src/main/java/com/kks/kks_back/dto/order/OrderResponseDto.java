package com.kks.kks_back.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;

    public OrderResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}
