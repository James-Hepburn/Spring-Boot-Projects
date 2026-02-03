package com.example.todolistapi.controller;

import com.example.todolistapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public String registerUser (@RequestBody RegisterRequest request) {
        return this.userService.registerUser (request.name, request.email, request.password);
    }

    @PostMapping("/login")
    public String loginUser (@RequestBody LoginRequest request) {
        return this.userService.authenticateUser (request.email, request.password);
    }

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }
}
