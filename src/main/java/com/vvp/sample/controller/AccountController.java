package com.vvp.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vvp.sample.helper.ValidationHelper;
import com.vvp.sample.model.Account;
import com.vvp.sample.model.AccountResponse;
import com.vvp.sample.service.AccountService;

@RestController
@RequestMapping(value = "v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {
    /**
     * Account service.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Provide account details.
     * @param accountId account identifier
     * @return account details
     * @throws Exception exception thrown during account details retrieval
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("accountId") final String accountId)
            throws Exception {
        AccountResponse accountResponse = new AccountResponse(accountId);
        validateParams(accountResponse);
        try {
            Optional<Account> account = accountService.getAccount(accountResponse.getAccountId());
            if (account.isPresent()) {
                accountResponse.setAccounts(Stream.of(account.get()).collect(Collectors.toList()));
            } else {
                throw new EmptyResultDataAccessException("", 0);
            }
        } catch (EmptyResultDataAccessException | NoResultException re) {
            accountResponse.setErrorMessage("{No data found}");
            throw new EmptyResultDataAccessException(accountResponse.toString(), 0);
        } catch (Exception ex) {
            accountResponse.setErrorMessage("{" + ex.getMessage() + "}");
            throw new Exception(accountResponse.toString(), ex);
        }
        return ResponseEntity.ok(accountResponse);
    }

    /**
     * Provide accounts details.
     * @return accounts details
     * @throws Exception exception thrown during account details retrieval
     */
    @GetMapping("")
    public ResponseEntity<AccountResponse> getAccounts() throws Exception {
        AccountResponse accountResponse = new AccountResponse();
        try {
            List<Account> accounts = accountService.getAccounts();
            if (accounts != null && !accounts.isEmpty()) {
                accountResponse.setAccounts(accounts);
            } else {
                throw new EmptyResultDataAccessException("", 0);
            }
        } catch (EmptyResultDataAccessException | NoResultException re) {
            accountResponse.setErrorMessage("{No data found}");
            throw new EmptyResultDataAccessException(accountResponse.toString(), 0);
        } catch (Exception ex) {
            accountResponse.setErrorMessage("{" + ex.getMessage() + "}");
            throw new Exception(accountResponse.toString(), ex);
        }
        return ResponseEntity.ok(accountResponse);
    }

    /**
     * Validate parameters.
     * @param accountResponse account response
     */
    private void validateParams(final AccountResponse accountResponse) {
        List<String> errors = new ArrayList<>();
        if (!ValidationHelper.validAccountId(accountResponse.getAccountId())) {
            errors.add("Invalid accountId");
        }
        if (!errors.isEmpty()) {
            accountResponse.setErrorMessage(errors.parallelStream()
                    .map(n -> String.valueOf(n)).collect(Collectors.joining("; ", "{", "}")));
            throw new IllegalArgumentException(accountResponse.toString());
        }
    }
}
