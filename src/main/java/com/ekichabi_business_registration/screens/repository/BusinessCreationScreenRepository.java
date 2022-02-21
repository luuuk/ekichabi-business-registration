package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.screens.stereotype.Screen;

import java.util.Locale.Category;

import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.entity.CategoryEntity;
import com.ekichabi_business_registration.screens.stereotype.InputScreen;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessCreationScreenRepository {
  private final BusinessService businessService;
  private final ErrorScreenRepository errorScreenRepository;
  private final SuccessScreenRepository successScreenRepository;

  @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
    private WelcomeScreenRepository welcomeScreenRepository;

  public Screen getBusinessCreationScreen() {
    return new BusinessCreationScreen();
  }

  private class BusinessCreationScreen extends InputScreen {
    private final String name;
    private final String phone;
    private final String district;

    BusinessCreationScreen() {
      super();
      this.name = null;
      this.phone = null;
      this.district = null;
      line("Enter business name");
    }

    BusinessCreationScreen(String name) {
      super();
      this.name = name;
      this.phone = null;
      this.district = null;
      line("Enter business phone number");
    }

    BusinessCreationScreen(String name, String phone) {
      super();
      this.name = name;
      this.phone = phone;
      this.district = null;
      line("Enter district name");
    }

    BusinessCreationScreen(String name, String phone, String district) {
      super();
      this.name = name;
      this.phone = phone;
      this.district = district;
      line("Enter village name");
    }

    @Override
    public Screen getNextScreen(String input) {
      if (this.name == null) {
        return new BusinessCreationScreen(input);
      } else if (this.phone == null) {
        return new BusinessCreationScreen(this.name, input);
      } else if (this.district == null) {
        return new BusinessCreationScreen(this.name, this.phone, input);
      } else {
        return new CategorySelectionScreen(this.name, this.phone, this.district, input, 1);
      }
    }
  }

  private class CategorySelectionScreen extends Screen {
    private final StringBuilder sb = new StringBuilder();
    private final String name;
    private final String phone;
    private final String district;
    private final String village;

    CategorySelectionScreen(String name, String phone, String district, String village, int page) {
      super(true);
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      if (page == 1) {
        line("Select category");
        line("1. Agri Processing");
        line("2. Financial Services");
        line("3. Hiring and Labor");
        line("4. Merchant/Retail");
        line("9. Next Page");
      } else {
        line("5. Non-Agri Services");
        line("6. Repairs");
        line("7. Trading and Wholesale");
        line("8. Transport");
        line("0. Previous Page");
      }
    }

    @Override
    public Screen doAction(char c) {
      if (c == '*') {
        CategoryEntity categoryEntity;
        switch (sb.charAt(0)) {
          case '1':
            categoryEntity = CategoryEntity.builder().name("Agri Processing").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '2':
            categoryEntity = CategoryEntity.builder().name("Financial Services").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '3':
            categoryEntity = CategoryEntity.builder().name("Hiring and Labor").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '4':
            categoryEntity = CategoryEntity.builder().name("Merchant/Retail").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '5':
            categoryEntity = CategoryEntity.builder().name("Non-Agri Services").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '6':
            categoryEntity = CategoryEntity.builder().name("Repairs").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '7':
            categoryEntity = CategoryEntity.builder().name("Trading and Wholesale").build();
            return new CreateBusinessConfirmationScreen(name, phone, district, village, categoryEntity);
          case '8':
            categoryEntity = CategoryEntity.builder().name("Transport").build();
          case '9': return new CategorySelectionScreen(name, phone, district, village, 0);
          case '0': return new CategorySelectionScreen(name, phone, district, village, 1);
          default:
            return this;
        }
      } else {
        sb.append(c);
        return this;
      }
    }
  }

  private class CreateBusinessConfirmationScreen extends Screen {
    private final StringBuilder sb = new StringBuilder();
    private final String name;
    private final String phone;
    private final String district;
    private final String village;
    private final CategoryEntity category;

    CreateBusinessConfirmationScreen(String name, String phone, String district, String village, CategoryEntity category) {
      super(true);
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      this.category = category;
      line("Add additional information");
      line("1. Add subcategory");
      line("2. Add coordinates");
      line("3. Confirm creation");
      line("4. Cancel creation");
    }

    @Override
    public Screen doAction(char c) {
      if (c == '*') {
        switch (sb.charAt(0)) {
          case '1': 
            // TODO: finish this
            return Screen.conScreen().line("This workflow is unfinished");
          case '2': 
            // TODO: finish this
            return Screen.conScreen().line("This workflow is unfinished");
          case '3': 
            // TODO: actually add the entity
            return successScreenRepository.getSuccessScreen("Business creation successful");
          case '4': return welcomeScreenRepository.getSignedInWelcomScreen();
          default:
            return this;
        }
      } else {
        sb.append(c);
        return this;
      }
    }
  }
  
}
