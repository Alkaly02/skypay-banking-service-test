package dev.lka.model;

public record Transaction(
        String date,
        int amount,
        int balance,
        TransactionType type
) {
}
