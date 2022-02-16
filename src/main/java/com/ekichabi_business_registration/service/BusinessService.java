package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository repository;

    public BusinessEntity findBusinessById(Long id) {
        return repository.findById(id).get();
    }

    public List<BusinessEntity> findAllBusinesses() {
        List<BusinessEntity> businessEntities = new ArrayList<>();
        repository.findAll().forEach(businessEntities::add);
        return businessEntities;
    }

    public BusinessEntity createBusiness(BusinessEntity businessEntity)
            throws InvalidCreationException {

        // If business has no name throw exception
        if (businessEntity.getName() == null) {
            throw new InvalidCreationException();
        }

        //TODO Luke add more validation steps to this method
        return repository.save(businessEntity);
    }
}
