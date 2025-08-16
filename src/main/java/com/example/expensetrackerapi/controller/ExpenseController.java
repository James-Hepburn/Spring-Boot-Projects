package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.ExpenseRequest;
import com.example.expensetrackerapi.dto.ExpenseResponse;
import com.example.expensetrackerapi.model.Expense;
import com.example.expensetrackerapi.model.User;
import com.example.expensetrackerapi.service.ExpenseService;
import com.example.expensetrackerapi.service.UserService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
@AllArgsConstructor
public class ExpenseController {
    private ExpenseService expenseService;
    private UserService userService;

    @PostMapping
    public ResponseEntity <ExpenseResponse> createExpense (@RequestBody ExpenseRequest request, Authentication authentication) {
        User user = this.userService.getEntityByUsername (authentication.getName ());
        Expense expense = this.expenseService.createExpense (user, request.getDescription (), request.getAmount (), request.getDate (), request.getCategory ());
        ExpenseResponse response = mapToResponse (expense);
        return ResponseEntity.ok (response);
    }

    @PutMapping("/{id}")
    public ResponseEntity <ExpenseResponse> updateExpense (@PathVariable Long id, @RequestBody ExpenseRequest request, Authentication authentication) {
        User user = this.userService.getEntityByUsername (authentication.getName ());
        Expense expense = this.expenseService.updateExpense (id, request.getDescription (), request.getAmount (), request.getDate (), request.getCategory ());

        if (!expense.getUser ().getId().equals (user.getId ())) {
            return ResponseEntity.status (403).build ();
        }

        return ResponseEntity.ok (mapToResponse (expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <?> deleteExpense (@PathVariable Long id, Authentication authentication) {
        User user = this.userService.getEntityByUsername (authentication.getName ());
        Optional <Expense> expenseOptional = this.expenseService.getExpenseById (id);

        if (expenseOptional.isEmpty ()) {
            return ResponseEntity.notFound ().build ();
        }

        Expense expense = expenseOptional.get ();

        if (!expense.getUser ().getId().equals (user.getId ())) {
            return ResponseEntity.status (403).build ();
        }

        this.expenseService.deleteExpense (id);
        return ResponseEntity.ok ("Expense deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity <ExpenseResponse> getExpenseById (@PathVariable Long id) {
        return this.expenseService.getExpenseById (id)
                .map (this::mapToResponse)
                .map (ResponseEntity::ok)
                .orElse (ResponseEntity.notFound ().build ());
    }

    @GetMapping
    public ResponseEntity <List <ExpenseResponse>> getExpensesByUser (Authentication authentication) {
        User user = this.userService.getEntityByUsername (authentication.getName ());
        List <ExpenseResponse> expenses = this.expenseService.getExpensesByUser (user).stream ()
                .map (this::mapToResponse)
                .collect (Collectors.toList ());
        return ResponseEntity.ok (expenses);
    }

    @GetMapping("/range")
    public ResponseEntity <List <ExpenseResponse>> getExpensesByUserAndDateRange (@RequestParam String start, @RequestParam String end, Authentication authentication) {
        User user = this.userService.getEntityByUsername (authentication.getName ());
        List <ExpenseResponse> expenses = this.expenseService.getExpensesByUserAndDateRange (user, LocalDate.parse (start), LocalDate.parse (end))
                .stream ()
                .map (this::mapToResponse)
                .collect (Collectors.toList ());
        return ResponseEntity.ok (expenses);
    }

    private ExpenseResponse mapToResponse (Expense expense) {
        return new ExpenseResponse (
                expense.getId (),
                expense.getDescription (),
                expense.getAmount (),
                expense.getDate (),
                expense.getCategory (),
                expense.getUser ().getUsername ()
        );
    }
}
