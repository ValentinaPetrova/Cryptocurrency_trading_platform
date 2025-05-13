package com.crypto.traiding.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TradingService {

    private final AccountService accountService;
    private final CryptoPriceService cryptoPriceService;
    private final TransactionService transactionService;

    public TradingService(AccountService accountService,
                          CryptoPriceService cryptoPriceService,
                          TransactionService transactionService) {
        this.accountService = accountService;
        this.cryptoPriceService = cryptoPriceService;
        this.transactionService = transactionService;
    }

    public void buy(Long userId, String symbol, BigDecimal quantity) {
        BigDecimal price = cryptoPriceService.getPrice(symbol);
        BigDecimal cost = price.multiply(quantity);

        accountService.deductBalance(userId, cost);
        transactionService.recordTransaction(userId, "buy", symbol, quantity, price);
    }

    public void sell(Long userId, String symbol, BigDecimal quantity) {
        BigDecimal price = cryptoPriceService.getPrice(symbol);
        BigDecimal gain = price.multiply(quantity);

        accountService.addBalance(userId, gain);
        transactionService.recordTransaction(userId, "sell", symbol, quantity, price);
    }
}
