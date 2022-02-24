package com.ekichabi_business_registration.screens.repository;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.screens.stereotype.Screen;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WelcomeScreenRepository {
    private final SignupScreenRepository signupScreenRepository;
    private final SignInScreenRepository signInScreenRepository;
    private final BusinessCreationScreenRepository businessCreationScreenRepository;
    private final UpdateBusinessScreenRepository updateBusinessScreenRepository;

    public static Screen getBrowseBusinessScreen() {
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

    public static Screen getDeleteBusinessScreen() {
        return null;
    }

    public static Screen getEditBusinessScreen() {
        return null;
    }

    public static Screen getRegisterBusinessScreen() {
        return null;
    }

    public Screen getWelcomeScreen() {
        return Screen.conScreen()
                .line("Welcome to ekichabi 2.0!")
                .line("Would you like to ...")
                .line("1. Sign in")
                .line("2. Sign up")
                .line("3. Exit")
                .addAction(c -> {
                    switch (c) {
                        case "1":
                            return signInScreenRepository.getSignInScreen();
                        case "2":
                            return signupScreenRepository.getSignupScreen();
                        case "3":
                            return Screen.endScreen()
                            .line("Thank you for using eKichabi 2.0!");
                        default:
                            return getWelcomeScreen();
                    }
                });
    }

    public Screen getSignedInWelcomeScreen(AccountEntity accountEntity) {
        return Screen.conScreen()
                .line("Signed in as " + accountEntity.getName())
                .line("1. Create business")
                .line("2. Update business")
                .line("3. Delete business")
                .line("4. Update account")
                .line("5. Exit")
                .addAction(c -> {
                    switch (c) {
                        case "1":
                            // TODO: add create business functionality
                            return businessCreationScreenRepository
                                    .getBusinessCreationScreen(accountEntity);
                        case "2":
                            // TODO: add update business functionality
                            return updateBusinessScreenRepository
                                .getUpdateBusinessesScreen(accountEntity);
                        case "3":
                            // TODO: add delete business functionality
                            return Screen.conScreen()
                                    .line("This workflow is unfinished");
                        case "4":
                            // TODO: add update account functionality
                            return Screen.conScreen()
                                    .line("This workflow is unfinished");
                        case "5":
                            // TODO: add session termination functionality
                            return Screen.endScreen()
                                .line("Thank you for using eKichabi 2.0!");
                        default:
                            return getSignedInWelcomeScreen(accountEntity);

                    }
                });
    }

    public Screen getError404Screen() {
        return Screen.endScreen()
                .line("ERROR Page not found");
    }
}
