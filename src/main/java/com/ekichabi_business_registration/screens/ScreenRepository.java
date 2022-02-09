package com.ekichabi_business_registration.screens;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ScreenRepository {
    // NOTE: only immutable screens can be declared here;
    // mutable screens like InputScreen should be created on the fly.
    private final Screen welcomeScreen =
            Screen.conScreen()
                    .line("Welcome to ekichabi 2.0!")
                    .line("Would you like to ...")
                    .line("1. Browse business")
                    .line("2. Login")
                    .line("3. Sign up")
                    .action(c -> {
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

    private final Screen businessOperationSelectScreen =
            Screen.conScreen()
                    .line("Would you like to ...")
                    .line("1. Register a business")
                    .line("2. Edit a business")
                    .line("3. Delete a business")
                    .action(c -> {
                        switch (c) {
                            case '1':
                                return getRegisterBusinessScreen();
                            case '2': return getEditBusinessScreen();
                            case '3':
                                return getDeleteBusinessScreen();
                        }
                        return null;
                    });

    public Screen getBrowseBusinessScreen() {
        return null;
    }

    public Screen getBusinessOperationSelectScreen() {
        return businessOperationSelectScreen;
    }

    public Screen getDeleteBusinessScreen() {
        return null;
    }

    public Screen getEditBusinessScreen() {
        return null;
    }

    public Screen getRegisterBusinessScreen() {
        return null;
    }

    public Screen getWelcomeScreen() {
        return welcomeScreen;
    }

    public Screen getSignupScreen() {
        return null;
    }
}
