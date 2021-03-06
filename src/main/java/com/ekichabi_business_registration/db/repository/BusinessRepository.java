package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BusinessRepository extends CrudRepository<BusinessEntity, Long> {
    boolean existsByCategoryAndSubvillage(CategoryEntity category,
                                          SubvillageEntity subvillage);

    List<BusinessEntity> findAllByCategory(CategoryEntity category);

    List<BusinessEntity> findBusinessEntitiesByUpdatedAtAfter(LocalDateTime timestamp);
}
