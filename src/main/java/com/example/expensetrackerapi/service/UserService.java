package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.model.User;
import com.example.expensetrackerapi.repository.UserRepository;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@AllArgsConstructor
@Service
public class UserService {
    private UserRepository repo;
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser (String username, String password) {
        if (this.repo.existsByUsername (username)) {
            throw new RuntimeException ("User already exists.");
        }

        String hashedPassword = this.passwordEncoder.encode (password);
        User user = new User (username, hashedPassword);
        return this.repo.save (user);
    }

    public UserDetails loadUserByUsername (String username) {
        User user = this.repo.findByUsername (username)
                .orElseThrow (() -> new RuntimeException ("User not found."));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public boolean usernameExists (String username) {
        return this.repo.existsByUsername (username);
    }

    public Optional <User> findById (Long id) {
        return this.repo.findById (id);
    }

    public User getEntityByUsername (String username) {
        return this.repo.findByUsername (username)
                .orElseThrow (() -> new RuntimeException ("User not found."));
    }
}
