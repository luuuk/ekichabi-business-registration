package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.controller.request.UssdRequest;
import com.ekichabi_business_registration.screens.repository.WelcomeScreenRepository;
import com.ekichabi_business_registration.service.Session;
import com.ekichabi_business_registration.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequestMapping("")
//@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@RequiredArgsConstructor
@Transactional
public class CommonController {

    private final SessionService sessionService;
    @Autowired
    private WelcomeScreenRepository welcomeScreenRepository;

    @GetMapping("favicon.ico")
    public ResponseEntity<ImageIcon> favicon() {
        // TODO return favicon here
        return null;
    }

    @GetMapping("ussd-simulator")
    public String ussdSimulator(String id, String command) {
        try {
            final Session session = sessionService.getSessionFromIdAndCommand(
                    id, command, welcomeScreenRepository::getWelcomeScreen);
            return session.getScreen().toString();
        } catch (IllegalArgumentException e) {
            return welcomeScreenRepository.getError404Screen().toString();
        }
    }

    @PostMapping("ussd")
    public String ussdEntryPoint(@ModelAttribute UssdRequest request) {
        try {
            final Session session = sessionService.getSessionFromIdAndCommand(
                    request.getSessionId(), request.getText(),
                    welcomeScreenRepository::getWelcomeScreen);
            return session.getScreen().toString();
        } catch (IllegalArgumentException e) {
            return welcomeScreenRepository.getError404Screen().toString();
        }
    }
}
