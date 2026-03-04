package com.bank;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * JUnit 5 Test Suite that aggregates all bank system tests
 * into a single executable suite.
 *
 * Run with: mvn test
 */
@Suite
@SuiteDisplayName("Bank System – Full Test Suite")
@SelectClasses({
    BankAccountTest.class,
    BankTest.class
})
public class BankSystemTestSuite {
    // No body needed – @Suite annotation drives execution
}
