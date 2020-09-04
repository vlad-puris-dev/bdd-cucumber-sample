package com.vvp.sample.service;

import java.util.List;
import java.util.Optional;

import com.vvp.sample.model.Account;
import com.vvp.sample.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    /**
     * Account repository.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Provide account details.
     * @param accountId account identifier
     * @return account details
     * @throws Exception exception thrown during account details retrieval
     */
    public Optional<Account> getAccount(final String accountId) throws Exception {
        return accountRepository.findById(accountId);
    }

    /**
     * Provide accounts details.
     * @return accounts details
     */
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}
