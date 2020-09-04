package com.vvp.sample.model;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.google.gson.GsonBuilder;

public class AccountResponse {
    /**
     * Account identifier.
     */
    private String accountId;
    /**
     * Response status.
     */
    private String status = String.valueOf(HttpStatus.OK.value());
    /**
     * Error message content.
     */
    private String errorMessage;
    /**
     * Account details.
     */
    private List<Account> accounts;

    /**
     * No arguments constructor.
     */
    public AccountResponse() {
    }

    /**
     * Constructor with arguments.
     * @param accountId account identifier
     */
    public AccountResponse(final String accountId) {
        this.setAccountId(accountId);
    }

    /**
     * Get account identifier.
     * @return account identifier
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Set account identifier.
     * @param accountId account identifier
     */
    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }

    /**
     * Get response status.
     * @return response status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set response status.
     * @param status response status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Get error message content.
     * @return error message content
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set error message content.
     * @param errorMessage error message content
     */
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Get accounts.
     * @return accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Set accounts.
     * @param accounts accounts
     */
    public void setAccounts(final List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Get string representation of object in json format.
     * @return string representation of object in json format
     */
    @Override
    public String toString() {
        return new GsonBuilder().serializeNulls().create().toJson(this).toString();
    }

    /**
     * Check if objects are equal.
     * @param obj object
     * @return object equal status
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    /**
     * Get hash code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
