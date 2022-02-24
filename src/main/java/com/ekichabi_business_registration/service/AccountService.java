package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.db.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public AccountEntity createAccount(AccountEntity accountEntity)
            throws InvalidCreationException {

        if (accountEntity.getName() == null) {
            throw new InvalidCreationException();
        }

        System.out.println(repository.findByName(accountEntity.getName()));
        if (repository.findByName(accountEntity.getName()) != null) {
            throw new InvalidCreationException();
        }

        // TODO: add more checks
        return repository.save(accountEntity);
    }

    public Optional<AccountEntity> login(String username, String password) {
        val accountEntity = repository.findByName(username);
        if (accountEntity.getPassword().equals(password)) {
            return Optional.of(accountEntity);
        } else {
            return Optional.empty();
        }
    }
}
