package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.dao.TransactionRepository;
import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Repository("MySQLManualTransactionRepository")
public class MySqlTransactionRepository implements TransactionRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleDateFormat sdf;

    @Autowired
    public MySqlTransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");
    }

    @Override
    public <S extends Transaction> S save(S entity) {
        final String sql ="insert into transactions ( amount, description, date, category ) values (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1, entity.getAmount());
            ps.setString(2, entity.getDescription());
            ps.setString(3, sdf.format(entity.getDate()));
            ps.setString(4, entity.getCategory());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            entity.setId(keyHolder.getKey().longValue());
        }

        return entity;
    }

    @Override
    public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Transaction> findById(Long aLong) {
        final String sql = "SELECT id, description, amount, category, date FROM transactions WHERE id = ?";

        List<Transaction> transactions = jdbcTemplate.query(sql, new Object[]{aLong}, (resultSet, i) ->
                new Transaction(resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("category"),
                        resultSet.getDate("date")));

        if (transactions.size() == 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(transactions.get(0));
    }

    @Override
    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }

    @Override
    public Iterable<Transaction> findAll() {
        final String sql = "SELECT id, description, amount, category, date FROM transactions";
        return jdbcTemplate.query(sql, (resultSet, i) -> new Transaction(resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("category"),
                        resultSet.getDate("date")));
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        String deleteQuery = "delete from transactions where id = ?";
        jdbcTemplate.update(deleteQuery, aLong);
    }

    @Override
    public void delete(Transaction entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Transaction> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {

    }
}
