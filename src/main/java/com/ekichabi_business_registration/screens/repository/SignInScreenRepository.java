package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.screens.stereotype.InputScreen;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import com.ekichabi_business_registration.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignInScreenRepository {
    private final AccountService accountService;
    private final ErrorScreenRepository errorScreenRepository;
    private final SuccessScreenRepository successScreenRepository;

    Screen getSignInScreen() {
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
            line("Sign in - enter password");
            this.username = username;
        }

        @Override
        public Screen getNextScreen(String password) {
            val accountEntityOptional = accountService.login(username, password);
            if (accountEntityOptional.isPresent()) {
                return successScreenRepository.getSuccessScreen(
                        "Login sucessful");
            } else {
                return errorScreenRepository.getErrorScreen("Login failed");
            }
        }
    }
}
