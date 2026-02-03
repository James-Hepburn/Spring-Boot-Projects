package com.example.workouttracker.controller;

import com.example.workouttracker.dto.LoginRequest;
import com.example.workouttracker.dto.SignupRequest;
import com.example.workouttracker.model.User;
import com.example.workouttracker.security.JwtUtils;
import com.example.workouttracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity <?> signup (@RequestBody SignupRequest signupRequest) {
        if (this.userService.existsByUsername (signupRequest.getUsername ())) {
            return ResponseEntity.badRequest ().body ("Username is already taken");
        } else if (this.userService.existsByEmail (signupRequest.getEmail ())) {
            return ResponseEntity.badRequest ().body ("Email is already in use");
        }

        User user = this.userService.createUser (signupRequest.getUsername (), signupRequest.getEmail (), signupRequest.getPassword ());
        return ResponseEntity.ok ("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity <?> login (@RequestBody LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate (new UsernamePasswordAuthenticationToken (loginRequest.getUsername (), loginRequest.getPassword ()));
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        String jwt = this.jwtUtils.generateJwtToken (loginRequest.getUsername ());
        return ResponseEntity.ok (jwt);
    }
}