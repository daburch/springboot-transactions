package com.daburch.springboot.transactions.dao;

import com.daburch.springboot.transactions.model.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public interface TransactionDao {

    boolean createTransaction(Transaction transaction);

    Set<Transaction> getAllTransactions();

    Transaction readTransaction(UUID id);

    boolean updateTransaction(UUID id, Transaction transaction);

    boolean deleteTransaction(UUID id);
}
