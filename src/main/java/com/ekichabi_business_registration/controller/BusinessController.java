package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.service.InvalidCreationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    // TODO implement remaining endpoints here
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessNameById(@PathVariable String id) {
        logger.info("Getting business name with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.name);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessCategoryById(@PathVariable String id) {
        logger.info("Getting business category with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessSubcategoriesById(@PathVariable String id) {
        logger.info("Getting business subcategories with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.subcategories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessSubvillageById(@PathVariable String id) {
        logger.info("Getting business subvillage with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.subvillage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessCoordinatesById(@PathVariable String id) {
        logger.info("Getting business coordinates with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.coordinates);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessOwnersById(@PathVariable String id) {
        logger.info("Getting business owners with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.owners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
    @GetMapping("business/{id}")
    public ResponseEntity<?> getBusinessPhoneNumbersById(@PathVariable String id) {
        logger.info("Getting business phone numbers with id: " + id);
        try {
            BusinessEntity entity = service.findBusinessById(Long.parseLong(id));
            return ResponseEntity.ok().body(entity.phoneNumbers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not find business with ID " + id
                    + ". Make sure your ID is numerical");
        }
    }
    
}
