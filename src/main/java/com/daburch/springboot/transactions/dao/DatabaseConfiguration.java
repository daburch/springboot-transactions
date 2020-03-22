package com.daburch.springboot.transactions.dao;

import com.daburch.springboot.transactions.dao.impl.HashMapTransactionRepository;
import com.daburch.springboot.transactions.dao.impl.MySqlTransactionRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseConfiguration {

    @Bean(name="database")
    @Profile("dev")
    public TransactionRepository mySqlTransactionRepository(JdbcTemplate jdbcTemplate) {
        return new MySqlTransactionRepository(jdbcTemplate);
    }

    @Bean(name="database")
    @Profile("test")
    public TransactionRepository inMemoryTransactionRepository() {
        return new HashMapTransactionRepository();
    }
}
