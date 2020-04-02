package com.daburch.springboot.transactions.dao;

import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("MongoTransactionRepository")
@Profile("mongo")
public interface MongoTransactionRepository extends MongoRepository<Transaction, Integer> {

}
