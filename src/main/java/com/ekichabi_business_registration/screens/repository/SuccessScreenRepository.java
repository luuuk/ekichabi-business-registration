package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.screens.stereotype.SimpleScreen;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SuccessScreenRepository {

    @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
    private WelcomeScreenRepository welcomeScreenRepository;

    private UpdateBusinessScreenRepository updateBusinessRepo;

    public Screen getSuccessScreen(String reason) {
        return new SuccessScreen(reason);
    }

    public Screen getSignedInSuccessScreen(String reason, AccountEntity accountEntity) {
        return new SignedInSuccessScreen(reason, accountEntity);
    }

    public Screen getBusinessUpdateSuccessScreen(String reason, AccountEntity acc, BusinessEntity be) {
        return new BusinessUpdateSuccessScreen(reason, acc, be);
    }

    public class SuccessScreen extends SimpleScreen {
        @Getter
        private final String reason;

        public SuccessScreen(String reason) {
            super(true);
            this.reason = reason;
            line("Success");
            line(reason);
            line("99. Continue");
            addAction(s -> {
                if (s.equals("99")) {
                    return getNextScreen();
                }
                return null;
            });
        }

        protected Screen getNextScreen() {
            return welcomeScreenRepository.getWelcomeScreen();
        }
    }

    public class SignedInSuccessScreen extends SuccessScreen {

        private final AccountEntity accountEntity;

        public SignedInSuccessScreen(String reason, AccountEntity accountEntity) {
            super(reason);
            this.accountEntity = accountEntity;
        }

        @Override
        protected Screen getNextScreen() {
            return welcomeScreenRepository.getSignedInWelcomeScreen(accountEntity);
        }
    }

    public class BusinessUpdateSuccessScreen extends SuccessScreen {
        private final AccountEntity accountEntity;
        private final BusinessEntity businessEntity;

        public BusinessUpdateSuccessScreen(String reason, AccountEntity acc, BusinessEntity be) {
            super(reason);
            this.accountEntity = acc;
            this.businessEntity = be;
        }

        @Override
        protected Screen getNextScreen() {
            return updateBusinessRepo.getUpdateBusinessScreen(businessEntity, accountEntity);
        }
    }
}
