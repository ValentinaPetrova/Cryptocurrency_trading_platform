package com.crypto.traiding.model;

public class User {
    private int id;
    private Double balance;

    // Constructors
    public User() {}

    public User(int id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
