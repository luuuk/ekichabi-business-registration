package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {
    AccountEntity findByName(String name);
}
