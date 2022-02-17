package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.screens.stereotype.Screen;
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

    public class SuccessScreen extends Screen {
        @Getter
        private final String reason;
        private int count = 0;

        public SuccessScreen(String reason) {
            super(true);
            this.reason = reason;
            line("Success");
            line(reason);
            line("99. Back");
        }

        @Override
        public Screen doAction(char c) {
            if (c == '9') {
                count++;
                if (count == 2) {
                    return welcomeScreenRepository.getWelcomeScreen();
                }
            }
            return this;
        }
    }
}
