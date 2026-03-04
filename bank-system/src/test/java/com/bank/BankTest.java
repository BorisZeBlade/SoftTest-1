package com.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Bank Integration Tests")
class BankTest {

    private Bank bank;
    private BankAccount alice;
    private BankAccount bob;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        alice = new BankAccount("ACC-001", "Alice", 500.0);
        bob   = new BankAccount("ACC-002", "Bob",   300.0);
        bank.addAccount(alice);
        bank.addAccount(bob);
    }



    @Test
    @DisplayName("AddAccount: accounts are added and retrievable")
    void addAccount_accountsAddedCorrectly() {
        assertEquals(2, bank.getAccounts().size());
        assertTrue(bank.getAccounts().contains(alice));
        assertTrue(bank.getAccounts().contains(bob));
    }

    @Test
    @DisplayName("AddAccount: null account throws IllegalArgumentException")
    void addAccount_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> bank.addAccount(null));
    }

    @Test
    @DisplayName("AddAccount: bank starts empty when no accounts added")
    void addAccount_emptyBank_hasZeroAccounts() {
        Bank emptyBank = new Bank();
        assertEquals(0, emptyBank.getAccounts().size());
    }



    @Test
    @DisplayName("GetTotalAssets: sum of all account balances is correct")
    void getTotalAssets_correctSum() {
        assertEquals(800.0, bank.getTotalAssets(), 1e-9);
    }

    @Test
    @DisplayName("GetTotalAssets: returns zero for empty bank")
    void getTotalAssets_emptyBank_returnsZero() {
        Bank emptyBank = new Bank();
        assertEquals(0.0, emptyBank.getTotalAssets(), 1e-9);
    }

    @Test
    @DisplayName("GetTotalAssets: updates correctly after deposit")
    void getTotalAssets_afterDeposit_updatesCorrectly() {
        alice.deposit(200.0);
        assertEquals(1000.0, bank.getTotalAssets(), 1e-9);
    }

    @Test
    @DisplayName("GetTotalAssets: total unchanged after transfer between accounts")
    void getTotalAssets_afterTransfer_totalUnchanged() {
        double totalBefore = bank.getTotalAssets();
        bank.transfer(alice, bob, 100.0);
        assertEquals(totalBefore, bank.getTotalAssets(), 1e-9);
    }



    @Test
    @DisplayName("Transfer: valid transfer moves funds correctly")
    void transfer_valid_movesFundsCorrectly() {
        bank.transfer(alice, bob, 200.0);
        assertEquals(300.0, alice.getBalance(), 1e-9);
        assertEquals(500.0, bob.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Transfer: transfer equal to source balance succeeds")
    void transfer_equalToBalance_succeeds() {
        bank.transfer(alice, bob, 500.0);
        assertEquals(0.0,   alice.getBalance(), 1e-9);
        assertEquals(800.0, bob.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Transfer: insufficient funds throws exception and balances unchanged")
    void transfer_insufficientFunds_throwsAndBalancesUnchanged() {
        double aliceBefore = alice.getBalance();
        double bobBefore   = bob.getBalance();

        assertThrows(IllegalStateException.class, () -> bank.transfer(alice, bob, 500.01));

        assertEquals(aliceBefore, alice.getBalance(), 1e-9);
        assertEquals(bobBefore,   bob.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Transfer: negative amount throws exception and balances unchanged")
    void transfer_negativeAmount_throwsAndBalancesUnchanged() {
        double aliceBefore = alice.getBalance();
        double bobBefore   = bob.getBalance();

        assertThrows(IllegalArgumentException.class, () -> bank.transfer(alice, bob, -50.0));

        assertEquals(aliceBefore, alice.getBalance(), 1e-9);
        assertEquals(bobBefore,   bob.getBalance(), 1e-9);
    }

    @Test
    @DisplayName("Transfer: zero amount throws exception")
    void transfer_zeroAmount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(alice, bob, 0.0));
    }

    @Test
    @DisplayName("Transfer: null source account throws exception")
    void transfer_nullFrom_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(null, bob, 100.0));
    }

    @Test
    @DisplayName("Transfer: null destination account throws exception")
    void transfer_nullTo_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(alice, null, 100.0));
    }


    @ParameterizedTest(name = "transfer from {0} → amount {1} → success={2}")
    @CsvSource({
        "500.0, 100.0,   true",
        "500.0, 500.0,   true",
        "500.0, 500.01,  false",
        "500.0, -50.0,   false",
        "500.0, 0.0,     false",
        "100.0, 100.0,   true",
        "100.0, 100.01,  false",
        "0.01,  0.01,    true",
        "0.01,  0.02,    false"
    })
    @DisplayName("Transfer: parameterized scenarios")
    void transfer_parameterized(double fromBalance, double transferAmount, boolean expectSuccess) {
        BankAccount source = new BankAccount("SRC", "Source", fromBalance);
        BankAccount dest   = new BankAccount("DST", "Dest", 0.0);
        Bank testBank = new Bank();
        testBank.addAccount(source);
        testBank.addAccount(dest);

        if (expectSuccess) {
            assertDoesNotThrow(() -> testBank.transfer(source, dest, transferAmount));
            assertEquals(fromBalance - transferAmount, source.getBalance(), 1e-9);
            assertEquals(transferAmount, dest.getBalance(), 1e-9);
        } else {
            double srcBefore  = source.getBalance();
            double destBefore = dest.getBalance();
            assertThrows(Exception.class, () -> testBank.transfer(source, dest, transferAmount));
            // Balances must remain unchanged on failure
            assertEquals(srcBefore,  source.getBalance(), 1e-9);
            assertEquals(destBefore, dest.getBalance(), 1e-9);
        }
    }
}
