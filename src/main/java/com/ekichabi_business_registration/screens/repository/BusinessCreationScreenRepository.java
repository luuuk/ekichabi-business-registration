package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.db.entity.*;
import com.ekichabi_business_registration.db.repository.CategoryRepository;
import com.ekichabi_business_registration.db.repository.DistrictRepository;
import com.ekichabi_business_registration.db.repository.SubcategoryRepository;
import com.ekichabi_business_registration.db.repository.VillageRepository;
import com.ekichabi_business_registration.screens.stereotype.PaginationScreen;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.screens.stereotype.SimpleScreen;

import java.util.ArrayList;
import java.util.List;

import com.ekichabi_business_registration.screens.stereotype.InputScreen;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class BusinessCreationScreenRepository {
    private final SuccessScreenRepository successScreenRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final DistrictRepository districtRepository;
    private final VillageRepository villageRepository;

    @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
    private WelcomeScreenRepository welcomeScreenRepository;

    public Screen getBusinessCreationScreen(AccountEntity accountEntity) {
        return new BusinessCreationScreen(accountEntity);
    }

    private class BusinessCreationScreen extends InputScreen {
        private final AccountEntity accountEntity;
        private final BusinessEntity businessEntity;

        BusinessCreationScreen(AccountEntity accountEntity) {
            super();
            this.accountEntity = accountEntity;
            this.businessEntity = BusinessEntity.builder().build();
            line("Enter business name");
        }

        BusinessCreationScreen(AccountEntity accountEntity, String name) {
            super();
            this.accountEntity = accountEntity;
            businessEntity = BusinessEntity.builder().name(name).build();
            line("Enter business phone number");
        }

        @Override
        public Screen getNextScreen(String input) {
            if (businessEntity.getName() == null) {
                return new BusinessCreationScreen(accountEntity, input);
            } else {
                // List.of(...) creates immutable list, which is not what we want.
                businessEntity.setPhoneNumbers(new ArrayList<>(List.of(input)));
                businessEntity.setOwners(new ArrayList<>(List.of(accountEntity)));
                return getSelectCategoryScreen(businessEntity);
            }
        }
    }

    private Screen getSelectCategoryScreen(BusinessEntity businessEntity) {
        List<CategoryEntity> categories = new ArrayList<>();
        List<String> categoryNames = new ArrayList<>();
        for (val category : categoryRepository.findAll()) {
            categories.add(category);
            categoryNames.add(category.getName());
        }
        return new SelectCategoryScreen(categories, categoryNames, businessEntity);
    }

    private class SelectCategoryScreen extends PaginationScreen {
        private final List<CategoryEntity> categories;
        private final BusinessEntity businessEntity;

        SelectCategoryScreen(List<CategoryEntity> categories, List<String> categoryNames,
                                    BusinessEntity businessEntity) {
            super(categoryNames, "Select category");

            this.categories = categories;
            this.businessEntity = businessEntity;
        }

        @Override
        protected Screen selected(int i) {
            val category = categories.get(i);
            businessEntity.setCategory(category);
            return getSelectDistrictScreen(businessEntity);
        }
    }

    private Screen getSelectDistrictScreen(BusinessEntity businessEntity) {
        List<DistrictEntity> districts = new ArrayList<>();
        List<String> districtNames = new ArrayList<>();
        for (val district : districtRepository.findAll()) {
            districts.add(district);
            districtNames.add(district.getName());
        }
        return new SelectDistrictScreen(districts, districtNames, businessEntity);
    }

    private class SelectDistrictScreen extends PaginationScreen {
        private final List<DistrictEntity> districts;
        private final BusinessEntity businessEntity;

        SelectDistrictScreen(List<DistrictEntity> districts, List<String> districtNames,
                                    BusinessEntity businessEntity) {
            super(districtNames, "Select district");
            this.districts = districts;
            this.businessEntity = businessEntity;
        }

        @Override
        protected Screen selected(int i) {
            val district = districts.get(i);
            return getSelectVillageScreen(district, businessEntity);
        }
    }

    private Screen getSelectVillageScreen(DistrictEntity district, BusinessEntity businessEntity) {
        List<VillageEntity> villages = new ArrayList<>();
        List<String> villageNames = new ArrayList<>();
        for (val village : villageRepository.findByDistrict(district)) {
            villages.add(village);
            villageNames.add(village.getName());
        }
        return new SelectVillageScreen(villages, villageNames, businessEntity);
    }

    private class SelectVillageScreen extends PaginationScreen {
        private final List<VillageEntity> villages;
        private final BusinessEntity businessEntity;

        SelectVillageScreen(List<VillageEntity> villages, List<String> villageNames,
                                   BusinessEntity businessEntity) {
            super(villageNames, "Select village");
            this.villages = villages;
            this.businessEntity = businessEntity;
        }

        @Override
        protected Screen selected(int i) {
            val village = villages.get(i);
            return new CreateBusinessConfirmationScreen(businessEntity, village);
        }
    }

    private class CreateBusinessConfirmationScreen extends SimpleScreen {
        CreateBusinessConfirmationScreen(BusinessEntity business, VillageEntity village) {
            super(true);

            line("Add additional information");
            line("1. Add subcategory");
            line("2. Add subvillage");
            line("3. Add coordinates");
            line("4. Confirm creation");
            line("5. Cancel creation");


            addAction(s -> {
                switch (s) {
                    case "1":
                        return getSelectSubcategoryScreen(business.getCategory(), business,
                                village);
                    case "2":
                        return new EnterSubvillageScreen(business, village);
                    case "3":
                        return new EnterCoordinateScreen(business, village);
                    case "4":
                        // TODO: actually add the entity
                        assert business.getOwners().size() == 1;
                        return successScreenRepository.getSignedInSuccessScreen("Business creation "
                                + "successful", business.getOwners().get(0));
                    case "5":
                        assert business.getOwners().size() == 1;
                        return welcomeScreenRepository.getSignedInWelcomeScreen(
                                business.getOwners().get(0));
                    default:
                        return this;
                }
            });
        }
    }

    private Screen getSelectSubcategoryScreen(CategoryEntity category, BusinessEntity business,
                                              VillageEntity village) {
        List<SubcategoryEntity> subcategories = new ArrayList<>();
        List<String> subcategoryNames = new ArrayList<>();
        for (val subcategory : subcategoryRepository.findByCategory(category)) {
            subcategories.add(subcategory);
            subcategoryNames.add(subcategory.getName());
        }
        return new SelectSubcategoryScreen(subcategories, subcategoryNames, business, village);
    }

    private class SelectSubcategoryScreen extends PaginationScreen {

        private final List<SubcategoryEntity> subcategories;
        private final BusinessEntity business;
        private final VillageEntity village;

        SelectSubcategoryScreen(
                List<SubcategoryEntity> subcategories,
                List<String> subcategoryNames, BusinessEntity business, VillageEntity village) {
            super(subcategoryNames, "Select subcategories");
            this.subcategories = subcategories;
            this.business = business;
            this.village = village;
        }

        @Override
        protected Screen selected(int i) {
            val subcategory = subcategories.get(i);
            business.getSubcategories().add(subcategory);
            return new CreateBusinessConfirmationScreen(business, village);
        }
    }

    private class EnterSubvillageScreen extends InputScreen {
        private final BusinessEntity business;
        private final VillageEntity village;

        EnterSubvillageScreen(BusinessEntity business, VillageEntity village) {
            this.business = business;
            this.village = village;
            line("Enter subvillage name");
        }

        @Override
        public Screen getNextScreen(String s) {
            val subvillage = SubvillageEntity.builder().name(s).village(village).build();
            business.setSubvillage(subvillage);
            return new CreateBusinessConfirmationScreen(business, village);
        }
    }

    private class EnterCoordinateScreen extends InputScreen {

        private final BusinessEntity business;
        private final VillageEntity village;

        EnterCoordinateScreen(BusinessEntity business, VillageEntity village) {
            this.business = business;
            this.village = village;
            line("Enter coordinates");
        }

        @Override
        public Screen getNextScreen(String s) {
            business.setCoordinates(s);
            return new CreateBusinessConfirmationScreen(business, village);
        }
    }

}
