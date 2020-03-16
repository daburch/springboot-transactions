package com.daburch.springboot.transactions.service;

import com.daburch.springboot.transactions.dao.TransactionRepository;
import com.daburch.springboot.transactions.exception.ValidationException;
import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(@Qualifier("database") TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) throws ValidationException {
        transaction.validate();
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

    public Transaction updateTransaction(long id, Transaction transaction) throws ValidationException {
        transaction.validate();

        Transaction transactionToUpdate = readTransaction(id);
        if (transactionToUpdate == null) return null;

        transactionToUpdate.setAmount(transaction.getAmount());
        transactionToUpdate.setCategory(transaction.getCategory());
        transactionToUpdate.setDate(transaction.getDate());
        transactionToUpdate.setDescription(transaction.getDescription());

        return transactionRepository.save(transactionToUpdate);
    }

    public void deleteTransaction(long id) {
        transactionRepository.deleteById(id);
    }
}
