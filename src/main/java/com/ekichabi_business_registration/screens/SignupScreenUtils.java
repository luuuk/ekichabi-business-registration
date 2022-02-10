package com.ekichabi_business_registration.screens;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.service.AccountService;
import com.ekichabi_business_registration.service.InvalidCreationException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignupScreenUtils {

    private static class SignupScreen extends InputScreen {

        public SignupScreen() {
            super();
            line("Sign up - enter username");
            line("Usernames must be unique");
        }

        @Override
        public Screen getNextScreen(String username) {
            return new SignupScreenPassword(username);
        }
    }

    private static class SignupScreenPassword extends PasswordScreen {
        private final String username;
        private final String password;

        public SignupScreenPassword(String username) {
            super();
            this.username = username;
            this.password = null;
            line("Sign up - enter password");
        }

        public SignupScreenPassword(String username, String password) {
            super();
            this.username = username;
            this.password = password;
            line("Sign up - enter password");
        }

        public boolean hasRepeated() {
            return password != null;
        }

        @Override
        public Screen getNextScreen(String password) {
            if (hasRepeated()) {
                if (password.equals(this.password)) {
                    return new SignupConfirmationScreen(username, password);
                } else {
                    return new ErrorScreen("Password does not match");
                }
            } else {
                return new SignupScreenPassword(username, password);
            }
        }
    }

    public static Screen getSignupScreen() {
        return new SignupScreen();
    }

    private static class SignupConfirmationScreen extends Screen implements WithRequest {
        private final String username;
        private final String password;

        @Autowired
        private AccountService accountService;

        public SignupConfirmationScreen(String username, String password) {
            super(false);
            this.username = username;
            this.password = password;
            line("username: " + username);
            line("password: " + Strings.repeat("*", password.length()));
            line("0. create");
            line("1. cancel");
        }

        @Override
        protected Screen doAction(char c) {
            switch (c) {
                case '0': return new SuccessScreen("Account registration success");
                case '1': return ScreenRepository.getWelcomeScreen();
            }
            return this;
        }

        @Override
        public void doRequest() {
            AccountEntity accountEntity = AccountEntity.builder()
                    .name(username)
                    .password(password)
                    .build();
            try {
                accountService.createBusiness(accountEntity);
            } catch (InvalidCreationException e) {
                // TODO: deal with creation failure
                e.printStackTrace();
            }
        }
    }
}
