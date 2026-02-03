package com.example.todolistapi.repository;

import com.example.todolistapi.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository <Todo, Long> {
    Optional <Todo> findByIdAndUser (Long id, User user);
    Page <Todo> findAllByUser (User user, Pageable pageable);
}