package dev.lka.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dev.lka.exception.CustomException;
import dev.lka.model.Transaction;
import dev.lka.model.TransactionType;

public class Account implements AccountService {
    private int balance;
    private List<Transaction> transactions;
    private DateTimeFormatter dateFormatter;

    public Account() {
        this(0);
    }

    public Account(int initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @Override
    public void deposit(int amount) {
        deposit(amount, LocalDate.now());
    }

    public void deposit(int amount, LocalDate date) {
        if (amount <= 0) {
            throw new CustomException("Deposit amount must be positive");
        }

        this.balance += amount;
        String formattedDate = date.format(dateFormatter);
        Transaction newTransaction = new Transaction(formattedDate, amount, this.balance, TransactionType.DEPOSIT);
        transactions.add(0, newTransaction);
    }

    @Override
    public void withdraw(int amount) {
        withdraw(amount, LocalDate.now());
    }

    public void withdraw(int amount, LocalDate date) {
        if (amount <= 0) {
            throw new CustomException("Withdrawal amount must be positive");
        }
        if (this.balance < amount) {
            throw new CustomException("Insufficient balance");
        }

        this.balance -= amount;
        String formattedDate = date.format(dateFormatter);
        Transaction newTransaction = new Transaction(formattedDate, amount, this.balance, TransactionType.WITHDRAW);
        transactions.add(0, newTransaction);
    }

    @Override
    public void printStatement() {
        System.out.println("Date       || Amount || Balance");
        transactions.forEach(transaction -> {
            String amountStr = transaction.type() == TransactionType.DEPOSIT
                ? String.valueOf(transaction.amount())
                : "-" + transaction.amount();
            System.out.println(
                transaction.date() + " || " +
                amountStr + " || " +
                transaction.balance()
            );
        });
    }

    public int getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
