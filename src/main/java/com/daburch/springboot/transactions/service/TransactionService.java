package com.daburch.springboot.transactions.service;

import com.daburch.springboot.transactions.dao.TransactionRepository;
import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(@Qualifier("MySQLAutomaticTransactionRepository") TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        // don't allow create if the transaction already exists. Force update
        if (transactionRepository.existsById(transaction.getId())) {
            return null;
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction t : transactionRepository.findAll()) {
            transactions.add(t);
        }

        return transactions;
    }

    public Transaction readTransaction(long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction updateTransaction(long id, Transaction transaction) {
        Transaction transactionToUpdate = readTransaction(id);

        if (transactionToUpdate == null) {
            return null;
        } else {
            transactionToUpdate.setAmount(transaction.getAmount());
            transactionToUpdate.setCategory(transaction.getCategory());
            transactionToUpdate.setDate(transaction.getDate());
            transactionToUpdate.setDescription(transaction.getDescription());
        }

        return transactionRepository.save(transactionToUpdate);
    }

    public boolean deleteTransaction(long id) {
        if (readTransaction(id) == null) return false;

        transactionRepository.deleteById(id);
        return true;
    }
}
