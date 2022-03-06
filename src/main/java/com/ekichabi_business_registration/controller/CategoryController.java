package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@Transactional
public class CategoryController {
    private final CategoryService service;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @GetMapping("categories")
    public ResponseEntity<List<CategoryEntity>> findAll() {
        logger.info("Calling FindAllCategories()");
        List<CategoryEntity> categoryEntities = service.findAllCategories();
        return ResponseEntity.ok().body(categoryEntities);
    }
}
