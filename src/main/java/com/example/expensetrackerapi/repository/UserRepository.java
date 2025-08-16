package com.example.expensetrackerapi.repository;

import com.example.expensetrackerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername (String username);
    boolean existsByUsername (String username);
}
