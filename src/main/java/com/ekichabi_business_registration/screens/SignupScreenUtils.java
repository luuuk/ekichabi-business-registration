package com.ekichabi_business_registration.screens;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.service.AccountService;
import com.ekichabi_business_registration.service.InvalidCreationException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SignupScreenUtils {

    @Component("signupScreen")
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    private static class SignupScreen extends InputScreen {
        @Autowired
        private ApplicationContext context;

        SignupScreen() {
            super();
            line("Sign up - enter username");
            line("Usernames must be unique");
        }

        @Override
        public Screen getNextScreen(String username) {
            return context.getBean(SignupScreenPassword.class, username);
        }
    }

    @Component
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    private static class SignupScreenPassword extends PasswordScreen {
        @Autowired
        private ApplicationContext context;
        private final String username;
        private final String password;

        SignupScreenPassword(String username) {
            super();
            this.username = username;
            this.password = null;
            line("Sign up - enter password");
        }

        SignupScreenPassword(String username, String password) {
            super();
            this.username = username;
            this.password = password;
            line("Sign up - enter password");
        }

        public boolean hasRepeated() {
            return password != null;
        }

        @Override
        public Screen getNextScreen(String suppliedPassword) {
            if (hasRepeated()) {
                if (password.equals(suppliedPassword)) {
                    return context.getBean(SignupConfirmationScreen.class, username, password);
                } else {
                    return context.getBean(ErrorScreen.class, "Password does not match");
                }
            } else {
                return context.getBean(SignupScreenPassword.class, username, suppliedPassword);
            }
        }
    }

    @Component
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    private static class SignupConfirmationScreen extends Screen {
        @Autowired
        private ApplicationContext context;
        @Autowired
        private AccountService accountService;
        private final String username;
        private final String password;


        SignupConfirmationScreen(String username, String password) {
            super(false);
            this.username = username;
            this.password = password;
            line("username: " + username);
            line("password: " + Strings.repeat("*", password.length()));
            line("0. create");
            line("1. cancel");
        }

        @Override
        public Transit doAction(char c) {
            switch (c) {
                case '0':
                    return new Transit(context.getBean(SuccessScreen.class,
                            "Account registration success")) {

                        @Override
                        public Optional<Screen> doRequest() {

                            AccountEntity accountEntity = AccountEntity.builder()
                                    .name(username)
                                    .password(password)
                                    .build();
                            try {
                                accountService.createBusiness(accountEntity);
                            } catch (InvalidCreationException e) {
                                return Optional.of(context.getBean(ErrorScreen.class,
                                        "User creation error"));
                            }
                            return Optional.empty();
                        }
                    };
                case '1':
                    return new PureTransit(context.getBean("welcomeScreen", Screen.class));
                default:
                    return new PureTransit(this);
            }

        }

    }
}
