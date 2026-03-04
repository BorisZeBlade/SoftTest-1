package com.bank;


public class BankAccount {

    private final String accountId;
    private final String ownerName;
    private double balance;


    public BankAccount(String accountId, String ownerName, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                "Initial balance cannot be negative. Provided: " + initialBalance);
        }
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit amount must be positive. Provided: " + amount);
        }
        balance += amount;
    }


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
