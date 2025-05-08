package com.kks.kks_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders") // 💯 복수형 명시
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 유저 정보 (필수)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ 상품 정보 (필수)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ 결제 가격
    @Column(nullable = false)
    private int price;

    // ✅ 배송 정보
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String orderNumber;
}
