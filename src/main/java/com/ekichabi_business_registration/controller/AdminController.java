package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller should be home to REST endpoints for use by developers/admins
 * (BATCH insert, UPDATE non-business table, etc)
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Transactional
public class AdminController {
    private static final String PASSWORD = "supersecretpassword";
    private final Logger logger = LoggerFactory.getLogger(BusinessController.class);
    private final CategoryService service;

    /**
     * Creates categories based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("categories/{auth}")
    public ResponseEntity<?> createCategories(@PathVariable String auth) {
        if (!auth.equals(PASSWORD)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating categories from Categories.txt");
        try {
            int createdCount = service.createCategories();
            return ResponseEntity.ok().body("Created " + createdCount + " new categories");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create categories");
        }
    }

    /**
     * Creates subcategories based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("subcategories/{auth}")
    public ResponseEntity<?> createSubcategories(@PathVariable String auth) {
        if (!auth.equals(PASSWORD)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating subcategories from Categories.txt");
        try {
            int createdCount = service.createSubcategories();
            return ResponseEntity.ok().body("Created " + createdCount + " new subcategories");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create subcategories");
        }
    }


}
