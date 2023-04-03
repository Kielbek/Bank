package com.example.bankspring2.Transaction;

import com.example.bankspring2.Enum.TypeOfTransaction;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final LocalDateTime transactionTime = LocalDateTime.now();
    private long senderId;
    private long recipientId;
    private int transactionAmount;
    private TypeOfTransaction transaction;

    public Transaction(long senderId, long recipientId, int transactionAmount, TypeOfTransaction transaction) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.transactionAmount = transactionAmount;
        this.transaction = transaction;
    }

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public TypeOfTransaction getTransaction() {
        return transaction;
    }
}
