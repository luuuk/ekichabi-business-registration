package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.db.entity.BusinessEntity;

import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.screens.stereotype.SimpleScreen;
import com.ekichabi_business_registration.screens.stereotype.InputScreen;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.sql.Update;

@Component
@RequiredArgsConstructor
public class UpdateBusinessScreenRepository {
  private final BusinessService businessService;
  private final ErrorScreenRepository errorScreenRepository;
  private final SuccessScreenRepository successScreenRepository;

  @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
  private WelcomeScreenRepository welcomeScreenRepository;

  public Screen getUpdateBusinessScreen() {
    return new SelectBusinessScreen();
  }

  private class SelectBusinessScreen extends SimpleScreen {
    

    SelectBusinessScreen() {
      super(true);
      line("1. <temp business>");
      line("2. <temp buisness>");
      line("99. Back");
      addAction(s -> {
        switch (s) {
          case "1": 
          case "2":
          case "99": return welcomeScreenRepository.getSignedInWelcomeScreen();
        }
      });
    }
  }

  private class UpdateBusinessScreen extends SimpleScreen {
    private final BusinessEntity be;

    UpdateBusinessScreen(BusinessEntity be) {
      super(true);
      this.be = be;
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
            return new UpdateNameScreen(be);
          case "2":
            return new UpdatePhoneScreen(be);
          case "3":
            return new UpdateCategoryScreen(be);
          case "4":
            return new UpdateSubcategoriesScreen();
          case "5":
            return new UpdateDistrictScreen(be);
          case "6":
            return new UpdateVillageScreen(be);
          case "7":
            return new UpdateSubvillageScreen(be);
          case "8":
            return new UpdateCoordinatesScreen(be);
          case "99":
            return new SelectBusinessScreen();
          default: 
            return this;
        }
      });

    }
  }

  private class UpdateCategoryScreen extends SimpleScreen {
    private final BusinessEntity be;

    UpdateCategoryScreen(BusinessEntity be) {
      super(true);
      this.be = be;
      line("Select category");
      line("1. Agri Processing");
      line("2. Financial Services");
      line("3. Hiring and Labor");
      line("4. Merchant/Retail");
      line("5. Non-Agri Services");
      line("6. Repairs");
      line("7. Trading and Wholesale");
      line("8. Transport");
      line("99. Back");

      // Interesting dependency of business updating here, if we change the category
      // we should clear the old subcategory selections
      addAction(s -> {
        switch (s) {
          case "1":
            // TODO: add call to update business entity
            break;
          case "2":
            // TODO: add call to update business entity
            break;
          case "3":
            // TODO: add call to update business entity
            break;
          case "4":
            // TODO: add call to update business entity
            break;
          case "5":
            // TODO: add call to update business entity
            break;
          case "6":
            // TODO: add call to update business entity
            break;
          case "7":
            // TODO: add call to update business entity
            break;
          case "8":
            // TODO: add call to update business entity
            break;
          case "99":
            break;
          default:
            break;
        }
        return new UpdateBusinessScreen(be);
      });
    }
  }

  private class UpdateNameScreen extends InputScreen {
    private final BusinessEntity be; 

    UpdateNameScreen(BusinessEntity be) {
      super();
      this.be = be;
      line("Enter new business name");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return new UpdateBusinessScreen(be);
    }
  }

  private class UpdatePhoneScreen extends InputScreen {
    private final BusinessEntity be; 

    UpdatePhoneScreen(BusinessEntity be) {
      super();
      this.be = be;
      line("Enter new phone number");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return new UpdateBusinessScreen(be);
    }
  }

  // TODO: change this to be a selection menu
  private class UpdateDistrictScreen extends InputScreen {
    private final BusinessEntity be; 

    UpdateDistrictScreen(BusinessEntity be) {
      super();
      this.be = be;
      line("Enter new district");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return new UpdateBusinessScreen(be);
    }
  }

  // TODO: change this to be a selection menu
  private class UpdateVillageScreen extends InputScreen {
    private final BusinessEntity be; 

    UpdateVillageScreen(BusinessEntity be) {
      super();
      this.be = be;
      line("Enter new village");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return new UpdateBusinessScreen(be);
    }
  }

  private class UpdateSubvillageScreen extends InputScreen {
    private final BusinessEntity be; 

    UpdateSubvillageScreen(BusinessEntity be) {
      super();
      this.be = be;
      line("Enter new subvillage");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return new UpdateBusinessScreen(be);
    }
  }

  private class UpdateCoordinatesScreen extends InputScreen {
    private final BusinessEntity be; 

    UpdateCoordinatesScreen(BusinessEntity be) {
      super();
      this.be = be;
      line("Enter new business name");
    }

    @Override
    public Screen getNextScreen(String s) {
      // TODO: add call to update business entity
      return new UpdateBusinessScreen(be);
    }
  }

  private class UpdateSubcategoriesScreen extends SimpleScreen {

    UpdateSubcategoriesScreen() {
      super(true);
      
    }
  }
}
