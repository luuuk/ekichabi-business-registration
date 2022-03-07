package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import com.ekichabi_business_registration.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@Transactional
public class GeoController {
    private final GeoService service;
    private final Logger logger = LoggerFactory.getLogger(GeoController.class);

    @GetMapping("districts")
    public ResponseEntity<List<DistrictEntity>> getDistricts() {
        logger.info("Calling findAllBusinesses()");
        List<DistrictEntity> districtEntities = service.findAllDistricts();
        return ResponseEntity.ok().body(districtEntities);
    }

    @GetMapping("villages/{district}")
    public ResponseEntity<List<VillageEntity>> getVillagesByDistrict(
            @PathVariable String district) {
        logger.info("Calling findVillagesByDistrict()");
        List<VillageEntity> villageEntities = service.findVillagesByDistrict(district);
        return ResponseEntity.ok().body(villageEntities);
    }

    @GetMapping("subvillages/{village}")
    public ResponseEntity<List<SubvillageEntity>> getSubvillagesByVillage(
            @PathVariable String village) {
        logger.info("Calling findSubvillagesByVillage()");
        List<SubvillageEntity> subvillageEntities = service.findSubvillagesByVillage(village);
        return ResponseEntity.ok().body(subvillageEntities);
    }
}
