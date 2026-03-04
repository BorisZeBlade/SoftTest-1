package com.bank;

/**
 * Represents a single bank account with basic operations.
 */
public class BankAccount {

    private final String accountId;
    private final String ownerName;
    private double balance;

    /**
     * Creates a new BankAccount.
     *
     * @param accountId  unique identifier of the account
     * @param ownerName  account holder's name
     * @param initialBalance starting balance (must not be negative)
     * @throws IllegalArgumentException if initialBalance is negative
     */
    public BankAccount(String accountId, String ownerName, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                "Initial balance cannot be negative. Provided: " + initialBalance);
        }
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    /**
     * Deposits money into the account.
     *
     * @param amount amount to deposit (must be positive)
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit amount must be positive. Provided: " + amount);
        }
        balance += amount;
    }

    /**
     * Withdraws money from the account.
     *
     * @param amount amount to withdraw (must be positive and not exceed balance)
     * @throws IllegalArgumentException if amount is zero or negative
     * @throws IllegalStateException    if amount exceeds current balance
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Withdrawal amount must be positive. Provided: " + amount);
        }
        if (amount > balance) {
            throw new IllegalStateException(
                "Insufficient funds. Balance: " + balance + ", Requested: " + amount);
        }
        balance -= amount;
    }

    /**
     * Returns the current balance of the account.
     *
     * @return current balance
     */
    public double getBalance() {
        return balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String toString() {
        return "BankAccount{accountId='" + accountId + "', ownerName='" + ownerName
            + "', balance=" + balance + "}";
    }
}
