package com.crypto.traiding.api.store;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KrakenPriceStore {
    private static final Map<String, BigDecimal> livePrices = new ConcurrentHashMap<>();

    public static void updatePrice(String pair, BigDecimal price) {
        livePrices.put(pair, price);
    }

    public static BigDecimal getPrice(String pair) {
        return livePrices.getOrDefault(pair, BigDecimal.ZERO);
    }

    public static Map<String, BigDecimal> getAllPrices() {
        return Map.copyOf(livePrices);
    }
}
