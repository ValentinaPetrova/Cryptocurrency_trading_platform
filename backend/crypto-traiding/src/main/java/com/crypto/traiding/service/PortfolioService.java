package com.crypto.traiding.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {

    //private final TradingService tradingService;
    private final CryptoPriceService priceService;
    private final TransactionService transactionService;

    public PortfolioService(TradingService tradingService, 
                            CryptoPriceService priceService,
                            TransactionService transactionService) {
        //this.tradingService = tradingService;
        this.priceService = priceService;
        this.transactionService = transactionService;
    }

    // Method to get current holdings of all cryptocurrencies for a user
    public Map<String, BigDecimal> getCurrentHoldings(Long userId) {
        List<Map<String, Object>> transactions = transactionService.getHistory(userId);

        Map<String, BigDecimal> holdings = new HashMap<>();

        for (Map<String, Object> transaction : transactions) {
            String symbol = (String) transaction.get("symbol");
            BigDecimal quantity = (BigDecimal) transaction.get("quantity");
            String type = (String) transaction.get("type");

            // Adjust the holdings based on whether the transaction is 'buy' or 'sell'
            holdings.put(symbol, holdings.getOrDefault(symbol, BigDecimal.ZERO)
                    .add(type.equals("buy") ? quantity : quantity.negate()));
        }

        return holdings;
    }

    // Method to calculate the profit/loss of the user's portfolio based on transactions
    public BigDecimal calculateProfitLoss(Long userId) {
        Map<String, BigDecimal> holdings = getCurrentHoldings(userId);

        BigDecimal totalProfitLoss = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            BigDecimal holdingQuantity = entry.getValue();

            // Calculate the total amount the user spent on their current holdings
            List<Map<String, Object>> userTransactions = transactionService.getHistory(userId);
            BigDecimal totalCost = BigDecimal.ZERO;

            for (Map<String, Object> transaction : userTransactions) {
                String transSymbol = (String) transaction.get("symbol");
                BigDecimal quantity = (BigDecimal) transaction.get("quantity");
                BigDecimal price = (BigDecimal) transaction.get("price");
                String type = (String) transaction.get("type");

                // If the symbol matches and it's a "buy" transaction, calculate total cost
                if (transSymbol.equals(symbol) && type.equals("buy")) {
                    totalCost = totalCost.add(price.multiply(quantity));
                }
            }

            // Get the current market price for the crypto
            BigDecimal currentPrice = priceService.getPrice(symbol);

            // Calculate profit or loss (current value - total cost)
            BigDecimal currentValue = holdingQuantity.multiply(currentPrice);
            BigDecimal profitLoss = currentValue.subtract(totalCost);

            totalProfitLoss = totalProfitLoss.add(profitLoss);
        }

        return totalProfitLoss;
    }

    // Method to calculate the total value of the portfolio based on current prices
    public BigDecimal calculatePortfolioValue(Long userId) {
        Map<String, BigDecimal> holdings = getCurrentHoldings(userId);
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            BigDecimal holdingQuantity = entry.getValue();

            // Get the current market price for the symbol
            BigDecimal currentPrice = priceService.getPrice(symbol);

            // Multiply holdings by current price to get total value for the symbol
            BigDecimal currentValue = holdingQuantity.multiply(currentPrice);
            totalValue = totalValue.add(currentValue);
        }

        return totalValue;
    }
}
