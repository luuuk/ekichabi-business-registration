package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.db.entity.BusinessEntity;

import java.util.ArrayList;
import java.util.List;

import com.ekichabi_business_registration.db.entity.*;
import com.ekichabi_business_registration.db.repository.*;

import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.screens.stereotype.SimpleScreen;
import com.ekichabi_business_registration.screens.stereotype.InputScreen;
import com.ekichabi_business_registration.screens.stereotype.PaginationScreen;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.sql.Update;

@Component
@RequiredArgsConstructor
public class UpdateBusinessScreenRepository {
  private final ErrorScreenRepository errorScreenRepository;
  private final SuccessScreenRepository successScreenRepository;
  private final CategoryRepository categoryRepository;
  private final SubcategoryRepository subcategoryRepository;
  private final DistrictRepository districtRepository;
  private final VillageRepository villageRepository;

  @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
  private WelcomeScreenRepository welcomeScreenRepository;

  // For welcome screen repository
  public Screen getUpdateBusinessesScreen(AccountEntity accountEntity) {
    return getSelectBusinessScreen(accountEntity);
  }

  // For success screen repository
  public Screen getUpdateBusinessScreen(BusinessEntity be, AccountEntity acc) {
    return new UpdateBusinessScreen(be, acc);
  } 

  private Screen getSelectBusinessScreen(AccountEntity accountEntity) {
    List<BusinessEntity> businesses = new ArrayList<>();
    List<String> businessNames = new ArrayList<>();
    for (val business : accountEntity.getOwnedBusinesses()) {
      businesses.add(business);
      businessNames.add(business.getName());
    }
    return new SelectBusinessScreen(businesses, businessNames, accountEntity);
  }

  private class SelectBusinessScreen extends PaginationScreen {
    private final List<BusinessEntity> businesses;
    private final AccountEntity acc;

    SelectBusinessScreen(List<BusinessEntity> businesses, List<String> businessNames,
                          AccountEntity acc) {
      super(businessNames, "Select business to update");
      this.businesses = businesses;
      this.acc = acc;
    }

    @Override
    protected Screen selected(int i) {
      return new UpdateBusinessScreen(businesses.get(i), this.acc);
    }
  }

  private class UpdateBusinessScreen extends SimpleScreen {
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdateBusinessScreen(BusinessEntity be, AccountEntity acc) {
      super(true);
      this.be = be;
      this.acc = acc;
      line("Update...");
      line("1. Name");
      line("2. Phone");
      line("3. Category");
      line("4. Subcategory");
      line("5. District");
      line("6. Village");
      line("7. Subvillage");
      line("8. Coordiantes");
      line("99. Back");

      addAction(s -> {
        switch (s) {
          case "1":
            return new UpdateNameScreen(be, acc);
          case "2":
            return new UpdatePhoneScreen(be, acc);
          case "3":
            return getUpdateCategoryScreen(be, acc);
          case "4":
            return getUpdateSubcategoryScreen(be, be.getCategory(), acc);
          case "5":
            return getUpdateDistrictScreen(be, acc);
          case "6":
            return getUpdateVillageScreen(be.getSubvillage().getVillage().getDistrict(), be, acc);
          case "7":
            return new UpdateSubvillageScreen(be, acc);
          case "8":
            return new UpdateCoordinatesScreen(be, acc);
          case "99":
            return getSelectBusinessScreen(acc);
          default: 
            return this;
        }
      });

    }
  }

  private Screen getUpdateCategoryScreen(BusinessEntity be, AccountEntity acc) {
    List<CategoryEntity> categories = new ArrayList<>();
    List<String> categoryNames = new ArrayList<>();
    for (val category : categoryRepository.findAll()) {
        categories.add(category);
        categoryNames.add(category.getName());
    }
    return new UpdateCategoryScreen(categories, categoryNames, businessEntity);
  }

  private class UpdateCategoryScreen extends PaginationScreen {
    private final BusinessEntity be;
    private final AccountEntity acc;
    private final List<CategoryEntity> cats;

    UpdateCategoryScreen(List<CategoryEntity> cats, List<String> catNames,
                         BusinessEntity be, AccountEntity acc) {
      super(catNames, "Select new category");
      this.be = be;
      this.acc = acc;
      this.cats = cats;
    }

    @Override
    protected Screen selected(int i) {
      // TODO: set new category value
      return getUpdateSubcategoryScreen(be, cats.get(i), acc);
    }
  }

  private class UpdateNameScreen extends InputScreen {
    private final BusinessEntity be; 
    private final AccountEntity acc;

