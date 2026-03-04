package com.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a collection of BankAccount objects and supports
 * aggregate and transfer operations.
 */
public class Bank {

    private final List<BankAccount> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Adds a bank account to this bank.
     *
     * @param account account to add (must not be null)
     * @throws IllegalArgumentException if account is null
     */
    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null.");
        }
        accounts.add(account);
    }

    /**
     * Returns the sum of balances across all managed accounts.
     *
     * @return total assets
     */
    public double getTotalAssets() {
        double total = 0;
        for (BankAccount account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    /**
     * Transfers funds from one account to another.
     * The operation is atomic: if any validation fails, neither balance changes.
     *
     * @param from   source account
     * @param to     destination account
     * @param amount amount to transfer (must be positive and not exceed source balance)
     * @throws IllegalArgumentException if from or to is null, or amount is not positive
     * @throws IllegalStateException    if source account has insufficient funds
     */
    public void transfer(BankAccount from, BankAccount to, double amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Source and destination accounts must not be null.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Transfer amount must be positive. Provided: " + amount);
        }
        if (amount > from.getBalance()) {
            throw new IllegalStateException(
                "Insufficient funds in source account. Balance: " + from.getBalance()
                    + ", Requested: " + amount);
        }
        from.withdraw(amount);
        to.deposit(amount);
    }

    /**
     * Returns an unmodifiable view of all accounts in this bank.
     *
     * @return list of accounts
     */
    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}
