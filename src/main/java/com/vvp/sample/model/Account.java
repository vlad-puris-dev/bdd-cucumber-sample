package com.vvp.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.GsonBuilder;

@Entity
@Table(name = "ACCOUNT")
public class Account {
    /**
     * Account identifier.
     */
    @Id
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    /**
     * Account type.
     */
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
    /**
     * Account open date.
     */
    @Column(name = "ACCOUNT_OPEN_DATE")
    private String accountOpenDate;
    /**
     * Account close date.
     */
    @Column(name = "ACCOUNT_CLOSE_DATE")
    private String accountCloseDate;

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
     * Get account type.
     * @return account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Set account type.
     * @param accountType account type
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * Get account open date.
     * @return account open date
     */
    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    /**
     * Set account open date.
     * @param accountOpenDate account open date
     */
    public void setAccountOpenDate(final String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    /**
     * Get account close date.
     * @return account close date
     */
    public String getAccountCloseDate() {
        return accountCloseDate;
    }

    /**
     * Set account close date.
     * @param accountCloseDate account close date
     */
    public void setAccountCloseDate(final String accountCloseDate) {
        this.accountCloseDate = accountCloseDate;
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
