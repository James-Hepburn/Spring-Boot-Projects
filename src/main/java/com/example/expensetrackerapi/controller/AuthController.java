package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.JwtResponse;
import com.example.expensetrackerapi.dto.LoginRequest;
import com.example.expensetrackerapi.dto.RegisterRequest;
import com.example.expensetrackerapi.model.User;
import com.example.expensetrackerapi.security.JwtUtil;
import com.example.expensetrackerapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity <?> registerUser (@RequestBody RegisterRequest request) {
        User user = this.userService.registerUser (request.getUsername (), request.getPassword ());
        return ResponseEntity.ok ("User registered successfully: " + user.getUsername ());
    }

    @PostMapping("/login")
    public ResponseEntity <?> loginUser (@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}