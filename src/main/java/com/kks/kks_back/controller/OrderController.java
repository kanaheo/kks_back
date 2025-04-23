package com.kks.kks_back.controller;

import com.kks.kks_back.dto.OrderRequestDto;
import com.kks.kks_back.service.OrderService;
import com.kks.kks_back.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long orderId = orderService.createOrder(requestDto, userDetails.getUser());
        return ResponseEntity.ok(Map.of("orderId", orderId));
    }

}
