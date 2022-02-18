package com.ekichabi_business_registration.app.common;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubcategoryEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TestEntities {
    public static final CategoryEntity CATEGORY = CategoryEntity.builder()
            .name("Restaurant")
            .build();

    public static final SubcategoryEntity SUBCATEGORY_1 = SubcategoryEntity.builder()
            .name("Burgers")
            .category(CATEGORY)
            .build();

    public static final SubcategoryEntity SUBCATEGORY_2 = SubcategoryEntity.builder()
            .name("Shakes")
            .category(CATEGORY)
            .build();

    public static final DistrictEntity DISTRICT = DistrictEntity.builder()
            .name("Capitol Hill")
            .build();

    public static final VillageEntity VILLAGE = VillageEntity.builder()
            .name("Broadway")
            .district(DISTRICT)
            .build();

    public static final SubvillageEntity SUBVILLAGE = SubvillageEntity.builder()
            .name("And Pine")
            .village(VILLAGE)
            .build();
    public static final AccountEntity ACCOUNT_1 =
            AccountEntity.builder().name("Fremont Troll").createdAt(LocalDateTime.now()).build();
    public static final AccountEntity ACCOUNT_2 =
            AccountEntity.builder().name("Mariner Moose").createdAt(LocalDateTime.now()).build();
    public static final BusinessEntity BUSINESS_ENTITY = BusinessEntity.builder()
            .coordinates("69.420.1234")
            .name("Dicks")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .category(CATEGORY)
            .owners(Arrays.asList(ACCOUNT_1, ACCOUNT_2))
            .subcategories(Arrays.asList(SUBCATEGORY_1, SUBCATEGORY_2))
            .subvillage(SUBVILLAGE)
            .build();
}
