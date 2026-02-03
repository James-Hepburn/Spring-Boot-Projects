package com.example.workouttracker.service;

import com.example.workouttracker.model.User;
import com.example.workouttracker.model.Role;
import com.example.workouttracker.repository.UserRepository;
import com.example.workouttracker.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser (String username, String email, String password) {
        Role role = this.roleRepository.findByName ("ROLE_USER").orElseThrow (() -> new RuntimeException ("Role not found"));
        Set <Role> roles = Set.of (role);
        User user = new User (username, email, this.passwordEncoder.encode (password), roles);
        return this.userRepository.save (user);
    }

    public Optional <User> findByUsername (String username) {
        return this.userRepository.findByUsername (username);
    }

    public Optional <User> findByEmail (String email) {
        return this.userRepository.findByEmail (email);
    }

    public boolean existsByUsername (String username) {
        return findByUsername (username).isPresent ();
    }

    public boolean existsByEmail (String email) {
        return findByEmail (email).isPresent ();
    }
}