package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.repository.AccountRepository;
import com.ekichabi_business_registration.util.exceptions.InvalidCreationException;
import com.ekichabi_business_registration.util.exceptions.InvalidOwnershipException;
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

    public boolean validateAccountOwnsBusiness(
            AccountEntity accountEntity, BusinessEntity businessEntity) {
        AccountEntity existingAccount = repository.findByName(accountEntity.getName());
        return existingAccount.getOwnedBusinesses().contains(businessEntity);
    }
}
