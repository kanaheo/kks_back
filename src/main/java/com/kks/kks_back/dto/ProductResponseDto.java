package com.kks.kks_back.dto;

import com.kks.kks_back.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private final Long id;
    private final String title;
    private final String description;
    private final int price;
    private final String location;
    private final String category;
    private final String imageUrl;
    private final String sellerNickname;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.location = product.getLocation();
        this.sellerNickname = product.getSeller().getNickname();
        this.category = product.getCategory();
        this.imageUrl = product.getImages().isEmpty()
                ? null
                : product.getImages().get(0).getImageUrl();
    }
}

