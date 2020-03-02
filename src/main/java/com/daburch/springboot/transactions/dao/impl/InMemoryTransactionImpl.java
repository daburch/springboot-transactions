package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.dao.TransactionDao;
import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository("inMemoryTransactionDao")
public class InMemoryTransactionImpl implements TransactionDao {

    private static HashMap<UUID, Transaction> DB;

    static {
        DB = new HashMap<>();
    }

    @Override
    public boolean createTransaction(Transaction transaction) {
        if (DB.containsKey(transaction.getId())) return false;
        DB.put(transaction.getId(), transaction);
        return true;
    }

    @Override
    public Set<Transaction> getAllTransactions() {
        return new HashSet<>(DB.values());
    }

    @Override
    public Transaction readTransaction(UUID id) {
        return DB.get(id);
    }

    @Override
    public boolean updateTransaction(UUID id, Transaction inTransaction) {
        if (!DB.containsKey(id)) return false;

        DB.put(id, inTransaction);
        return true;
    }

    @Override
    public boolean deleteTransaction(UUID id) {
        if (!DB.containsKey(id)) return false;
        DB.remove(id);
        return true;
    }
}
