package com.ekichabi_business_registration.screens.stereotype;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class InputScreen extends Screen {
    @Getter(AccessLevel.PROTECTED)
    private final StringBuilder sb = new StringBuilder();

    public InputScreen() {
        super(true);
    }

    @Override
    public Screen doAction(char c) {
        if (c == '*') {
            return getNextScreen(sb.toString());
        } else {
            sb.append(c);
            return this;
        }
    }

    protected String baseScreenText() {
        return super.toString();
    }

    @Override
    public String toString() {
        return super.toString() + sb + "\n";
    }

    public abstract Screen getNextScreen(String s);
}
