package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.model.User;
import com.example.expensetrackerapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity <User> getUserById (@PathVariable Long id) {
        return this.userRepository.findById (id)
                .map (ResponseEntity::ok)
                .orElse (ResponseEntity.notFound ().build ());
    }

    @GetMapping("/me")
    public ResponseEntity <User> getCurrentUser (@AuthenticationPrincipal UserDetails userDetails) {
        return this.userRepository.findByUsername (userDetails.getUsername ())
                .map (ResponseEntity::ok)
                .orElse (ResponseEntity.notFound ().build ());
    }
}
