package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.entity.SubcategoryEntity;
import com.ekichabi_business_registration.db.repository.CategoryRepository;
import com.ekichabi_business_registration.db.repository.SubcategoryRepository;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    protected static final int SUBSECTOR_V1_COUNT = 4;

    public int createCategories() {
        int createdCategories = 0;
        try {
            CSVReaderHeaderAware csvReaderHeaderAware = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = csvReaderHeaderAware.readMap()) != null) {
                if (!categoryRepository.existsByName(v1Entity.get("sector"))) {
                    CategoryEntity newCat = CategoryEntity.builder()
                            .name(v1Entity.get("sector"))
                            .build();
                    categoryRepository.save(newCat);
                    createdCategories++;
                }
            }
            csvReaderHeaderAware.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return createdCategories;
    }

    public int createSubcategories() {
        int createdSubcategories = 0;
        try {
            CSVReaderHeaderAware csvReaderHeaderAware = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = csvReaderHeaderAware.readMap()) != null) {
                CategoryEntity parentCategory =
                        categoryRepository.findByName(v1Entity.get("sector"));
                for (int i = 1; i <= SUBSECTOR_V1_COUNT; i++) {
                    if (!v1Entity.get("subsector_eng_" + i).isEmpty()
                            && !subcategoryRepository.existsByNameAndCategory(
                                    v1Entity.get("subsector_eng_" + i), parentCategory)) {

                        SubcategoryEntity newSubcategory =
                                SubcategoryEntity.builder()
                                        .name(v1Entity.get("subsector_eng_" + i))
                                        .category(parentCategory)
                                        .build();
                        subcategoryRepository.save(newSubcategory);
                        createdSubcategories++;
                    }
                }
            }
            csvReaderHeaderAware.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return createdSubcategories;
    }
}
