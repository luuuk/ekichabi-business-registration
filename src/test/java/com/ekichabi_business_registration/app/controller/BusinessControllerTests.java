package com.ekichabi_business_registration.app.controller;


import com.ekichabi_business_registration.app.common.IntegrationTest;
import com.ekichabi_business_registration.controller.BusinessController;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.repository.BusinessRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

import static com.ekichabi_business_registration.app.common.TestEntities.BUSINESS_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTest
public class BusinessControllerTests {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessController businessController;

    @Test
    @Transactional
    void findAllBusinessesSuccess() {
        BusinessEntity saved = businessRepository.save(BUSINESS_ENTITY);

        ResponseEntity<List<BusinessEntity>> responseEntity = businessController.findAll();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.getBody().contains(saved));
    }

    @Test
    @Transactional
    void findAllBusinessesNoBusinesses() {
        ResponseEntity<List<BusinessEntity>> responseEntity = businessController.findAll();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.getBody().isEmpty());
    }

    @Test
    @Transactional
    void findBusinessByIdSuccess() {
        BusinessEntity saved = businessRepository.save(BUSINESS_ENTITY);

        ResponseEntity<?> responseEntity =
                businessController.findBusinessById(saved.getId().toString());

        assertTrue(responseEntity.getBody() instanceof BusinessEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Transactional
    void findBusinessByIdFailure() {
        ResponseEntity<?> responseEntity =
                businessController.findBusinessById(String.valueOf(Long.MAX_VALUE));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @Transactional
    void createBusinessSuccess() {
        // Create new business by invoking controller method
        ResponseEntity<?> responseEntity = businessController.createBusiness(BUSINESS_ENTITY);

        // Parse ID from newly created business
        assertTrue(responseEntity.getBody() instanceof BusinessEntity);
        BusinessEntity created = (BusinessEntity) responseEntity.getBody();

        // Assert that newly created business is in db
        BusinessEntity found =
                (BusinessEntity) businessController.findBusinessById(created.getId().toString())
                        .getBody();
        assertEquals(created, found);
    }

    @Test
    @Transactional
    void createBusinessNoFieldsFailure() {
        // Create new business by invoking controller method
        ResponseEntity<?> responseEntity =
                businessController.createBusiness(BusinessEntity.builder().build());

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
