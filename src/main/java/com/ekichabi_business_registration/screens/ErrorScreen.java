package com.ekichabi_business_registration.screens;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ErrorScreen extends Screen {
    private final String reason;
    private int count = 0;

    public ErrorScreen(String reason) {
        super(true);
        this.reason = reason;
        line("ERROR");
        line(reason);
        line("99. Back");
    }

    @Override
    protected Screen doAction(char c) {
        if (c == '9') {
            count++;
            if (count == 2) {
                return ScreenRepository.getWelcomeScreen();
            }
        }
        return this;
    }
}
