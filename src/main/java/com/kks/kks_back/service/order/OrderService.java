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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StripeService stripeService;

    public String createOrder(OrderRequestDto dto, User user) {
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
                .orderNumber(generateOrderNumber())
                .build();

        orderRepository.save(order);
        return order.getOrderNumber();
    }

    private String generateOrderNumber() {
        return "KKS-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    public Order findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("주문번호를 찾을 수 없습니다: " + orderNumber));
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findAllByUser(user);
    }
}
