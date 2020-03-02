package com.daburch.springboot.transactions.model;

import java.util.UUID;

public class Transaction {
    private UUID id;
    private String name;
    private float cost;
    private TransactionCategory category;

    public Transaction(UUID id, String name, float cost, TransactionCategory category) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
}
