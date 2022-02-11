package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.screens.Screen;
import com.ekichabi_business_registration.screens.ScreenRepository;
import com.ekichabi_business_registration.screens.Transit;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.ImageIcon;
import java.util.Optional;

@RestController
@RequestMapping("")
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@RequiredArgsConstructor
@Transactional
public class CommonController {

    private final ApplicationContext context;

    @GetMapping("favicon.ico")
    public ResponseEntity<ImageIcon> favicon() {
        // TODO return favicon here
        return null;
    }

    @GetMapping("ussd-simulator")
    public String ussdSimulator(String command) {
        Transit transit = Screen.run(context.getBean("welcomeScreen", Screen.class), command);
        Optional<Screen> optionalScreen = transit.doRequest(context);
        Screen screen = optionalScreen.orElseGet(transit::getScreen);
        return screen.toString();
    }
}
