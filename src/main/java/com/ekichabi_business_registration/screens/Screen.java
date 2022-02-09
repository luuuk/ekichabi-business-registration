package com.ekichabi_business_registration.screens;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Screen {
    public static Screen run(Screen screen, String s) {
        for (char c: s.toCharArray()) {
            Screen next = null;
            for (Action action: screen.actions) {
                 next = action.apply(c);
                if (next != null) {
                    screen = next;
                    break;
                }
            }
            if (next == null) {
                return screen.getFallbackScreen();
            }
        }
        return screen;
    }

    public static Screen conScreen() {
        return new Screen(true);
    }

    public static Screen endScreen() {
        return new Screen(false);
    }

    private final boolean shouldContinue;
    @Getter
    private final List<StringBuilder> lines = new ArrayList<>();
    @Getter
    private final List<Action> actions = new ArrayList<>();
    @Getter
    private Screen fallbackScreen;

    public Screen action(Action action) {
        actions.add(action);
        return this;
    }

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
