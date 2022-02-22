package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.PhoneNumberEntity;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumberEntity, Integer> {
}