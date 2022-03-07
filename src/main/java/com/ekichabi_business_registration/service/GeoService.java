package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import com.ekichabi_business_registration.db.repository.DistrictRepository;
import com.ekichabi_business_registration.db.repository.SubvillageRepository;
import com.ekichabi_business_registration.db.repository.VillageRepository;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeoService {
    private final DistrictRepository districtRepository;
    private final VillageRepository villageRepository;
    private final SubvillageRepository subvillageRepository;

    public int createDistricts() {
        int created = 0;
        try {
            CSVReaderHeaderAware csvReaderHeaderAware = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = csvReaderHeaderAware.readMap()) != null) {
                if (!districtRepository.existsByName(v1Entity.get("district"))) {
                    DistrictEntity entity = DistrictEntity.builder()
                            .name(v1Entity.get("district"))
                            .build();
                    districtRepository.save(entity);
                    created++;
                }
            }
            csvReaderHeaderAware.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return created;
    }

    public int createVillages() {
        int created = 0;
        try {
            CSVReaderHeaderAware csvReaderHeaderAware = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = csvReaderHeaderAware.readMap()) != null) {
                DistrictEntity parentDistrict =
                        districtRepository.findByName(v1Entity.get("district"));
                if (!villageRepository.existsByNameAndDistrict(v1Entity.get("kijiji"),
                        parentDistrict)) {
                    VillageEntity entity = VillageEntity.builder()
                            .name(v1Entity.get("kijiji"))
                            .district(parentDistrict)
                            .build();
                    villageRepository.save(entity);
                    created++;
                }
            }
            csvReaderHeaderAware.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return created;
    }

    public int createSubvillages() {
        int created = 0;
        try {
            CSVReaderHeaderAware csvReaderHeaderAware = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = csvReaderHeaderAware.readMap()) != null) {
                DistrictEntity parentDistrict =
                        districtRepository.findByName(v1Entity.get("district"));
                VillageEntity parentVillage =
                        villageRepository.findByNameAndDistrict(v1Entity.get("kijiji"),
                                parentDistrict);
                if (!subvillageRepository.existsByNameAndVillage(v1Entity.get("subvillage"),
                        parentVillage)) {
                    SubvillageEntity entity = SubvillageEntity.builder()
                            .name(v1Entity.get("subvillage"))
                            .village(parentVillage)
                            .build();
                    subvillageRepository.save(entity);
                    created++;
                }
            }
            csvReaderHeaderAware.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return created;
    }

    public List<DistrictEntity> findAllDistricts() {
        List<DistrictEntity> districtEntities = new ArrayList<>();
        districtRepository.findAll().forEach(districtEntities::add);
        return districtEntities;
    }

    public List<VillageEntity> findVillagesByDistrict(String district) {
        List<VillageEntity> entities = new ArrayList<>();
        villageRepository.findByDistrict(districtRepository.findByName(district))
                .forEach(entities::add);
        return entities;
    }

    public List<SubvillageEntity> findSubvillagesByVillage(String village) {
        List<SubvillageEntity> entities = new ArrayList<>();
        subvillageRepository.findByVillage(villageRepository.findByName(village))
                .forEach(entities::add);
        return entities;
    }
}
