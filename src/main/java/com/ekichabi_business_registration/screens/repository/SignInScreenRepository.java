package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.screens.stereotype.InputScreen;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import org.springframework.stereotype.Component;

@Component
public class SignInScreenRepository {
    public Screen getSignInScreen() {
        return new SignInScreen();
    }

    private class SignInScreen extends InputScreen {
        SignInScreen() {
            super();
            line("Sign in - enter username");
        }

        @Override
        public Screen getNextScreen(String username) {
            return new SignInScreenPassword(username);
        }
    }

    private class SignInScreenPassword extends InputScreen {
        private final String username;

        SignInScreenPassword(String username) {
            super();
            this.username = username;
        }

        @Override
        public Screen getNextScreen(String password) {
            return null;
        }
    }
}
