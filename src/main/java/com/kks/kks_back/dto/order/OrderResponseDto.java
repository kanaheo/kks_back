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

    public OrderResponseDto(Order order) {
        this.orderNumber = order.getOrderNumber();

        Product product = order.getProduct();
        if (product != null) {
            this.productName = product.getTitle();

            List<ProductImage> images = product.getImages();
            if (images != null && !images.isEmpty()) {
                this.productImageUrl = images.get(0).getImageUrl(); // ✅ 첫 번째 이미지를 대표로 사용
            }
        }
    }
}
