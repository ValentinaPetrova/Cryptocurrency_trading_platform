package com.crypto.traiding.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.crypto.traiding.crypto_traiding.CryptoTraidingApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CryptoTraidingApplication.class)
class TradingServiceTest {

    private AccountService accountService;
    private CryptoPriceService priceService;
    private TransactionService transactionService;
    private TradingService tradingService;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        priceService = mock(CryptoPriceService.class);
        transactionService = mock(TransactionService.class);
        tradingService = new TradingService(accountService, priceService, transactionService);
    }

    @Test
    void testBuyWithSufficientBalance() {
        when(priceService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(10));
        when(accountService.getBalance(1L)).thenReturn(BigDecimal.valueOf(200));

        tradingService.buy(1L, "BTC", BigDecimal.valueOf(10));

        verify(accountService).deductBalance(1L, BigDecimal.valueOf(100));
        verify(transactionService).recordTransaction(1L, "buy", "BTC", BigDecimal.valueOf(10), BigDecimal.valueOf(10));
    }

    @Test
    void testBuyWithInsufficientBalance() {
        when(priceService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(10));
        when(accountService.getBalance(1L)).thenReturn(BigDecimal.valueOf(50));

        Exception e = assertThrows(IllegalArgumentException.class, () ->
            tradingService.buy(1L, "BTC", BigDecimal.valueOf(10))
        );
        assertEquals("Insufficient balance to complete purchase.", e.getMessage());
    }

    @Test
    void testSellWithSufficientHoldings() {
        List<Map<String, Object>> history = List.of(
            Map.of("symbol", "BTC", "type", "buy", "quantity", 10),
            Map.of("symbol", "BTC", "type", "sell", "quantity", 2)
        );
        when(transactionService.getHistory(1L)).thenReturn(history);
        when(priceService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(20));

        tradingService.sell(1L, "BTC", BigDecimal.valueOf(5));

        verify(accountService).addBalance(1L, BigDecimal.valueOf(100));
        verify(transactionService).recordTransaction(1L, "sell", "BTC", BigDecimal.valueOf(5), BigDecimal.valueOf(20));
    }

    @Test
    void testSellWithInsufficientHoldings() {
        List<Map<String, Object>> history = List.of(
            Map.of("symbol", "BTC", "type", "buy", "quantity", 3)
        );
        when(transactionService.getHistory(1L)).thenReturn(history);

        Exception e = assertThrows(IllegalArgumentException.class, () ->
            tradingService.sell(1L, "BTC", BigDecimal.valueOf(5))
        );
        assertEquals("Insufficient holdings to complete sale.", e.getMessage());
    }

    @Test
    void testBuyWithNegativeQuantity() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            tradingService.buy(1L, "BTC", BigDecimal.valueOf(-10))
        );
        assertEquals("Quantity must be greater than zero.", e.getMessage());
    }
}
