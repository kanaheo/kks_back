package com.kks.kks_back.controller;

import com.kks.kks_back.dto.ProductResponseDto;
import com.kks.kks_back.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createProduct(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int price,
            @RequestParam String location,
            @RequestParam String category,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        productService.createProduct(title, description, price, location, category, images);

        return ResponseEntity.ok(Map.of("message", "상품 등록 완료"));
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(productService.getAllProducts(category));
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}

