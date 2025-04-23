package com.kks.kks_back.service.order;

import com.kks.kks_back.dto.order.OrderRequestDto;
import com.kks.kks_back.entity.Order;
import com.kks.kks_back.entity.Product;
import com.kks.kks_back.entity.User;
import com.kks.kks_back.exception.order.PaymentException;
import com.kks.kks_back.repository.OrderRepository;
import com.kks.kks_back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StripeService stripeService;

    public Long createOrder(OrderRequestDto dto, User user) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않음"));

        try {
            stripeService.charge(product.getPrice(), dto.getPaymentMethodId());
        } catch (Exception e) {
            throw new PaymentException("결제 실패: " + e.getMessage());
        }

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
