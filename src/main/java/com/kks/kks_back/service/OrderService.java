package com.kks.kks_back.service;

import com.kks.kks_back.dto.OrderRequestDto;
import com.kks.kks_back.entity.Order;
import com.kks.kks_back.entity.Product;
import com.kks.kks_back.entity.User;
import com.kks.kks_back.repository.OrderRepository;
import com.kks.kks_back.repository.ProductRepository;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public Long createOrder(OrderRequestDto dto, User user) {
        Stripe.apiKey = stripeSecretKey;

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않음"));
//.setAmount((long) product.getPrice())
        // Stripe 결제
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount((long) product.getPrice() * 100)
                .setCurrency("krw")
                .setPaymentMethod(dto.getPaymentMethodId())
                .setConfirm(true)
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER) // ✨ 요놈이 핵심!!!
                                .build()
                )
                .build();


        try {
            PaymentIntent intent = PaymentIntent.create(createParams);
        } catch (Exception e) {
            throw new RuntimeException("결제 실패: " + e.getMessage());
        }

        // 주문 저장
        Order order = Order.builder()
                .user(user)
                .product(product)
                .price(product.getPrice())
                .address(dto.getAddress())
                .recipient(dto.getRecipient())
                .phone(dto.getPhone())
                .build();

        orderRepository.save(order);
        return order.getId();
    }
}
