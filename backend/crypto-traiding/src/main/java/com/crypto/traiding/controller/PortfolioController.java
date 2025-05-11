package com.crypto.traiding.controller;

import com.crypto.traiding.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // Get current holdings of a user
    @GetMapping("/api/portfolio/{userId}/holdings")
    public Map<String, BigDecimal> getPortfolioHoldings(@PathVariable Long userId) {
        return portfolioService.getCurrentHoldings(userId);
    }

    // Calculate profit/loss of the portfolio
    @GetMapping("/api/portfolio/{userId}/profit-loss")
    public BigDecimal calculatePortfolioProfitLoss(@PathVariable Long userId) {
        return portfolioService.calculateProfitLoss(userId);
    }

    // Get the total value of the portfolio
    @GetMapping("/api/portfolio/{userId}/value")
    public BigDecimal calculatePortfolioValue(@PathVariable Long userId) {
        return portfolioService.calculatePortfolioValue(userId);
    }
}
