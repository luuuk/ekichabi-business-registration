package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.service.CategoryService;
import com.ekichabi_business_registration.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private final Logger logger = LoggerFactory.getLogger(BusinessController.class);
    private final CategoryService categoryService;
    private final GeoService geoService;
    private final BusinessService businessService;
    private String password;

    @Autowired
    private void setPassword(Environment environment) {
        String configPassword = environment.getProperty("admin-password", String.class);
        if (configPassword == null) {
            throw new IllegalAccessError("property admin-password is not set");
        }
        password = configPassword;
    }


    /**
     * Creates categories based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("categories/{auth}")
    public ResponseEntity<?> createCategories(@PathVariable String auth) {
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating categories from Categories.txt");
        try {
            int createdCount = categoryService.createCategories();
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
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating subcategories from Categories.txt");
        try {
            int createdCount = categoryService.createSubcategories();
            return ResponseEntity.ok().body("Created " + createdCount + " new subcategories");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create subcategories");
        }
    }

    /**
     * Creates districts based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("districts/{auth}")
    public ResponseEntity<?> createDistricts(@PathVariable String auth) {
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating districts from Census v1 data");
        try {
            int createdCount = geoService.createDistricts();
            return ResponseEntity.ok().body("Created " + createdCount + " new districts");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create districts");
        }
    }

    /**
     * Creates villages based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("villages/{auth}")
    public ResponseEntity<?> createVillages(@PathVariable String auth) {
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating villages from Census v1 data");
        try {
            int createdCount = geoService.createVillages();
            return ResponseEntity.ok().body("Created " + createdCount + " new villages");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create villages");
        }
    }

    /**
     * Creates subvillages based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("subvillages/{auth}")
    public ResponseEntity<?> createSubvillages(@PathVariable String auth) {
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating subvillages from Census v1 data");
        try {
            int createdCount = geoService.createSubvillages();
            return ResponseEntity.ok().body("Created " + createdCount + " new subvillages");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create subvillages");
        }
    }

    /**
     * Creates businesses based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("businesses/{auth}")
    public ResponseEntity<?> createBusinesses(@PathVariable String auth) {
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating businesses from Census v1 data");
        try {
            int createdCount = businessService.createBusinesses();
            return ResponseEntity.ok().body("Created " + createdCount + " new businesses");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create businesses");
        }
    }

    /**
     * Creates all entities based on the contents of census_full.csv
     * in the resources/static directory
     **/
    @PostMapping("seed-from-census/{auth}")
    public ResponseEntity<?> createFromCensus(@PathVariable String auth) {
        if (!auth.equals(password)) {
            return ResponseEntity.badRequest()
                    .body("Admin user not authenticated");
        }
        logger.info("Creating businesses from Census v1 data");
        try {
            categoryService.createCategories();
            categoryService.createSubcategories();
            geoService.createDistricts();
            geoService.createVillages();
            geoService.createSubvillages();
            int createdCount = businessService.createBusinesses();
            return ResponseEntity.ok().body("Created " + createdCount + " new businesses");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Could not create businesses");
        }
    }
}
