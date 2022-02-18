package com.ekichabi_business_registration.db.repository;

import com.ekichabi_business_registration.db.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    boolean existsByName(String name);
    CategoryEntity findByName(String name);
}
