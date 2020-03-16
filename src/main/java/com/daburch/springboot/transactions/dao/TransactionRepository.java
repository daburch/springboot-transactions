package com.daburch.springboot.transactions.dao;

import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
