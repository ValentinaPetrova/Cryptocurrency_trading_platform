package com.crypto.traiding.controller;

import com.crypto.traiding.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserController {

    private final AccountService accountService;

    public UserController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Get current balance of a user
    @GetMapping("/api/user/balance")
    public BigDecimal getUserBalance(@RequestParam Long userId) {
        return accountService.getBalance(userId);
    }

    // Reset user's account balance
    @PostMapping("/api/user/reset")
    public String resetUserBalance(@RequestParam Long userId) {
        try {
            accountService.resetBalance(userId);
            return "Account balance reset!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
