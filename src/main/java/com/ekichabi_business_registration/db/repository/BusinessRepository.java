package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends CrudRepository<BusinessEntity, Long> {
    boolean existsByCategoryAndSubvillage(CategoryEntity category,
                                          SubvillageEntity subvillage);
}
