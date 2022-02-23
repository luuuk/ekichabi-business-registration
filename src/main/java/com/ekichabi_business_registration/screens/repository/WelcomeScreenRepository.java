package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.screens.stereotype.Screen;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WelcomeScreenRepository {
    private final SignupScreenRepository signupScreenRepository;
    private final SignInScreenRepository signInScreenRepository;
    private final BusinessCreationScreenRepository businessCreationScreenRepository;

    public static Screen getBrowseBusinessScreen() {
        return null;
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

    public Screen getBusinessOperationSelectScreen() {
        return Screen.conScreen()
                .line("Would you like to ...")
                .line("1. Register a business")
                .line("2. Edit a business")
                .line("3. Delete a business")
                .addAction(c -> {
                    switch (c) {
                        case "1":
                            return getRegisterBusinessScreen();
                        case "2":
                            return getEditBusinessScreen();
                        case "3":
                            return getDeleteBusinessScreen();
                        default:
                            return null;
                    }
                });
    }

    public Screen getWelcomeScreen() {
        return Screen.conScreen()
                .line("Welcome to ekichabi 2.0!")
                .line("Would you like to ...")
                .line("1. Browse business")
                .line("2. Sign in")
                .line("3. Sign up")
                .addAction(c -> {
                    switch (c) {
                        case "1":
                            return getBrowseBusinessScreen();
                        case "2":
                            return signInScreenRepository.getSignInScreen();
                        case "3":
                            return signupScreenRepository.getSignupScreen();
                        default:
                            return null;
                    }
                });
    }

    public Screen getSignedInWelcomeScreen() {
        return Screen.conScreen()
                .line("1. Browse")
                .line("2. Create business")
                .line("3. Update business")
                .line("4. Update account")
                .line("5. Exit")
                .addAction(c -> {
                    switch (c) {
                        case "1":
                            // TODO: add browse business functionality
                            return Screen.conScreen()
                            .line("This workflow is unfinished");
                        case "2":
                            // TODO: add create business functionality
                            return businessCreationScreenRepository.getBusinessCreationScreen();
                        case "3":
                            // TODO: add update business functionality
                            return Screen.conScreen()
                            .line("This workflow is unfinished");
                        case "4":
                            // TODO: add update account functionality
                            return Screen.conScreen()
                            .line("This workflow is unfinished");
                        case "5":
                            // TODO: add session termination functionality
                            return Screen.conScreen()
                            .line("This workflow is unfinished");
                        default:
                            return getSignedInWelcomeScreen();

                    }
                });
    }

    public Screen getError404Screen() {
        return Screen.endScreen()
                .line("ERROR Page not found");
    }
}
