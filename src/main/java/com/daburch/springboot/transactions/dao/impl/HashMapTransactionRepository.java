package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository("HashMapTransactionRepository")
@Profile("h2")
public class HashMapTransactionRepository implements CrudRepository<Transaction, Integer> {

    private static Integer id;
    private static Map<Integer, Transaction> DB;

    @Autowired
    public HashMapTransactionRepository() {
        id = 1;
        DB = new HashMap<>();
    }

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
    public Optional<Transaction> findById(Integer aLong) {
        return Optional.ofNullable(DB.get(aLong));
    }

    @Override
    public boolean existsById(Integer aLong) {
        return DB.containsKey(aLong);
    }

    @Override
    public Iterable<Transaction> findAll() {
        return DB.values();
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<Integer> longs) {
        return null;
    }

    @Override
    public long count() {
        return DB.size();
    }

    @Override
    public void deleteById(Integer aLong) {
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
