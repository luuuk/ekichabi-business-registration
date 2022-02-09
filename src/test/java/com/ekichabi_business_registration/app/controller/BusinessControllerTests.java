package com.ekichabi_business_registration.app.controller;


import com.ekichabi_business_registration.app.common.IntegrationTest;
import com.ekichabi_business_registration.controller.BusinessController;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
public class BusinessControllerTests {

    @Autowired
    BusinessController businessController;

    @Test
    void findAllBusinesses_Success() {
        // TODO add businesses to DB and verify inclusion in response body
        ResponseEntity<List<BusinessEntity>> responseEntity = businessController.findAll();

        assertEquals (responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
