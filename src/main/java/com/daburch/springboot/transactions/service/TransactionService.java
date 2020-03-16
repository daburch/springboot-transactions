package com.daburch.springboot.transactions.service;

import com.daburch.springboot.transactions.exception.ValidationException;
import com.daburch.springboot.transactions.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction) throws ValidationException;

    List<Transaction> getAllTransactions();

    Transaction readTransaction(long id);

    Transaction updateTransaction(long id, Transaction transaction) throws ValidationException;

    void deleteTransaction(long id);
}
