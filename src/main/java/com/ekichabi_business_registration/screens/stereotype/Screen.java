package com.ekichabi_business_registration.screens.stereotype;

import com.ekichabi_business_registration.screens.repository.WelcomeScreenRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class Screen {
    @Setter(onMethod = @__({@Autowired}), onParam = @__({@Lazy}))
    private WelcomeScreenRepository welcomeScreenRepository;

    @Getter
    private final List<StringBuilder> lines = new ArrayList<>();
    private final boolean shouldContinue;
    private Screen fallbackScreen;

    protected Screen(boolean shouldContinue) {
        this.shouldContinue = shouldContinue;
    }

    public Screen getFallbackScreen() {
        return Objects.requireNonNullElseGet(fallbackScreen,
                welcomeScreenRepository::getError404Screen);
    }

    public abstract Screen doAction(char c);

    public static SimpleScreen conScreen() {
        return new SimpleScreen(true);
    }

    public static SimpleScreen endScreen() {
        return new SimpleScreen(false);
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
