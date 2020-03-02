package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.dao.TransactionDao;
import com.daburch.springboot.transactions.model.Transaction;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class InMemoryTransactionImpl implements TransactionDao {

    private static Set<Transaction> DB;

    static {
        DB = new HashSet<>();
    }

    @Override
    public boolean createTransaction(Transaction transaction) {
        return DB.add(transaction);
    }

    @Override
    public Set<Transaction> getAllTransactions() {
        return DB;
    }

    @Override
    public Transaction readTransaction(UUID id) {
        return DB.stream().filter((transaction) -> {
            return transaction.getId().equals(id);
        }).findFirst().orElse(null);
    }

    @Override
    public boolean updateTransaction(UUID id, Transaction inTransaction) {
        Transaction transactionToUpdate = readTransaction(id);
        if (transactionToUpdate == null) return false;

        transactionToUpdate.setName(inTransaction.getName());
        transactionToUpdate.setCost(inTransaction.getCost());
        transactionToUpdate.setCategory(inTransaction.getCategory());

        return true;
    }

    @Override
    public boolean deleteTransaction(UUID id) {
        Transaction transactionToDelete = readTransaction(id);
        if (transactionToDelete == null) return false;

        return DB.remove(transactionToDelete);
    }
}
