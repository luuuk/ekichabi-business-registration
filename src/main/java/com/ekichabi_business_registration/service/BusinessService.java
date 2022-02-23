package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.DistrictEntity;
import com.ekichabi_business_registration.db.entity.SubcategoryEntity;
import com.ekichabi_business_registration.db.entity.SubvillageEntity;
import com.ekichabi_business_registration.db.entity.VillageEntity;
import com.ekichabi_business_registration.db.repository.BusinessRepository;
import com.ekichabi_business_registration.db.repository.CategoryRepository;
import com.ekichabi_business_registration.db.repository.DistrictRepository;
import com.ekichabi_business_registration.db.repository.SubcategoryRepository;
import com.ekichabi_business_registration.db.repository.SubvillageRepository;
import com.ekichabi_business_registration.db.repository.VillageRepository;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ekichabi_business_registration.service.CategoryService.SUBSECTOR_V1_COUNT;

@Service
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final DistrictRepository districtRepository;
    private final VillageRepository villageRepository;
    private final SubvillageRepository subvillageRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final AccountEntity v1AdminAccount =
            AccountEntity.builder()
                    .name("V1_ADMIN")
                    .password("V1_ADMIN")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
    private static final int PHONE_NUMBERS_V1_COUNT = 3;

    public BusinessEntity findBusinessById(Long id) {
        return businessRepository.findById(id).get();
    }

    public List<BusinessEntity> findAllBusinesses() {
        List<BusinessEntity> businessEntities = new ArrayList<>();
        businessRepository.findAll().forEach(businessEntities::add);
        return businessEntities;
    }

    public BusinessEntity createBusiness(BusinessEntity businessEntity)
            throws InvalidCreationException {

        // If business has no name throw exception
        if (businessEntity.getName() == null) {
            throw new InvalidCreationException();
        }

        //TODO Luke add more validation steps to this method
        return businessRepository.save(businessEntity);
    }

    /**
     * Creates businesses from V1 Census data
     */
    public int createBusinesses() {
        int created = 0;
        try {
            CSVReaderHeaderAware csvReaderHeaderAware = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = csvReaderHeaderAware.readMap()) != null) {

                // Confirm this is a new business with all new relations
                DistrictEntity district =
                        districtRepository.findByName(v1Entity.get("district"));
                VillageEntity village =
                        villageRepository.findByNameAndDistrict(v1Entity.get("kijiji"),
                                district);
                SubvillageEntity subvillage =
                        subvillageRepository.findByNameAndVillage(v1Entity.get("subvillage"),
                                village);

                CategoryEntity category =
                        categoryRepository.findByName(v1Entity.get("sector"));

                List<SubcategoryEntity> subcategories = new ArrayList<>();

                for (int i = 1; i <= SUBSECTOR_V1_COUNT; i++) {
                    if (!v1Entity.get("subsector_eng_" + i).isEmpty()) {

                        subcategories.add(subcategoryRepository.findByNameAndCategory(
                                v1Entity.get("subsector_eng_" + i), category));
                    }
                }

                List<String> phoneNumbers = new ArrayList<>();

                for (int i = 1; i <= PHONE_NUMBERS_V1_COUNT; i++) {
                    if (!v1Entity.get("mobile_number_" + i).isEmpty()) {
                        phoneNumbers.add(v1Entity.get("mobile_number_" + i));
                    }
                }

                //TODO check business uniqueness on subcategories as well
                //TODO current limitation is that hibernate (I think) caps insert transactions at
                // 500 per request? Need to insert more than that
                if (!businessRepository.existsByCategoryAndSubvillage(category, subvillage)) {
                    BusinessEntity entity =
                            BusinessEntity.builder()
                                    .name(v1Entity.get("firm_name"))
                                    .subvillage(subvillage)
                                    .subcategories(subcategories)
                                    .category(category)
                                    .owner(v1AdminAccount)
                                    .phoneNumbers(phoneNumbers)
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build();
                    businessRepository.save(entity);
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
}
