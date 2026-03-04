package com.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BankAccount class.
 * Covers constructor, deposit, withdraw, and boundary/edge cases.
 */
@DisplayName("BankAccount Unit Tests")
class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC-001", "Alice", 500.0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Constructor: valid initial balance initializes correctly")
    void constructor_validBalance_initializesCorrectly() {
        BankAccount acc = new BankAccount("ACC-NEW", "Bob", 1000.0);
        assertEquals("ACC-NEW", acc.getAccountId());
        assertEquals("Bob", acc.getOwnerName());
        assertEquals(1000.0, acc.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Constructor: zero initial balance is valid")
    void constructor_zeroBalance_isValid() {
        BankAccount acc = new BankAccount("ACC-ZERO", "Carol", 0.0);
        assertEquals(0.0, acc.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Constructor: negative initial balance throws IllegalArgumentException")
    void constructor_negativeBalance_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new BankAccount("ACC-BAD", "Dave", -100.0));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Deposit Tests
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Deposit: normal deposit increases balance correctly")
    void deposit_normalAmount_increasesBalance() {
        account.deposit(200.0);
        assertEquals(700.0, account.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Deposit: zero amount throws IllegalArgumentException")
    void deposit_zeroAmount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
    }

    @Test
    @DisplayName("Deposit: negative amount throws IllegalArgumentException")
    void deposit_negativeAmount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-50.0));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Withdraw Tests
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Withdraw: normal withdrawal reduces balance correctly")
    void withdraw_normalAmount_reducesBalance() {
        account.withdraw(150.0);
        assertEquals(350.0, account.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Withdraw: withdrawal equal to balance succeeds and results in zero")
    void withdraw_exactBalance_succeeds() {
        account.withdraw(500.0);
        assertEquals(0.0, account.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Withdraw: withdrawal exceeding balance throws IllegalStateException")
    void withdraw_exceedingBalance_throwsException() {
        assertThrows(IllegalStateException.class, () -> account.withdraw(500.01));
    }

    @Test
    @DisplayName("Withdraw: zero amount throws IllegalArgumentException")
    void withdraw_zeroAmount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
    }

    @Test
    @DisplayName("Withdraw: negative amount throws IllegalArgumentException")
    void withdraw_negativeAmount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100.0));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Parameterized Deposit Tests
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Tests deposit with normal, boundary values.
     * Initial balance is 500.0.
     * Format: depositAmount, expectedBalance
     */
    @ParameterizedTest(name = "deposit({0}) → balance = {1}")
    @CsvSource({
        "50.0,    550.0",   // normal
        "100.0,   600.0",   // normal
        "250.75,  750.75",  // normal with decimals
        "0.01,    500.01",  // boundary (minimum valid deposit)
        "499.99,  999.99",  // boundary (balance - 0.01)
        "500.0,   1000.0"   // boundary (equal to balance)
    })
    @DisplayName("Deposit: parameterized normal and boundary cases")
    void deposit_parameterized_normalAndBoundary(double amount, double expectedBalance) {
        account.deposit(amount);
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    /**
     * Tests that invalid (zero or negative) deposit amounts throw an exception.
     */
    @ParameterizedTest(name = "deposit({0}) → exception")
    @ValueSource(doubles = {0.0, -1.0, -100.5, -0.01})
    @DisplayName("Deposit: parameterized invalid (edge) cases throw exception")
    void deposit_parameterized_invalidAmounts_throwException(double invalidAmount) {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(invalidAmount));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Parameterized Withdraw Tests
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Tests withdraw with normal and boundary values.
     * Initial balance is 500.0.
     * Format: withdrawAmount, expectedBalance
     */
    @ParameterizedTest(name = "withdraw({0}) → balance = {1}")
    @CsvSource({
        "50.0,   450.0",   // normal
        "100.0,  400.0",   // normal
        "250.75, 249.25",  // normal with decimals
        "0.01,   499.99",  // boundary (minimum valid withdrawal)
        "499.99, 0.01",    // boundary (balance - 0.01)
        "500.0,  0.0"      // boundary (equal to balance)
    })
    @DisplayName("Withdraw: parameterized normal and boundary cases")
    void withdraw_parameterized_normalAndBoundary(double amount, double expectedBalance) {
        account.withdraw(amount);
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    /**
     * Tests that invalid (zero or negative) withdrawal amounts throw an exception.
     */
    @ParameterizedTest(name = "withdraw({0}) → exception")
    @ValueSource(doubles = {0.0, -1.0, -100.5, -0.01})
    @DisplayName("Withdraw: parameterized invalid (edge) cases throw exception")
    void withdraw_parameterized_invalidAmounts_throwException(double invalidAmount) {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(invalidAmount));
    }

    @Test
    @DisplayName("Withdraw: amount slightly over balance throws exception")
    void withdraw_slightlyOverBalance_throwsException() {
        assertThrows(IllegalStateException.class, () -> account.withdraw(500.01));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Balance remains unchanged on failed operations
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Balance: unchanged after failed deposit")
    void balance_unchangedAfterFailedDeposit() {
        double before = account.getBalance();
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.0));
        assertEquals(before, account.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Balance: unchanged after failed withdrawal")
    void balance_unchangedAfterFailedWithdrawal() {
        double before = account.getBalance();
        assertThrows(IllegalStateException.class, () -> account.withdraw(9999.0));
        assertEquals(before, account.getBalance(), 1e-9);
    }
}
