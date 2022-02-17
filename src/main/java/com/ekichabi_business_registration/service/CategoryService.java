package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.db.repository.CategoryRepository;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public void createCategories() {
        try {
            CSVReaderHeaderAware CSVReader = new CSVReaderHeaderAware(new FileReader("src/main/resources/static/census_full.csv"));
            while(CSVReader.peek().length != 0){
                Map<String, String> v1Entity = CSVReader.readMap();
                if (!repository.existsByName(v1Entity.get("sector"))){
                    CategoryEntity newCat = new CategoryEntity(v1Entity.get("sector"));
                    repository.save(newCat);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
