package com.ekichabi_business_registration.screens;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScreenRepository {

    // NOTE: only immutable screens can be declared here;
    // mutable screens like InputScreen should be created on the fly.
    private static final Screen welcomeScreen =
            Screen.conScreen()
                    .line("Welcome to ekichabi 2.0!")
                    .line("Would you like to ...")
                    .line("1. Browse business")
                    .line("2. Sign in")
                    .line("3. Sign up")
                    .addAction(c -> {
                        switch (c) {
                            case '1': return getBrowseBusinessScreen();
                            case '2':
                                // TODO: Actually, we need to login before doing operation selection
                                return getBusinessOperationSelectScreen();
                            case '3':
                                return getSignupScreen();
                        }
                        return null;
                    });

    private static final Screen businessOperationSelectScreen =
            Screen.conScreen()
                    .line("Would you like to ...")
                    .line("1. Register a business")
                    .line("2. Edit a business")
                    .line("3. Delete a business")
                    .addAction(c -> {
                        switch (c) {
                            case '1':
                                return getRegisterBusinessScreen();
                            case '2': return getEditBusinessScreen();
                            case '3':
                                return getDeleteBusinessScreen();
                        }
                        return null;
                    });

    public static Screen getBrowseBusinessScreen() {
        return null;
    }

    public static Screen getBusinessOperationSelectScreen() {
        return businessOperationSelectScreen;
    }

    public static Screen getDeleteBusinessScreen() {
        return null;
    }

    public static Screen getEditBusinessScreen() {
        return null;
    }

    public static Screen getRegisterBusinessScreen() {
        return null;
    }

    public static Screen getWelcomeScreen() {
        return welcomeScreen;
    }

    public static Screen getSignupScreen() {
        return SignupScreenUtils.getSignupScreen();
    }
}
