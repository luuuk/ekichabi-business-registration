package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.screens.repository.WelcomeScreenRepository;
import com.ekichabi_business_registration.service.Session;
import com.ekichabi_business_registration.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.ImageIcon;

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
    public String ussdSimulator(Long id, String command) {
        final Session session;

        if (sessionService.contains(id)) {
            if (command.equals((""))) {
                return welcomeScreenRepository.getError404Screen().toString();
            }
            session = sessionService.get(id);
            if (session.getCommand().length() + 1 != command.length()) {
                return welcomeScreenRepository.getError404Screen().toString();
            }
            session.setCommand(command);
            val screen = session.getScreen().doAction(command.charAt(command.length() - 1));
            session.setScreen(screen);
        } else {
            if (!command.equals("")) {
                // fresh command should correspond to fresh session
                return welcomeScreenRepository.getError404Screen().toString();
            }
            session = new Session(id, welcomeScreenRepository.getWelcomeScreen(), command);
            sessionService.put(id, session);
        }
        return session.getScreen().toString();
    }
}
