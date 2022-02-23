package com.ekichabi_business_registration.screens.stereotype;

import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class SimpleScreen extends Screen {
    private StringBuilder buffer = new StringBuilder();

    public SimpleScreen(boolean shouldContinue) {
        super(shouldContinue);
    }

    public final Screen doAction(char c) {
        if (c == '*') {
            val command = buffer.toString();
            for (Action action : actions) {
                Screen screen = action.apply(command);
                if (screen != null) {
                    buffer.setLength(0);
                    return screen;
                }
            }
            return null;
        } else {
            buffer.append(c);
            return this;
        }
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
