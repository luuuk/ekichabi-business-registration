package com.ekichabi_business_registration.screens;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorScreenRepository {
    @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
    private WelcomeScreenRepository welcomeScreenRepository;

    public Screen getErrorScreen(String reason) {
        return new ErrorScreen(reason);
    }

    private class ErrorScreen extends Screen {
        @Getter
        private final String reason;
        private int count = 0;

        public ErrorScreen(String reason) {
            super(true);
            this.reason = reason;
            line("ERROR");
            line(reason);
            line("99. Back");
        }

        @Override
        public Transit doAction(char c) {
            if (c == '9') {
                count++;
                if (count == 2) {
                    return new PureTransit(welcomeScreenRepository.getWelcomeScreen());
                }
            }
            return new PureTransit(this);
        }
    }
}
