package com.ekichabi_business_registration.app;

import com.ekichabi_business_registration.app.common.IntegrationTest;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubcategoryEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import com.ekichabi_business_registration.db.repository.BusinessRepository;
import com.ekichabi_business_registration.db.repository.CategoryRepository;
import com.ekichabi_business_registration.db.repository.DistrictRepository;
import com.ekichabi_business_registration.db.repository.SubcategoryRepository;
import com.ekichabi_business_registration.db.repository.SubvillageRepository;
import com.ekichabi_business_registration.db.repository.VillageRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTest
class BusinessRepositoryTests {
    @Autowired
    private BusinessRepository businessRepository;
    private static CategoryEntity CATEGORY;
    private static SubcategoryEntity SUBCATEGORY_1;
    private static SubcategoryEntity SUBCATEGORY_2;
    private static DistrictEntity DISTRICT;
    private static VillageEntity VILLAGE;
    private static SubvillageEntity SUBVILLAGE;

    @BeforeAll
    public static void setUp(
            @Autowired CategoryRepository categoryRepository,
            @Autowired SubcategoryRepository subcategoryRepository,
            @Autowired DistrictRepository districtRepository,
            @Autowired VillageRepository villageRepository,
            @Autowired SubvillageRepository subvillageRepository) {

        CATEGORY = CategoryEntity.builder()
                .name("Restaurant")
                .build();
        CATEGORY = categoryRepository.save(CATEGORY);

        SUBCATEGORY_1 = SubcategoryEntity.builder()
                .name("Burgers")
                .category(CATEGORY)
                .build();
        SUBCATEGORY_1 = subcategoryRepository.save(SUBCATEGORY_1);

        SUBCATEGORY_2 = SubcategoryEntity.builder()
                .name("Shakes")
                .category(CATEGORY)
                .build();
        SUBCATEGORY_2 = subcategoryRepository.save(SUBCATEGORY_2);

        DISTRICT = DistrictEntity.builder()
                .name("Capitol Hill")
                .build();
        DISTRICT = districtRepository.save(DISTRICT);

        VILLAGE = VillageEntity.builder()
                .name("(none)")
                .district(DISTRICT)
                .build();
        VILLAGE = villageRepository.save(VILLAGE);

        SUBVILLAGE = SubvillageEntity.builder()
                .name("(none)")
                .village(VILLAGE)
                .build();
        SUBVILLAGE = subvillageRepository.save(SUBVILLAGE);
    }

    @AfterAll
    public static void tearDown(
            @Autowired CategoryRepository categoryRepository,
            @Autowired SubcategoryRepository subcategoryRepository,
            @Autowired DistrictRepository districtRepository,
            @Autowired VillageRepository villageRepository,
            @Autowired SubvillageRepository subvillageRepository) {
        subvillageRepository.delete(SUBVILLAGE);
        villageRepository.delete(VILLAGE);
        districtRepository.delete(DISTRICT);
        subcategoryRepository.delete(SUBCATEGORY_1);
        subcategoryRepository.delete(SUBCATEGORY_2);
        categoryRepository.delete(CATEGORY);
    }

    @Test
    @Transactional
    void getBusinessOK() {
        BusinessEntity business = BusinessEntity.builder()
                .category(CATEGORY)
                .subcategory(SUBCATEGORY_1)
                .subcategory(SUBCATEGORY_2)
                .name("Dick's Drive In")
                .build();
        business = businessRepository.save(business);
        Optional<BusinessEntity> savedBusiness = businessRepository.findById(business.getId());

        assertTrue(savedBusiness.isPresent(), "business should persist after saving");
        assertEquals(business.getCategory(), savedBusiness.get().getCategory());
        assertEquals(business.getSubcategories().size(), savedBusiness.get().getSubcategories().size());
    }
}
