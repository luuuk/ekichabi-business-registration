package com.ekichabi_business_registration.app.service;

import com.ekichabi_business_registration.app.common.IntegrationTest;
import com.ekichabi_business_registration.app.common.TestHelperService;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.service.BusinessService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@IntegrationTest
public class BusinessServiceTests {
    private static TestHelperService testHelperService;

    @Autowired
    private BusinessService businessService;

    @BeforeAll
    static void setUp() {
        testHelperService.setUp();
    }

    @AfterAll
    public static void tearDown() {
        testHelperService.tearDown();
    }

    @Test
    void findAllBusinesses_Success(){
     List<BusinessEntity> businessEntityList = businessService.findAllBusinesses();


     //     assert businessEntityList.containsAll();
    }
}
