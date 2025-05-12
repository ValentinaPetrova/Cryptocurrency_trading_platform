package com.crypto.traiding.service;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import com.crypto.traiding.crypto_traiding.CryptoTraidingApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CryptoTraidingApplication.class)
class TransactionServiceTest {

    @Test
    void testRecordTransaction() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        TransactionService transactionService = new TransactionService(jdbcTemplate);

        transactionService.recordTransaction(1L, "buy", "ETH", BigDecimal.valueOf(5), BigDecimal.valueOf(2000));

        verify(jdbcTemplate).update(anyString(), eq(1L), eq("buy"), eq("ETH"), eq(BigDecimal.valueOf(5)), eq(BigDecimal.valueOf(2000)));
    }
}
