package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BusinessRepository extends CrudRepository<BusinessEntity, Long> {
}
