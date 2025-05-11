package com.crypto.traiding.controller;

import com.crypto.traiding.repo.UserRepo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/{id}/reset")
    public void resetUserBalance(@PathVariable int id) {
        userRepo.resetUserBalance(id);
    }

    @GetMapping("/{id}/balance")
    public double getUserBalance(@PathVariable int id) {
        return userRepo.getUserBalance(id);
    }

    @PostMapping("/init")
    public void createUserIfNotExists() {
        userRepo.createUserIfNotExists();
    }
}
