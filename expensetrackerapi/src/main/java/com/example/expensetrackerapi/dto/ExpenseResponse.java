package com.example.expensetrackerapi.dto;

import com.example.expensetrackerapi.model.Expense;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String description;
    private double amount;
    private LocalDate date;
    private Expense.Category category;
    private String username;
}
