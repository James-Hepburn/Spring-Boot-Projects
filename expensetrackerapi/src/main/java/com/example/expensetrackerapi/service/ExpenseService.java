package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.model.*;
import com.example.expensetrackerapi.repository.ExpenseRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
@Service
public class ExpenseService {
    private ExpenseRepository repo;

    public Expense createExpense (User user, String description, double amount, LocalDate date, Expense.Category category) {
        Expense expense = new Expense (description, amount, date, category);
        user.getExpenses ().add (expense);
        expense.setUser (user);
        return this.repo.save (expense);
    }

    public Expense updateExpense (Long expenseId, String description, double amount, LocalDate date, Expense.Category category) {
        Optional <Expense> expenseOptional = getExpenseById (expenseId);

        if (expenseOptional.isPresent ()) {
            Expense expense = expenseOptional.get ();

            expense.setDescription (description);
            expense.setAmount (amount);
            expense.setDate (date);
            expense.setCategory (category);

            return this.repo.save (expense);
        }

        throw new RuntimeException ("Expense not found.");
    }

    public void deleteExpense (Long expenseId) {
        Optional <Expense> expenseOptional = getExpenseById (expenseId);

        if (expenseOptional.isPresent ()) {
            Expense expense = expenseOptional.get ();
            this.repo.delete (expense);
        } else {
            throw new RuntimeException ("Expense not found.");
        }
    }

    public Optional <Expense> getExpenseById (Long expenseId) {
        return this.repo.findById (expenseId);
    }

    public List <Expense> getExpensesByUser (User user) {
        return this.repo.findByUser (user);
    }

    public List <Expense> getExpensesByUserAndDateRange (User user, LocalDate start, LocalDate end) {
        return this.repo.findByUserAndDateBetween (user, start, end);
    }
}
