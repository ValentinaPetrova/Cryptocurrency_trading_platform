package com.crypto.traiding.controller;

import com.crypto.traiding.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Get transaction history of a user
    @GetMapping("/api/transactions/{userId}")
    public List<Map<String, Object>> getTransactionHistory(@PathVariable Long userId) {
        return transactionService.getHistory(userId);
    }

    // Reset the account balance of a user
    @PostMapping("/api/transactions/reset")
    public String resetAccountBalance(@RequestParam Long userId) {
        try {
            transactionService.resetTransactions(userId);
            return "Account balance reset to initial value!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
