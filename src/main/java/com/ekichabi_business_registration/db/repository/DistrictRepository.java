package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.DistrictEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends CrudRepository<DistrictEntity, Integer> {
    boolean existsByName(String name);

    DistrictEntity findByName(String name);
}
