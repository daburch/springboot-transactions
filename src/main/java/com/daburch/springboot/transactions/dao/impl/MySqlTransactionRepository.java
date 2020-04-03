package com.daburch.springboot.transactions.dao.impl;

import com.daburch.springboot.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository("MySQLTransactionRepository")
@Profile({"mysql_local", "mysql_docker"})
public class MySqlTransactionRepository implements CrudRepository<Transaction, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlTransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Transaction> S save(S entity) {
        final String sql ="insert into transactions ( amount, description, date, category ) values (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1, entity.getAmount());
            ps.setString(2, entity.getDescription());
            ps.setTimestamp(3, new java.sql.Timestamp(entity.getDate().getTime()));
            ps.setString(4, entity.getCategory());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            entity.setId(keyHolder.getKey().intValue());
        }

        return entity;
    }

    @Override
    public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Transaction> findById(Integer aLong) {
        final String sql = "SELECT id, description, amount, category, date FROM transactions WHERE id = ?";

        List<Transaction> transactions = jdbcTemplate.query(sql, new Object[]{aLong}, (resultSet, i) ->
                new Transaction(resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("category"),
                        resultSet.getTimestamp("date")));

        if (transactions.size() == 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(transactions.get(0));
    }

    @Override
    public boolean existsById(Integer aLong) {
        return findById(aLong).isPresent();
    }

    @Override
    public Iterable<Transaction> findAll() {
        final String sql = "SELECT id, description, amount, category, date FROM transactions";
        return jdbcTemplate.query(sql, (resultSet, i) -> new Transaction(resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("category"),
                        resultSet.getTimestamp("date")));
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<Integer> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer aLong) {
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
