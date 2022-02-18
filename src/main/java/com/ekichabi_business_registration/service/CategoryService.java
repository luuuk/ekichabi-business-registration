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

    public int createCategories() {
        int createdCategories = 0;
        try {
            CSVReaderHeaderAware CSVReader = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = CSVReader.readMap()) != null) {
                if (!categoryRepository.existsByName(v1Entity.get("sector"))) {
                    CategoryEntity newCat = new CategoryEntity(v1Entity.get("sector"));
                    categoryRepository.save(newCat);
                    createdCategories++;
                }
            }
            CSVReader.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return createdCategories;
    }

    public int createSubcategories() {
        int createdSubcategories = 0;
        try {
            CSVReaderHeaderAware CSVReader = new CSVReaderHeaderAware(
                    new FileReader("src/main/resources/static/census_full.csv"));
            Map<String, String> v1Entity;
            while ((v1Entity = CSVReader.readMap()) != null) {
                CategoryEntity parentCategory =
                        categoryRepository.findByName(v1Entity.get("sector"));
                for (int i = 1; i <= 4; i++) {
                    if (!v1Entity.get("subsector_eng_" + i).isEmpty() &&
                            !subcategoryRepository.existsByNameAndCategory(
                                    v1Entity.get("subsector_eng_" + i), parentCategory)) {

                        SubcategoryEntity newSubcategory =
                                new SubcategoryEntity(0, v1Entity.get("subsector_eng_" + i),
                                        parentCategory);
                        subcategoryRepository.save(newSubcategory);
                        createdSubcategories++;
                    }
                }
            }
            CSVReader.close();
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return createdSubcategories;
    }
}
