package com.ekichabi_business_registration.app.db;

import com.ekichabi_business_registration.app.common.IntegrationTest;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.repository.BusinessRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ekichabi_business_registration.app.common.TestEntities.BUSINESS_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTest
class BusinessRepositoryTests {
    @Autowired
    private BusinessRepository businessRepository;

    @Test
    @Transactional
    void getBusinessOK() {
        businessRepository.save(BUSINESS_ENTITY);
        Optional<BusinessEntity> savedBusiness =
                businessRepository.findById(BUSINESS_ENTITY.getId());

        assertTrue(savedBusiness.isPresent(), "business should persist after saving");
        assertEquals(BUSINESS_ENTITY.getCategory(), savedBusiness.get().getCategory());
        assertEquals(BUSINESS_ENTITY.getSubcategories().size(),
                savedBusiness.get().getSubcategories().size());
    }
}
