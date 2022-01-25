package com.ekichabi_business_registration.api;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/business")
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
public class BusinessController {
    private final BusinessService service;
    private final Logger logger = LoggerFactory.getLogger(BusinessController.class);

    public BusinessController(BusinessService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BusinessEntity>> findAll() {
        logger.info("Calling FindAllBusinesses()");
        List<BusinessEntity> businessEntities = service.findAllBusinesses();
        return ResponseEntity.ok().body(businessEntities);
    }

    // TODO implement remaining endpoints here
}
