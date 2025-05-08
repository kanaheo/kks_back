package com.kks.kks_back.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @NotBlank(message = "결제 수단 ID는 필수입니다.")
    private String paymentMethodId;

    @NotBlank(message = "받는 사람을 입력해주세요.")
    private String recipient;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;
}
