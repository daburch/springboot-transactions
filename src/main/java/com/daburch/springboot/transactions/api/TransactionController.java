package com.daburch.springboot.transactions.api;

import com.daburch.springboot.transactions.exception.SequenceException;
import com.daburch.springboot.transactions.exception.ValidationException;
import com.daburch.springboot.transactions.model.Transaction;
import com.daburch.springboot.transactions.service.TransactionService;
import com.daburch.springboot.transactions.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("api/v1/transaction")
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @NonNull Transaction transaction) {
        Transaction retTransaction;
        try {
            retTransaction = transactionService.createTransaction(transaction);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (SequenceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (retTransaction != null) {
            return new ResponseEntity<>(retTransaction, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("read/{id}")
    public ResponseEntity<Transaction> readTransaction(@PathVariable Integer id) {
        Transaction transaction = transactionService.readTransaction(id);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Integer id, @RequestBody @NonNull Transaction transaction) {
        Transaction returnTransaction;
        try {
            returnTransaction = transactionService.updateTransaction(id, transaction);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (returnTransaction != null) {
            return new ResponseEntity<>(returnTransaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