    UpdateNameScreen(BusinessEntity be, AccountEntity acc) {
      super();
      this.be = be;
      this.acc = acc;
      line("Enter new business name");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return successScreenRepository.getBusinessUpdateSuccessScreen("Name updated",
              acc, be);
    }
  }

  private class UpdatePhoneScreen extends InputScreen {
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdatePhoneScreen(BusinessEntity be, AccountEntity acc) {
      super();
      this.be = be;
      this.acc = acc;
      line("Enter new phone number");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return successScreenRepository.getBusinessUpdateSuccessScreen("Phone number updated",
              acc, be);
    }
  }

  private Screen getUpdateDistrictScreen(BusinessEntity be, AccountEntity acc) {
    List<DistrictEntity> districts = new ArrayList<>();
    List<String> districtNames = new ArrayList<>();
    for (val district : districtRepository.findAll()) {
        districts.add(district);
        districtNames.add(district.getName());
    }
    return new UpdateDistrictScreen(districts, districtNames, be, acc);
  }

  private class UpdateDistrictScreen extends PaginationScreen {
    private final List<DistrictEntity> districts;
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdateDistrictScreen(List<DistrictEntity> districts, List<String> districtNames,
                                 BusinessEntity be, AccountEntity acc) {
      super(districtNames, "Select new district");
      this.districts = districts;
      this.be = be;
      this.acc = acc;
    }

    @Override
    protected Screen selected(int i) {
      // TODO: update district in be
      return getUpdateVillageScreen(districts.get(i), be, acc);
    }
  }

  private Screen getUpdateVillageScreen(DistrictEntity district, BusinessEntity be,
                                        AccountEntity acc) {
    List<VillageEntity> villages = new ArrayList<>();
    List<String> villageNames = new ArrayList<>();
    for (val village : villageRepository.findByDistrict(district)) {
        villages.add(village);
        villageNames.add(village.getName());
    }
    return new UpdateVillageScreen(villages, villageNames, be, acc);
  }

  private class UpdateVillageScreen extends PaginationScreen {
    private final List<VillageEntity> villages;
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdateVillageScreen(List<VillageEntity> villages, List<String> villageNames,
                        BusinessEntity be, AccountEntity acc) {
      super(villageNames, "Select new village");
      this.villages = villages;
      this.be = be;
      this.acc = acc;
    }

    @Override
    protected Screen selected(int i) {
      // TODO: change village in the business entity
      return successScreenRepository.getBusinessUpdateSuccessScreen("Geo info updated",
              acc, be);
    }
  }

  private class UpdateSubvillageScreen extends InputScreen {
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdateSubvillageScreen(BusinessEntity be, AccountEntity acc) {
      super();
      this.be = be;
      this.acc = acc;
      line("Enter new subvillage");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return successScreenRepository.getBusinessUpdateSuccessScreen("Business subvillage updated",
              acc, be);
    }
  }

  private class UpdateCoordinatesScreen extends InputScreen {
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdateCoordinatesScreen(BusinessEntity be, AccountEntity acc) {
      super();
      this.be = be;
      this.acc = acc;
      line("Enter new business name");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      be.setCoordinates(s);
      return successScreenRepository.getBusinessUpdateSuccessScreen("Business coordinates updated",
              acc, be);
    }
  }

  private Screen getUpdateSubcategoryScreen(BusinessEntity be, CategoryEntity ce, 
                                            AccountEntity acc) {
    // TODO: implement
    List<SubcategoryEntity> subcategories = new ArrayList<>();
    List<String> subcategoryNames = new ArrayList<>();
    for (val subcategory : subcategoryRepository.findByCategory(ce)) {
      subcategories.add(subcategory);
      subcategoryNames.add(subcategory.getName());
    }
    return new UpdateSubcategoryScreen(subcategories, subcategoryNames, be, acc);
  }

  private class UpdateSubcategoryScreen extends PaginationScreen {
    private final List<SubcategoryEntity> subcats;
    private final BusinessEntity be;
    private final AccountEntity acc;

    UpdateSubcategoryScreen(List<SubcategoryEntity> subcats, 
        List<String> subCatNames, BusinessEntity be, AccountEntity acc) {
      super(subCatNames, "Select new subcategory");
      this.be = be;
      this.acc = acc;
      this.subcats = subcats;
    }

    @Override
    protected Screen selected(int i) {
      // TODO: update business entity
      return successScreenRepository.getBusinessUpdateSuccessScreen("Subcategory updated",
              acc, be);
    }
  }
}
