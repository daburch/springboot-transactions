package com.daburch.springboot.transactions.api;

import com.daburch.springboot.transactions.model.Transaction;
import com.daburch.springboot.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RequestMapping("api/v1/transaction")
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Set<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @NonNull Transaction transaction) {
        Transaction retTransaction = transactionService.createTransaction(transaction);
        if (retTransaction != null) {
            return new ResponseEntity<>(retTransaction, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("read/{id}")
    public ResponseEntity<Transaction> readTransaction(@PathVariable UUID id) {
        Transaction transaction = transactionService.readTransaction(id);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateTransaction(@PathVariable UUID id, @RequestBody @NonNull Transaction transaction) {
        if (transactionService.updateTransaction(id, transaction)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteTransaction(@PathVariable UUID id) {
        if (transactionService.deleteTransaction(id)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
