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
import com.ekichabi_business_registration.util.exceptions.InvalidCreationException;
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
    private static final int PHONE_NUMBER_LENGTH = 9;
    private static final int PHONE_NUMBERS_V1_COUNT = 3;
    private static final String COORDINATE_REGEX =
            "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|"
                    + "((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

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

    public BusinessEntity findBusinessById(Long id) {
        return businessRepository.findById(id).get();
    }

    public List<BusinessEntity> findAllBusinesses() {
        List<BusinessEntity> businessEntities = new ArrayList<>();
        businessRepository.findAll().forEach(businessEntities::add);
        return businessEntities;
    }

    //TODO Luke complete this before demo on 2/23
    public BusinessEntity createBusiness(BusinessEntity businessEntity)
            throws InvalidCreationException {

        // If business has no name throw exception
        if (businessEntity.getName() == null) {
            throw new InvalidCreationException();
        }

        // If business has no category or has a category not in the DB, throw exception
        if (businessEntity.getCategory() == null
                || !categoryRepository.existsByName(businessEntity.getCategory().getName())) {
            throw new InvalidCreationException();
        }

        // If business has a subcategory not associated with its category,
        // write new subcategory to db
        for (SubcategoryEntity subcategory : businessEntity.getSubcategories()) {
            if (!subcategoryRepository.existsByNameAndCategory(subcategory.getName(),
                    subcategory.getCategory())) {
                subcategoryRepository.save(subcategory);
            }
        }

        // If business has no district, throw exception
        if (businessEntity.getSubvillage().getVillage().getDistrict() == null) {
            throw new InvalidCreationException();
        }

        // If business has no village, throw exception
        if (businessEntity.getSubvillage().getVillage() == null) {
            throw new InvalidCreationException();
        }

        // If village not yet in DB, add mapping to district
        if (!villageRepository.existsByNameAndDistrict(
                businessEntity.getSubvillage().getVillage().getName(),
                businessEntity.getSubvillage().getVillage().getDistrict())) {
            villageRepository.save(businessEntity.getSubvillage().getVillage());
        }

        // If subvillage not yet in DB, add mapping to village
        if (!subvillageRepository.existsByNameAndVillage(businessEntity.getSubvillage().getName(),
                businessEntity.getSubvillage().getVillage())) {
            subvillageRepository.save(businessEntity.getSubvillage());
        }

        // If business has no owners, throw exception
        if (businessEntity.getOwners().isEmpty()) {
            throw new InvalidCreationException();
        }

        // If business phone numbers are not of right format, throw exception
        for (String phoneNumber : businessEntity.getPhoneNumbers()) {
            if (phoneNumber.length() != PHONE_NUMBER_LENGTH) {
                throw new InvalidCreationException();
            }
            // TODO check phone number regex
        }

        //TODO check coordinate regex

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
