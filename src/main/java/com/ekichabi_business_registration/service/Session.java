package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.screens.stereotype.Screen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Session {
    private String id;
    private Screen screen;
    private String command;
}
