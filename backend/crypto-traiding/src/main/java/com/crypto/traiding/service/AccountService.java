package com.crypto.traiding.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final JdbcTemplate jdbcTemplate;

    public AccountService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BigDecimal getBalance(Long userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    public void deductBalance(Long userId, BigDecimal amount) {
        BigDecimal current = getBalance(userId);
        if (current.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        String sql = "UPDATE users SET balance = balance - ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }

    public void addBalance(Long userId, BigDecimal amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }

    public void resetBalance(Long userId) {
        String sql = "UPDATE users SET balance = 10000.00 WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
