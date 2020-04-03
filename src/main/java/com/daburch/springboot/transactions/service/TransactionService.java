package com.daburch.springboot.transactions.service;

import com.daburch.springboot.transactions.exception.SequenceException;
import com.daburch.springboot.transactions.exception.ValidationException;
import com.daburch.springboot.transactions.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction) throws ValidationException, SequenceException;

    List<Transaction> getAllTransactions();

    Transaction readTransaction(Integer id);

    Transaction updateTransaction(Integer id, Transaction transaction) throws ValidationException;

    void deleteTransaction(Integer id);
}
