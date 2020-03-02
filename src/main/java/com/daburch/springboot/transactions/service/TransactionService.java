package com.daburch.springboot.transactions.service;

import com.daburch.springboot.transactions.dao.TransactionDao;
import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionService(@Qualifier("inMemoryTransactionDao") TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public UUID createTransaction(Transaction transaction) {
        if (!transaction.validate()) {
            return null;
        }

        if(transactionDao.createTransaction(transaction)) {
            return transaction.getId();
        } else {
            return null;
        }
    }

    public Set<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public Transaction readTransaction(UUID id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        return transactionDao.readTransaction(id);
    }

    public boolean updateTransaction(UUID id, Transaction transaction) {
        if (StringUtils.isEmpty(transaction.getId())) {
            transaction.setId(id);
        } else if (!transaction.getId().equals(id)) {
            return false;
        }

        if (!transaction.validate()) {
            return false;
        }

        return transactionDao.updateTransaction(id, transaction);
    }

    public boolean deleteTransaction(UUID id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }

        return transactionDao.deleteTransaction(id);
    }
}
