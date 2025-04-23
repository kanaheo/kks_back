package com.kks.kks_back.controller;

import com.kks.kks_back.dto.order.OrderRequestDto;
import com.kks.kks_back.dto.order.OrderResponseDto;
import com.kks.kks_back.security.UserDetailsImpl;
import com.kks.kks_back.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    // ✅ 주문 생성
    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        Long orderId = orderService.createOrder(requestDto, userDetails.getUser());
        return ResponseEntity.ok(new OrderResponseDto(orderId));
    }
}
