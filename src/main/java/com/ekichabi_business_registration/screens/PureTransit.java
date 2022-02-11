package com.ekichabi_business_registration.screens;

import org.springframework.context.ApplicationContext;

import java.util.Optional;

public class PureTransit extends Transit {
    public PureTransit(Screen screen) {
        super(screen);
    }

    @Override
    public Optional<Screen> doRequest(ApplicationContext context) {
        return Optional.empty();
    }
}