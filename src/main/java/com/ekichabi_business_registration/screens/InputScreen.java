package com.ekichabi_business_registration.screens;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class InputScreen extends SimpleScreen {
    @Getter(AccessLevel.PROTECTED)
    private final StringBuilder sb = new StringBuilder();

    public InputScreen() {
        super(true);
        addAction(c -> {
            if (c != '*') {
                sb.append(c);
                return this;
            } else {
                return getNextScreen(sb.toString());
            }
        });
    }

    protected String baseScreenText() {
        return super.toString();
    }

    @Override
    public String toString() {
        return super.toString() + sb.toString() + "\n";
    }

    public abstract Screen getNextScreen(String s);
}
