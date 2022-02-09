package com.ekichabi_business_registration.app.service;

import com.ekichabi_business_registration.app.common.IntegrationTest;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@IntegrationTest
// TODO complete test class
public class BusinessServiceTests {

    @Autowired
    private BusinessService businessService;

    @Test
    void findAllBusinessesSuccess() {
        List<BusinessEntity> businessEntityList = businessService.findAllBusinesses();
        // TODO add entities to business repo and verify that they're returned here
    }
}
