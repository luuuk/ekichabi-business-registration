package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubvillageRepository extends CrudRepository<SubvillageEntity, Integer> {
    boolean existsByNameAndVillage(String name, VillageEntity parentVillage);
}
