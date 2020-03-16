package com.daburch.springboot.transactions.dao;

import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
