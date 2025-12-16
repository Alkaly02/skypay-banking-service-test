package dev.lka;

import dev.lka.exception.CustomException;
import dev.lka.service.Account;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Account account = new Account(0);

        try {
            // Deposit 1000 on 10-01-2012
            account.deposit(1000, LocalDate.of(2012, 1, 10));
        } catch (CustomException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

        try {
            // Deposit 2000 on 13-01-2012
            account.deposit(2000, LocalDate.of(2012, 1, 13));
        } catch (CustomException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

        try {
            // Withdraw 500 on 14-01-2012
            account.withdraw(500, LocalDate.of(2012, 1, 14));
        } catch (CustomException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

        // Print statement
        account.printStatement();
    }
}
