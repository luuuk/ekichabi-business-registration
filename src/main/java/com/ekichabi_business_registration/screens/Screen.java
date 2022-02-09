package com.ekichabi_business_registration.screens;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {

    @Getter
    private final List<StringBuilder> lines = new ArrayList<>();
    private Screen fallbackScreen;

    protected Screen(boolean shouldContinue) {
        this.shouldContinue = shouldContinue;
    }

    public static Screen run(Screen screen, String s) {
        for (char c: s.toCharArray()) {
            Screen next = screen.doAction(c);
            if (next == null) {
                return screen.getFallbackScreen();
            }
            screen = next;
        }
        return screen;
    }

    public Screen getFallbackScreen() {
        if (fallbackScreen == null){
            return ScreenRepository.getError404Screen();
        } else {
            return fallbackScreen;
        }
    }

    protected abstract Screen doAction(char c);

    public static SimpleScreen conScreen() {
        return new SimpleScreen(true);
    }

    public static SimpleScreen endScreen() {
        return new SimpleScreen(false);
    }

    private final boolean shouldContinue;

    public Screen fallbackScreen(Screen screen) {
        fallbackScreen = screen;
        return this;
    }

    public Screen line(String text) {
        lines.add(new StringBuilder(text));
        return this;
    }

    public Screen line() {
        lines.add(new StringBuilder());
        return this;
    }

    public Screen text(String text) {
        lines.get(lines.size() - 1).append(text);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(shouldContinue ? "CON " : "END ");
        for (StringBuilder line: lines) {
            sb.append(line).append('\n');
        }
        return sb.toString();
    }
}
