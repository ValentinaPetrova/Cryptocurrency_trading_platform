package com.crypto.traiding.controller;

import com.crypto.traiding.service.CryptoPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class CryptoController {

    private final CryptoPriceService cryptoPriceService;

    public CryptoController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    // Get the current price of a specific cryptocurrency
    @GetMapping("/api/crypto/price")
    public BigDecimal getCryptoPrice(@RequestParam String symbol) {
        return cryptoPriceService.getPrice(symbol);
    }

    // Get top 20 cryptocurrencies' prices
    @GetMapping("/api/crypto/top20")
    public Map<String, BigDecimal> getTop20CryptoPrices() {
        return cryptoPriceService.getTop20Prices();
    }
}
