package com.crypto.traiding.service;

import com.crypto.traiding.api.KrakenWebSocketService;
import com.crypto.traiding.api.dto.KrakenTickerResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CryptoPriceService {

    private final KrakenWebSocketService krakenWebSocketService;
    private final Map<String, String> symbolToPair;

    public CryptoPriceService(KrakenWebSocketService krakenWebSocketService) {
        this.krakenWebSocketService = krakenWebSocketService;
        this.symbolToPair = initializeSymbolMapping();
    }

    // Fetch real-time price for a symbol (e.g., BTC, ETH)
    public BigDecimal getPrice(String symbol) {
        String pair = symbolToPair.get(symbol.toUpperCase());
        if (pair == null) {
            throw new IllegalArgumentException("Unsupported cryptocurrency symbol: " + symbol);
        }

        KrakenTickerResponse response = krakenWebSocketService.getTicker(pair);
        if (response == null || response.getResult() == null) {
            throw new RuntimeException("Invalid response from Kraken WebSocket");
        }

        // Extract the price for the pair
        switch (pair) {
            case "XXBTZUSD": return new BigDecimal(response.getResult().btcusd().getAskPrice()[0]);
            case "XETHZUSD": return new BigDecimal(response.getResult().ethusd().getAskPrice()[0]);
            case "XLTCZUSD": return new BigDecimal(response.getResult().ltcusd().getAskPrice()[0]);
            default: throw new IllegalArgumentException("Unsupported pair: " + pair);
        }
    }

    public Map<String, BigDecimal> getTop20Prices() {
        Map<String, BigDecimal> prices = new HashMap<>();
        prices.put("BTC", getPrice("BTC"));
        prices.put("ETH", getPrice("ETH"));
        prices.put("LTC", getPrice("LTC"));
        // You can expand this with more pairs as needed
        return prices;
    }

    // Initialize mappings from symbols to Kraken pairs
    private Map<String, String> initializeSymbolMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("BTC", "XXBTZUSD");
        mapping.put("ETH", "XETHZUSD");
        mapping.put("LTC", "XLTCZUSD");
        // More mappings can go here
        return mapping;
    }
}
