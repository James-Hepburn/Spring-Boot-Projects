package com.example.unitconverter.model;

import jakarta.persistence.Entity;

@Entity
public class UnitType {
    private double amount;
    private String symbol;

    public UnitType (double amount, String symbol) {
        this.amount = amount;
        this.symbol = symbol;
    }

    public String toString () {
        return this.amount + " " + this.symbol;
    }
}