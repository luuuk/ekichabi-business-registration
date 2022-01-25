package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    private final BusinessRepository repository;


    public BusinessService(
            BusinessRepository repository) {
        this.repository = repository;
    }

    public List<BusinessEntity> findAllBusinesses() {
        return (List<BusinessEntity>) repository.findAll();
    }

}
