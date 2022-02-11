package com.ekichabi_business_registration.screens;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class Screen {
    @Autowired
    private ApplicationContext context;

    @Getter
    private final List<StringBuilder> lines = new ArrayList<>();
    private Screen fallbackScreen;

    protected Screen(boolean shouldContinue) {
        this.shouldContinue = shouldContinue;
    }

    public static Transit run(Screen screen, String s) {
        // TODO: There is still another funky bug, where the next page of an error is a success page...
        Transit transit = new PureTransit(screen);
        for (char c: s.toCharArray()) {
            screen = transit.getScreen();
            Transit nextTransit = screen.doAction(c);
            if (nextTransit == null) {
                nextTransit = new PureTransit(screen.getFallbackScreen());
            }
            transit = nextTransit;
        }
        return transit;
    }

    public Screen getFallbackScreen() {
        return Objects.requireNonNullElseGet(fallbackScreen, () -> context.getBean("error404Screen", Screen.class));
    }


    protected abstract Transit doAction(char c);

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
