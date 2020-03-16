package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.dao.TransactionRepository;
import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository("HashMapTransactionRepository")
public class HashMapTransactionRepository implements TransactionRepository {

    private static long id;
    private static HashMap<Long, Transaction> DB;

    static {
        id = 1;
        DB = new HashMap<>();
    }

    @Autowired
    public HashMapTransactionRepository() { }

    @Override
    public <S extends Transaction> S save(S entity) {
        entity.setId(id++);
        DB.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Transaction> findById(Long aLong) {
        return Optional.ofNullable(DB.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return DB.containsKey(aLong);
    }

    @Override
    public Iterable<Transaction> findAll() {
        return DB.values();
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return DB.size();
    }

    @Override
    public void deleteById(Long aLong) {
        DB.remove(aLong);
    }

    @Override
    public void delete(Transaction entity) {
        DB.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Transaction> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        DB.clear();
    }
}
