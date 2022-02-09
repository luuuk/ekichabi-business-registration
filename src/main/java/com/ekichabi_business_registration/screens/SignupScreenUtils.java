package com.ekichabi_business_registration.screens;

import org.apache.logging.log4j.util.Strings;
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
                    // TODO: think about this case; Should direct to an error page
                    return getFallbackScreen();
                }
            } else {
                return new SignupScreenPassword(username, password);
            }
        }
    }

    public static Screen getSignupScreen() {
        return new SignupScreen();
    }

    private static class SignupConfirmationScreen extends SimpleScreen {
        public SignupConfirmationScreen(String username, String password) {
            super(false);
            line("username: " + username);
            line("password: " + Strings.repeat("*", password.length()));
            line("0. create");
            line("1. cancel");
            addAction(c -> {
                switch (c) {
                    case '0': return null; // TODO: think about this case
                    case '1': return ScreenRepository.getWelcomeScreen();
                }
                return null;
            });
        }
    }
}
