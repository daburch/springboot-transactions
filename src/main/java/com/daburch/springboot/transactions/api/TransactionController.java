package com.daburch.springboot.transactions.api;

import com.daburch.springboot.transactions.model.Transaction;
import com.daburch.springboot.transactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

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
    public UUID createTransaction(@RequestBody @NonNull Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PostMapping("read/{id}")
    public Transaction readTransaction(@PathVariable UUID id) {
        return transactionService.readTransaction(id);
    }

    @PutMapping("{id}")
    public boolean updateTransaction(@PathVariable UUID id, @RequestBody @NonNull Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("{id}")
    public boolean deleteTransaction(@PathVariable UUID id) {
        return transactionService.deleteTransaction(id);
    }
}
