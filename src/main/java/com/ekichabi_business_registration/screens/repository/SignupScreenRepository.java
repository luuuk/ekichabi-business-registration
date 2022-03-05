package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.screens.stereotype.InputScreen;
import com.ekichabi_business_registration.screens.stereotype.PasswordScreen;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.service.AccountService;
import com.ekichabi_business_registration.util.exceptions.InvalidCreationException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupScreenRepository {

    private final AccountService accountService;
    private final ErrorScreenRepository errorScreenRepository;
    private final SuccessScreenRepository successScreenRepository;

    @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
    private WelcomeScreenRepository welcomeScreenRepository;

    public Screen getSignupScreen() {
        return new SignupScreen();
    }

    private class SignupScreen extends InputScreen {

        SignupScreen() {
            super();
            line("Sign up - enter username");
            line("Usernames must be unique");
        }

        @Override
        public Screen getNextScreen(String username) {
            return new SignupScreenPassword(username);
        }
    }

    private class SignupScreenPassword extends PasswordScreen {
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
            line("Sign up - confirm password");
        }

        public boolean hasRepeated() {
            return password != null;
        }

        @Override
        public Screen getNextScreen(String suppliedPassword) {
            if (hasRepeated()) {
                if (password.equals(suppliedPassword)) {
                    return new SignupConfirmationScreen(username, password);
                } else {
                    return errorScreenRepository.getErrorScreen("Password does not match");
                }
            } else {
                return new SignupScreenPassword(username, suppliedPassword);
            }
        }
    }

    private class SignupConfirmationScreen extends Screen {
        private final String username;
        private final String password;
        private final StringBuilder sb = new StringBuilder();


        SignupConfirmationScreen(String username, String password) {
            super(true);
            this.username = username;
            this.password = password;
            line("username: " + username);
            line("password: " + Strings.repeat("*", password.length()));
            line("0. create");
            line("1. cancel");
        }

        @Override
        public Screen doAction(char c) {
            if (c == '*') {
                switch (sb.charAt(0)) {
                    case '0':
                        AccountEntity accountEntity = AccountEntity.builder()
                                .name(username)
                                .password(password)
                                .build();
                        try {
                            accountService.createAccount(accountEntity);
                        } catch (InvalidCreationException e) {
                            return errorScreenRepository.getErrorScreen(
                                    "User creation error");
                        }
                        return successScreenRepository.getSuccessScreen(
                                "Account registration success");
                    case '1':
                        return welcomeScreenRepository.getWelcomeScreen();
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
