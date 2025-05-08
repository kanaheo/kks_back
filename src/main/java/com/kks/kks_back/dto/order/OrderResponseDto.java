package com.kks.kks_back.dto.order;

import com.kks.kks_back.entity.Product;
import com.kks.kks_back.entity.ProductImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.kks.kks_back.entity.Order;

import java.util.List;


@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private String orderNumber;
    private String productName;
    private String productImageUrl;

    public OrderResponseDto(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    // 👉 주문 리스트용 (주문 + 상품 + 이미지 포함)
    public OrderResponseDto(Order order) {
        this.orderNumber = order.getOrderNumber();

        Product product = order.getProduct();
        if (product != null) {
            this.productName = product.getTitle(); // name → title 맞게 수정 완료!

            List<ProductImage> images = product.getImages();
            if (images != null && !images.isEmpty()) {
                this.productImageUrl = images.get(0).getImageUrl(); // ✅ 대표 이미지
            }
        }
    }
}
