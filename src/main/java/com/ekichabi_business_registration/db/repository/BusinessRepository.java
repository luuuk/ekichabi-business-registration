package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubcategoryEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends CrudRepository<BusinessEntity, Long> {
    boolean existsByCategoryAndSubvillage(CategoryEntity category,
                                          SubvillageEntity subvillage);
    List<BusinessEntity> findAllByCategory(CategoryEntity category);
    List<BusinessEntity> findAllByDistrict(DistrictEntity district);
    List<BusinessEntity> findAllBySubcategory(SubcategoryEntity subcategory);
    List<BusinessEntity> findAllByVillage(VillageEntity village);
}
