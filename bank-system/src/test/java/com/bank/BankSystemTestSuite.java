package com.bank;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@Suite
@SuiteDisplayName("Bank System – Full Test Suite")
@SelectClasses({
    BankAccountTest.class,
    BankTest.class
})
public class BankSystemTestSuite {

}
