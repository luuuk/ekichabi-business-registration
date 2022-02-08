package com.ekichabi_business_registration.app.common;

import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubcategoryEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import com.ekichabi_business_registration.db.repository.CategoryRepository;
import com.ekichabi_business_registration.db.repository.DistrictRepository;
import com.ekichabi_business_registration.db.repository.SubcategoryRepository;
import com.ekichabi_business_registration.db.repository.SubvillageRepository;
import com.ekichabi_business_registration.db.repository.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestHelperService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final DistrictRepository districtRepository;
    private final VillageRepository villageRepository;
    private final SubvillageRepository subvillageRepository;

    public static CategoryEntity CATEGORY;
    public static SubcategoryEntity SUBCATEGORY_1;
    public static SubcategoryEntity SUBCATEGORY_2;
    public static DistrictEntity DISTRICT;
    public static VillageEntity VILLAGE;
    public static SubvillageEntity SUBVILLAGE;

    public void setUp() {

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

    public void tearDown() {
        subvillageRepository.delete(SUBVILLAGE);
        villageRepository.delete(VILLAGE);
        districtRepository.delete(DISTRICT);
        subcategoryRepository.delete(SUBCATEGORY_1);
        subcategoryRepository.delete(SUBCATEGORY_2);
        categoryRepository.delete(CATEGORY);
    }
}
