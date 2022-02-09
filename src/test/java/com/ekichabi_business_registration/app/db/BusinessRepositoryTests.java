package com.ekichabi_business_registration.app.db;

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
    private static CategoryEntity category;
    private static SubcategoryEntity subcategory1;
    private static SubcategoryEntity subcategory2;
    private static DistrictEntity district;
    private static VillageEntity village;
    private static SubvillageEntity subvillage;
    @Autowired
    private BusinessRepository businessRepository;

    @BeforeAll
    public static void setUp(
            @Autowired CategoryRepository categoryRepository,
            @Autowired SubcategoryRepository subcategoryRepository,
            @Autowired DistrictRepository districtRepository,
            @Autowired VillageRepository villageRepository,
            @Autowired SubvillageRepository subvillageRepository) {

        category = CategoryEntity.builder()
                .name("Restaurant")
                .build();
        category = categoryRepository.save(category);

        subcategory1 = SubcategoryEntity.builder()
                .name("Burgers")
                .category(category)
                .build();
        subcategory1 = subcategoryRepository.save(subcategory1);

        subcategory2 = SubcategoryEntity.builder()
                .name("Shakes")
                .category(category)
                .build();
        subcategory2 = subcategoryRepository.save(subcategory2);

        district = DistrictEntity.builder()
                .name("Capitol Hill")
                .build();
        district = districtRepository.save(district);

        village = VillageEntity.builder()
                .name("(none)")
                .district(district)
                .build();
        village = villageRepository.save(village);

        subvillage = SubvillageEntity.builder()
                .name("(none)")
                .village(village)
                .build();
        subvillage = subvillageRepository.save(subvillage);
    }

    @AfterAll
    public static void tearDown(
            @Autowired CategoryRepository categoryRepository,
            @Autowired SubcategoryRepository subcategoryRepository,
            @Autowired DistrictRepository districtRepository,
            @Autowired VillageRepository villageRepository,
            @Autowired SubvillageRepository subvillageRepository) {
        subvillageRepository.delete(subvillage);
        villageRepository.delete(village);
        districtRepository.delete(district);
        subcategoryRepository.delete(subcategory1);
        subcategoryRepository.delete(subcategory2);
        categoryRepository.delete(category);
    }

    @Test
    @Transactional
    void getBusinessOK() {
        BusinessEntity business = BusinessEntity.builder()
                .category(category)
                .subcategory(subcategory1)
                .subcategory(subcategory2)
                .name("Dick's Drive In")
                .build();
        business = businessRepository.save(business);
        Optional<BusinessEntity> savedBusiness = businessRepository.findById(business.getId());

        assertTrue(savedBusiness.isPresent(), "business should persist after saving");
        assertEquals(business.getCategory(), savedBusiness.get().getCategory());
        assertEquals(business.getSubcategories().size(),
                savedBusiness.get().getSubcategories().size());
    }
}
