package com.ekichabi_business_registration.controller;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.service.AccountService;
import com.ekichabi_business_registration.util.exceptions.InvalidCreationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {}) // TODO update with origins we want to allow requests from
@Transactional
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService service;

    @PostMapping("account")
    public ResponseEntity<?> createAccount(@RequestBody AccountEntity account) {
        logger.info("Creating Account");

        try {
            service.createAccount(account);
            logger.info("Created account");
            return ResponseEntity.ok().body("Created account");
        } catch (Exception | InvalidCreationException e) {
            logger.error("Failed to create account");
            return ResponseEntity.badRequest()
                    .body("Account with username " + account.getName() + " already exists.");
        }
    }
}
