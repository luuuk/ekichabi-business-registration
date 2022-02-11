package com.ekichabi_business_registration.screens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@AllArgsConstructor
public abstract class Transit {
    @Getter
    private final Screen screen;
    // A potential screen in the case of error
    public abstract Optional<Screen> doRequest(ApplicationContext context);
}
