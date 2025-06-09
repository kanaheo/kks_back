package com.kks.kks_back.controller;

import com.kks.kks_back.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = categoryRepository.findAll()
                .stream()
                .map(c -> c.getName())
                .toList();
        return ResponseEntity.ok(categories);
    }
}

