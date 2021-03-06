package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.util.exceptions.InvalidCreationException;
import com.ekichabi_business_registration.util.exceptions.InvalidUpdateException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@Transactional
public class BusinessController {
    private final BusinessService service;
    private final Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @GetMapping("businesses")
    public ResponseEntity<List<BusinessEntity>> findAll() {
        logger.info("Calling FindAllBusinesses()");
        List<BusinessEntity> businessEntities = service.findAllBusinesses();
        return ResponseEntity.ok().body(businessEntities);
    }

    @GetMapping("business/{id}")
    public ResponseEntity<?> findBusinessById(@PathVariable String id) {
        logger.info("Finding business with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }

    @PostMapping("business")
    public ResponseEntity<?> createBusiness(@RequestBody BusinessEntity businessEntity) {
        logger.info("Creating business " + businessEntity.toString());
        try {
            BusinessEntity created = service.createBusiness(businessEntity);
            return ResponseEntity.ok().body(created);
        } catch (InvalidCreationException e) {
            return ResponseEntity.badRequest()
                    .body("Please pass a valid business in your request body");
        }
    }

    @GetMapping("businessesByCategory/{category}")
    public ResponseEntity<List<BusinessEntity>> findAllByCategory(
            @PathVariable String category) {
        logger.info("Calling FindAllBusinessesByCategory()");
        List<BusinessEntity> businessEntities = service.findAllBusinessesByCategory(category);
        return ResponseEntity.ok().body(businessEntities);
    }

    @GetMapping("businessesByUpdatedAt/{timestamp}")
    public ResponseEntity<List<BusinessEntity>> findAllByUpdatedAt(
            @PathVariable String timestamp) {
        logger.info("Calling findAllBusinessByUpdatedAt()");
        List<BusinessEntity> businessEntities = service.findAllBusinessByUpdatedAt(timestamp);
        return ResponseEntity.ok().body(businessEntities);
    }

    /**
     * Update a business. Will fail if called with owners.size != 1.
     * TODO Need to devise a new mechanism for updating business owners.
     *
     * @param id       id of the business to update
     * @param business updates to given business
     * @return business if successful, otherwise bad request
     */
    @PatchMapping("business/{id}")
    public ResponseEntity<?> updateBusiness(@PathVariable Long id,
                                            @RequestBody BusinessEntity business) {
        logger.info("Updating business " + business.toString());
        try {
            BusinessEntity updated = service.updateBusiness(id, business);
            return ResponseEntity.ok().body(updated);
        } catch (InvalidUpdateException e) {
            return ResponseEntity.badRequest()
                    .body("Please pass a valid business in your request body");
        }
    }
}
