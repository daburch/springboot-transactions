package com.daburch.springboot.transactions.service.impl;

import com.daburch.springboot.transactions.dao.MongoTransactionRepository;
import com.daburch.springboot.transactions.dao.SequenceDao;
import com.daburch.springboot.transactions.exception.SequenceException;
import com.daburch.springboot.transactions.exception.ValidationException;
import com.daburch.springboot.transactions.model.Transaction;
import com.daburch.springboot.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final String TRANSACTIONS_SEQ_KEY = "transactions";

    private final CrudRepository<Transaction, Integer> transactionRepository;

    @Autowired(required = false)
    private SequenceDao sequenceDao;

    @Autowired
    public TransactionServiceImpl(CrudRepository<Transaction, Integer> transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) throws ValidationException, SequenceException {
        transaction.validate();

        if (transactionRepository instanceof MongoTransactionRepository) {
            transaction.setId(sequenceDao.getNextSequenceId(TRANSACTIONS_SEQ_KEY));
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

    public Transaction readTransaction(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction updateTransaction(Integer id, Transaction transaction) throws ValidationException {
        transaction.validate();

        Transaction transactionToUpdate = readTransaction(id);
        if (transactionToUpdate == null) return null;

        transactionToUpdate.setAmount(transaction.getAmount());
        transactionToUpdate.setCategory(transaction.getCategory());
        transactionToUpdate.setDate(transaction.getDate());
        transactionToUpdate.setDescription(transaction.getDescription());

        return transactionRepository.save(transactionToUpdate);
    }

    public void deleteTransaction(Integer id) {
        transactionRepository.deleteById(id);
    }
}
