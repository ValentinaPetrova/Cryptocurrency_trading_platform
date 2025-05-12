package com.crypto.traiding.service;

import com.crypto.traiding.api.store.KrakenPriceStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CryptoPriceService {

    private final Map<String, String> symbolToPair;

    public CryptoPriceService() {
        this.symbolToPair = initializeSymbolMapping();
    }

    public BigDecimal getPrice(String symbol) {
        String pair = symbolToPair.get(symbol.toUpperCase());
        if (pair == null) {
            throw new IllegalArgumentException("Unsupported cryptocurrency symbol: " + symbol);
        }

        BigDecimal price = KrakenPriceStore.getPrice(pair);
        if (price == null) {
            throw new RuntimeException("Price not available yet for: " + symbol);
        }

        return price;
    }

    public Map<String, BigDecimal> getTop20Prices() {
        Map<String, BigDecimal> prices = new LinkedHashMap<>();
        prices.put("BTC", getPrice("BTC"));
        prices.put("ETH", getPrice("ETH"));
        prices.put("LTC", getPrice("LTC"));
        prices.put("ADA", getPrice("ADA"));
        prices.put("DOT", getPrice("DOT"));
        prices.put("XRP", getPrice("XRP"));
        prices.put("SOL", getPrice("SOL"));
        prices.put("AVAX", getPrice("AVAX"));
        prices.put("MATIC", getPrice("MATIC"));
        prices.put("LINK", getPrice("LINK"));
        prices.put("BCH", getPrice("BCH"));
        prices.put("XLM", getPrice("XLM"));
        prices.put("ATOM", getPrice("ATOM"));
        prices.put("UNI", getPrice("UNI"));
        prices.put("DOGE", getPrice("DOGE"));
        prices.put("SHIB", getPrice("SHIB"));
        prices.put("TRX", getPrice("TRX"));
        prices.put("ALGO", getPrice("ALGO"));
        prices.put("ETC", getPrice("ETC"));
        prices.put("EOS", getPrice("EOS"));
        return  prices;
    }

    private Map<String, String> initializeSymbolMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("BTC", "XBT/USD");
        mapping.put("ETH", "ETH/USD");
        mapping.put("LTC", "LTC/USD");
        mapping.put("ADA", "ADA/USD");
        mapping.put("DOT", "DOT/USD");
        mapping.put("XRP", "XRP/USD");
        mapping.put("SOL", "SOL/USD");
        mapping.put("AVAX", "AVAX/USD");
        mapping.put("MATIC", "MATIC/USD");
        mapping.put("LINK", "LINK/USD");
        mapping.put("BCH", "BCH/USD");
        mapping.put("XLM", "XLM/USD");
        mapping.put("ATOM", "ATOM/USD");
        mapping.put("UNI", "UNI/USD");
        mapping.put("DOGE", "DOGE/USD");
        mapping.put("SHIB", "SHIB/USD");
        mapping.put("TRX", "TRX/USD");
        mapping.put("ALGO", "ALGO/USD");
        mapping.put("ETC", "ETC/USD");
        mapping.put("EOS", "EOS/USD");
        return mapping;
    }

}
