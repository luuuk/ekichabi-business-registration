package com.ekichabi_business_registration.screens;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Getter
public class SuccessScreen extends Screen {
    private final String reason;
    private int count = 0;
    @Autowired
    private ApplicationContext context;

    public SuccessScreen(String reason) {
        super(true);
        this.reason = reason;
        line("Success");
        line(reason);
        line("99. Back");
    }

    @Override
    public Transit doAction(char c) {
        if (c == '9') {
            count++;
            if (count == 2) {
                return new PureTransit(context.getBean("welcomeScreen", Screen.class));
            }
        }
        return new PureTransit(this);
    }
}
