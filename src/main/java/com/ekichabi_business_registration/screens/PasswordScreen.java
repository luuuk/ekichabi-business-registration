package com.ekichabi_business_registration.screens;

import org.apache.logging.log4j.util.Strings;

public abstract class PasswordScreen extends InputScreen {
    @Override
    public String toString() {
        return baseScreenText() + Strings.repeat("*", sb.length());
    }
}
