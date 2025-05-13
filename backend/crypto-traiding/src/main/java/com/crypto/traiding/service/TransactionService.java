package com.crypto.traiding.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void recordTransaction(Long userId, String type, String symbol, BigDecimal quantity, BigDecimal price) {
        String sql = """
            INSERT INTO transactions (user_id, type, symbol, quantity, price)
            VALUES (?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql, userId, type, symbol, quantity, price);
    }

    public List<Map<String, Object>> getHistory(Long userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
        return jdbcTemplate.queryForList(sql, userId);
    }

    public void resetTransactions(Long userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);

        BigDecimal initialBalance = new BigDecimal("1000.00");
        String updateSql = "UPDATE users SET balance = ? WHERE id = 1";
        jdbcTemplate.update(updateSql, initialBalance);
    }

    public BigDecimal getTotalBought(Long userId, String symbol) {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM transactions WHERE user_id = ? AND symbol = ? AND type = 'buy'";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, symbol);
    }

    public BigDecimal getTotalSold(Long userId, String symbol) {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM transactions WHERE user_id = ? AND symbol = ? AND type = 'sell'";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, symbol);
    }
}
