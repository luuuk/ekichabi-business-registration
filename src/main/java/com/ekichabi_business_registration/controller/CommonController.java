package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.screens.Screen;
import com.ekichabi_business_registration.screens.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.ImageIcon;

@RestController
@RequestMapping("")
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@RequiredArgsConstructor
@Transactional
public class CommonController {

    @GetMapping("favicon.ico")
    public ResponseEntity<ImageIcon> favicon() {
        // TODO return favicon here
        return null;
    }

    @GetMapping("ussd-simulator")
    public String ussd_simulator(String command) {
        return Screen.run(ScreenRepository.getWelcomeScreen(), command).toString();
    }
}
