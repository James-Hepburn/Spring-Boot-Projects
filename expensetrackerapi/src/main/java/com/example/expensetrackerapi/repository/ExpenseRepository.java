package com.example.expensetrackerapi.repository;

import com.example.expensetrackerapi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List <Expense> findByUser (User user);
    List <Expense> findByUserAndDateBetween (User user, LocalDate start, LocalDate end);
}
