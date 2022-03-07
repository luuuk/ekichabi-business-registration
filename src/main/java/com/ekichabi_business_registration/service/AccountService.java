package com.ekichabi_business_registration.service;

import com.ekichabi_business_registration.db.entity.AccountEntity;
import com.ekichabi_business_registration.db.entity.BusinessEntity;
import com.ekichabi_business_registration.db.repository.AccountRepository;
import com.ekichabi_business_registration.util.exceptions.InvalidCreationException;
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
            Long id, BusinessEntity businessEntity) {

        // If requested BusinessEntity has multiple owners, return false
        if (businessEntity.getOwners().size() != 1) {
            return false;
        }

        AccountEntity requestedAccount = businessEntity.getOwners().get(0);
        AccountEntity existingAccount = repository.findByName(requestedAccount.getName());

        // If requested Account does not exist return false
        if (existingAccount == null) {
            return false;
        }

        // If requested BusinessEntity owner login creds are incorrect, return false
        if (!requestedAccount.getPassword().equals(existingAccount.getPassword())) {
            return false;
        }

        // Check that account owns business
        return existingAccount.getOwnedBusinesses()
                .contains(BusinessEntity.builder().id(id).build());
    }
}
