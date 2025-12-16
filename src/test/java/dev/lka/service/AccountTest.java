package dev.lka.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.lka.model.Transaction;
import dev.lka.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.lka.exception.CustomException;

class AccountTest {

    private Account account;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        account = new Account(0);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testAcceptanceTestScenario() {
        // Given: deposits and withdrawal as per acceptance test
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        account.withdraw(500, LocalDate.of(2012, 1, 14));

        // When: print statement
        account.printStatement();

        // Then: verify output contains expected transactions
        String output = outputStream.toString();
        assertTrue(output.contains("10-01-2012"));
        assertTrue(output.contains("13-01-2012"));
        assertTrue(output.contains("14-01-2012"));
        assertTrue(output.contains("1000"));
        assertTrue(output.contains("2000"));
        assertTrue(output.contains("-500"));
        assertTrue(output.contains("2500")); // Final balance
    }

    @Test
    void testDepositPositiveAmount() {
        account.deposit(1000);
        assertEquals(1000, account.getBalance());
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void testDepositWithDate() {
        LocalDate date = LocalDate.of(2023, 5, 15);
        account.deposit(500, date);

        List<Transaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("15-05-2023", transactions.get(0).date());
        assertEquals(500, transactions.get(0).amount());
        Assertions.assertEquals(TransactionType.DEPOSIT, transactions.get(0).type());
    }

    @Test
    void testDepositZeroAmountThrowsException() {
        CustomException exception = assertThrows(CustomException.class, () -> account.deposit(0));
        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    void testDepositNegativeAmountThrowsException() {
        CustomException exception = assertThrows(CustomException.class, () -> account.deposit(-100));

        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    void testWithdrawPositiveAmount() {
        account.deposit(1000);
        account.withdraw(300);
        assertEquals(700, account.getBalance());
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    void testWithdrawWithDate() {
        account.deposit(1000, LocalDate.of(2023, 1, 1));
        LocalDate withdrawDate = LocalDate.of(2023, 1, 2);
        account.withdraw(300, withdrawDate);

        List<Transaction> transactions = account.getTransactions();
        assertEquals(2, transactions.size());
        Transaction withdrawTransaction = transactions.get(0);
        assertEquals("02-01-2023", withdrawTransaction.date());
        assertEquals(300, withdrawTransaction.amount());
        assertEquals(TransactionType.WITHDRAW, withdrawTransaction.type());
        assertEquals(700, withdrawTransaction.balance());
    }

    @Test
    void testWithdrawZeroAmountThrowsException() {
        account.deposit(1000);
        CustomException exception = assertThrows(CustomException.class, () -> account.withdraw(0));

        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    void testWithdrawNegativeAmountThrowsException() {
        account.deposit(1000);
        CustomException exception = assertThrows(CustomException.class, () -> account.withdraw(-100));

        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }

    @Test
    void testWithdrawInsufficientBalanceThrowsException() {
        account.deposit(100);
        CustomException exception = assertThrows(CustomException.class, () -> account.withdraw(200));

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testMultipleTransactions() {
        account.deposit(1000, LocalDate.of(2023, 1, 1));
        account.deposit(2000, LocalDate.of(2023, 1, 2));
        account.withdraw(500, LocalDate.of(2023, 1, 3));
        account.withdraw(300, LocalDate.of(2023, 1, 4));

        assertEquals(2200, account.getBalance());
        assertEquals(4, account.getTransactions().size());
    }

    @Test
    void testStatementOrder() {
        account.deposit(1000, LocalDate.of(2023, 1, 1));
        account.deposit(2000, LocalDate.of(2023, 1, 2));
        account.withdraw(500, LocalDate.of(2023, 1, 3));

        account.printStatement();
        String output = outputStream.toString();

        // Verify transactions appear in chronological order (oldest first)
        int index1 = output.indexOf("01-01-2023");
        int index2 = output.indexOf("02-01-2023");
        int index3 = output.indexOf("03-01-2023");

        assertTrue(index1 > index2 && index2 > index3,
            "Transactions should be in chronological order");
    }

    @Test
    void testInitialBalance() {
        Account accountWithBalance = new Account(500);
        assertEquals(500, accountWithBalance.getBalance());
    }

    @Test
    void testStatementFormat() {
        account.deposit(1000, LocalDate.of(2023, 1, 10));
        account.printStatement();

        String output = outputStream.toString();
        assertTrue(output.contains("Date       || Amount || Balance"));
        assertTrue(output.contains("10-01-2023"));
        assertTrue(output.contains("1000"));
    }

    @Test
    void testWithdrawShowsNegativeAmount() {
        account.deposit(1000);
        account.withdraw(300);
        account.printStatement();

        String output = outputStream.toString();
        assertTrue(output.contains("-300"));
    }
}
