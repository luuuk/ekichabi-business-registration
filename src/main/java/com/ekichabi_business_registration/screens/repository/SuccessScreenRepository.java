package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.db.entity.AccountEntity;
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

    public Screen getSuccessScreen(String reason) {
        return new SuccessScreen(reason);
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
}
