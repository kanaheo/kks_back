package com.kks.kks_back.service;

import com.kks.kks_back.dto.ProductResponseDto;
import com.kks.kks_back.entity.Product;
import com.kks.kks_back.entity.ProductImage;
import com.kks.kks_back.entity.User;
import com.kks.kks_back.repository.ProductImageRepository;
import com.kks.kks_back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductImageRepository productImageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

        public void createProduct(String title, String desc, int price, String location, String category, List<MultipartFile> images) {
            User seller = userService.getMyInfo();

        // 1. 상품 저장
        Product product = Product.builder()
                .title(title)
                .description(desc)
                .price(price)
                .location(location)
                .category(category)
                .seller(seller)
                .build();

        productRepository.save(product);

        // 2. 이미지 저장
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                try {
                    // ✅ 실제 경로 조립 (루트 + 설정된 디렉토리)
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    String rootPath = System.getProperty("user.dir");
                    Path uploadPath = Paths.get(rootPath, uploadDir);
                    File folder = uploadPath.toFile();
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    Path filePath = uploadPath.resolve(fileName);
                    file.transferTo(filePath.toFile());

                    // DB 저장
                    ProductImage image = ProductImage.builder()
                            .imageUrl("/" + uploadDir + fileName) // ex) /uploads/uuid_img.jpg
                            .product(product)
                            .build();

                    productImageRepository.save(image);

                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 실패", e);
                }
            }
        }
    }


    public List<ProductResponseDto> getAllProducts(String category) {
        List<Product> products;

        if (category != null && !category.isBlank()) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(ProductResponseDto::new)
                .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        return new ProductResponseDto(product);
    }
}

