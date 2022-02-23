package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageRepository extends CrudRepository<VillageEntity, Integer> {
    boolean existsByNameAndDistrict(String name, DistrictEntity district);

    VillageEntity findByNameAndDistrict(String village, DistrictEntity parentDistrict);

    Iterable<VillageEntity> findByDistrict(DistrictEntity district);
}
