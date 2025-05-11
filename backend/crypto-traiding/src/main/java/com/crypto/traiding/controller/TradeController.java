package com.crypto.traiding.controller;

import com.crypto.traiding.service.TradingService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class TradeController {

    private final TradingService tradingService;

    public TradeController(TradingService tradingService) {
        this.tradingService = tradingService;
    }

    // Buy cryptocurrency
    @PostMapping("/api/trade/buy")
    public String buyCrypto(@RequestParam Long userId, @RequestParam String symbol, @RequestParam BigDecimal quantity) {
        try {
            tradingService.buy(userId, symbol, quantity);
            return "Purchase successful!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Sell cryptocurrency
    @PostMapping("/api/trade/sell")
    public String sellCrypto(@RequestParam Long userId, @RequestParam String symbol, @RequestParam BigDecimal quantity) {
        try {
            tradingService.sell(userId, symbol, quantity);
            return "Sale successful!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
