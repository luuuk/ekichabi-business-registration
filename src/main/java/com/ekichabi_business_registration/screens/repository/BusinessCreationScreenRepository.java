package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.service.BusinessService;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.screens.stereotype.SimpleScreen;

import java.util.Locale.Category;

import java.util.ArrayList;
import java.util.List;

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
        return new CategorySelectionScreen(this.name, this.phone, this.district, input);
      }
    }
  }

  private class CategorySelectionScreen extends SimpleScreen {
    private final String name;
    private final String phone;
    private final String district;
    private final String village;

    CategorySelectionScreen(String name, String phone, String district, String village) {
      super(true);
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      line("Select category");
      line("1. Agri Processing");
      line("2. Financial Services");
      line("3. Hiring and Labor");
      line("4. Merchant/Retail");
      line("5. Non-Agri Services");
      line("6. Repairs");
      line("7. Trading and Wholesale");
      line("8. Transport");
      // TODO: pagination
      addAction(s -> {
        switch (s) {
          case "1":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Agri Processing", new ArrayList<>(), null);
          case "2":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Financial Services", new ArrayList<>(), null);
          case "3":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Hiring and Labor", new ArrayList<>(), null);
          case "4":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Merchant/Retail", new ArrayList<>(), null);
          case "5":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Non-Agri Services", new ArrayList<>(), null);
          case "6":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Repairs", new ArrayList<>(), null);
          case "7":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Trading and Wholesale", new ArrayList<>(), null);
          case "8":
            return new CreateBusinessConfirmationScreen(name, phone, district, village, 
            "*", "Transport", new ArrayList<>(), null);
          default:
            return this;
        }
      });
    }
  }

  private class CreateBusinessConfirmationScreen extends SimpleScreen {
    private final String name;
    private final String phone;
    private final String district;
    private final String village;
    private final String subvillage;
    private final String category;
    private List<String> subcategories;
    private String coordinates;

    CreateBusinessConfirmationScreen(String name, String phone, 
                                     String district, String village, String subvillage,
                                     String category, List<String> subcats,
                                     String coordinates) {
      super(true);
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      this.subvillage = subvillage;
      this.category = category;
      this.subcategories = subcats;
      this.coordinates = coordinates;
      line("Add additional information");
      line("1. Add subcategory");
      line("2. Add subvillage");
      line("3. Add coordinates");
      line("4. Confirm creation");
      line("5. Cancel creation");
      addAction(s -> {
        switch (s) {
          case "1": 
            if (subcategories.size() < 3){
              return new SelectSubcategoryScreen(this.name, this.phone, this.district, this.village, 
              this.subvillage, this.category, this.subcategories, this.coordinates);
            } else {
              // TODO: fix this screen to actually be informative
              return this;
            }
          case "2": 
            return new EnterSubvillageScreen(this.name, this.phone, this.district, this.village, 
            this.subvillage, this.category, this.subcategories, this.coordinates);
          case "3": 
            return new EnterCoordinateScreen(this.name, this.phone, this.district, this.village, 
            this.subvillage, this.category, this.subcategories, this.coordinates);
          case "4": 
            // TODO: actually add the entity
            return successScreenRepository.getSuccessScreen("Business creation successful");
          case "5": 
            return welcomeScreenRepository.getSignedInWelcomeScreen();
          default:
            return this;
        }
      });
    }
  }

  private class SelectSubcategoryScreen extends SimpleScreen {
    private final String name;
    private final String phone;
    private final String district;
    private final String village;
    private final String subvillage;
    private final String category;
    private String coordinates;
    private List<String> subs;

    SelectSubcategoryScreen(String name, String phone, 
                            String district, String village, String subvillage,
                            String category, List<String> subcats,
                            String coordinates) {
      super(true);
      List<String> subcategories = new ArrayList<>();
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      this.subvillage = subvillage;
      this.category = category;
      this.subs = subcats;
      switch (category) {
        case "Agri Processing":
          subcategories.add("Brewery");
          subcategories.add("Cooking Oil");
          subcategories.add("Honey");
          subcategories.add("Milling");
          subcategories.add("Packaging");
          subcategories.add("Threshing");
          break;
        case "Financial Services":
          subcategories.add("Farmer Cooperative");
          subcategories.add("Formal Lending Institution");
          subcategories.add("Mobile Money");
          subcategories.add("SACCO");
          subcategories.add("VICOBA");
          break;
        case "Hiring and Labor":
          subcategories.add("Agricultural Labor Crew");
          subcategories.add("Labor Crew Manager");
          subcategories.add("Rental of Draft Animals for Plowing");
          subcategories.add("ractor Rental");
          break;
        case "Merchant/Retail":
          // TODO: pagination
          subcategories.add("Construction/Hardware Store");
          subcategories.add("Crops");
          subcategories.add("General Store");
          subcategories.add("Household Goods");
          subcategories.add("Livestock");
          subcategories.add("Market Stall");
          subcategories.add("Mobile Phone Shop");
          subcategories.add("Non-food Goods");
          subcategories.add("Non-market Kiosk/Stall");
          subcategories.add("Sale of Agricultural Inputs");
          subcategories.add("Small Scale Vendor");
          subcategories.add("Wholesale");
          break;
        case "Non-Agri Services":
          subcategories.add("Guest House");
          subcategories.add("Health Clinic");
          subcategories.add("Hotel");
          subcategories.add("Internet/Computer Cafe");
          subcategories.add("Formal Gas Station");
          subcategories.add("Pharmacy");
          subcategories.add("Photocopy and Stationary");
          subcategories.add("Veterinary Services");
          break;
        case "Repairs":
          // TODO: pagination
          subcategories.add("Bicycle Repair");
          subcategories.add("Carpentry/Artisan");
          subcategories.add("Cell Phone Repair");
          subcategories.add("Electrician");
          subcategories.add("Car Repair");
          subcategories.add("Motorbike Repair");
          subcategories.add("Water Pump Repair");
          subcategories.add("Tailor");
          subcategories.add("Tractor Repair");
          subcategories.add("Welder");
          break;
        case "Trading and Wholesale":
          subcategories.add("Crops");
          subcategories.add("Farmgate");
          subcategories.add("Forestry");
          subcategories.add("Livestock");
          subcategories.add("Market Center");
          subcategories.add("Wholesaler");
          break;
        case "Transport":
          subcategories.add("Bicycle");
          subcategories.add("Bus");
          subcategories.add("Car/Truck");
          subcategories.add("Hand/Pushcart");
          subcategories.add("Motorbike");
          subcategories.add("Porter");
          break;
      }
      for (int i = 0; i < subcategories.size(); i++) {
        line(i + 1 + ". " + subcategories.get(i));
      }
      line("99. Back");

      addAction(s -> {
        int val = Integer.parseInt(s);
        if (s.equals("99")) {
          return new CreateBusinessConfirmationScreen(this.name, this.phone, this.district, this.village,
           this.subvillage, this.category, this.subs, this.coordinates);
        } else if (val <= subcategories.size()) {
          subs.add(subcategories.get(val - 1));
          return new CreateBusinessConfirmationScreen(this.name, this.phone, this.district, this.village,
           this.subvillage, this.category, this.subs, this.coordinates);
        } else {
          return this;
        }
      });

    }
  }

  private class EnterSubvillageScreen extends InputScreen {
    private final String name;
    private final String phone;
    private final String district;
    private final String village;
    private final String subvillage;
    private final String category;
    private final List<String> subcategories;
    private final String coordinates;

    EnterSubvillageScreen(String name, String phone, 
                            String district, String village, String subvillage,
                            String category, List<String> subcats,
                            String coordinates) {
      super();
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      this.subvillage = subvillage;
      this.category = category;
      this.subcategories = subcats;
      this.coordinates = coordinates;

      line("Enter subvillage name");
    }

    @Override
    public Screen getNextScreen(String s) {
      return new CreateBusinessConfirmationScreen(name, phone, district, village, s, 
              category, subcategories, coordinates);
    }
  }

  private class EnterCoordinateScreen extends InputScreen {
    private final String name;
    private final String phone;
    private final String district;
    private final String village;
    private final String subvillage;
    private final String category;
    private final List<String> subcategories;
    private final String coordinates;

    EnterCoordinateScreen(String name, String phone, 
                            String district, String village, String subvillage,
                            String category, List<String> subcats,
                            String coordinates) {
      super();
      this.name = name;
      this.phone = phone;
      this.district = district;
      this.village = village;
      this.subvillage = subvillage;
      this.category = category;
      this.subcategories = subcats;
      this.coordinates = coordinates;
      line("Enter coordinates");
    }

    @Override
    public Screen getNextScreen(String s) {
      return new CreateBusinessConfirmationScreen(name, phone, district, village, subvillage, 
              category, subcategories, s);
    }
  }

}
