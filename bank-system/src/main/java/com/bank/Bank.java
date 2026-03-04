package com.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Bank {

    private final List<BankAccount> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }


    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null.");
        }
        accounts.add(account);
    }


    public double getTotalAssets() {
        double total = 0;
        for (BankAccount account : accounts) {
            total += account.getBalance();
        }
        return total;
    }


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

    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }
}
