package com.ekichabi_business_registration.screens;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SimpleScreen extends Screen {
    public SimpleScreen(boolean shouldContinue) {
        super(shouldContinue);
    }

    public final Transit doAction(char c) {
        Screen screen;
        for (Action action: actions) {
            screen = action.apply(c);
            if (screen != null) {
                return new PureTransit(screen);
            }
        }
        return null;
    }


    @Getter
    private final List<Action> actions = new ArrayList<>();

    public Screen addAction(Action action) {
        actions.add(action);
        return this;
    }

    @Override
    public SimpleScreen line(String text) {
        return ((SimpleScreen) super.line(text));
    }

}
