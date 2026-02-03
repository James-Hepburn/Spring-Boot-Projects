package com.example.todolistapi.controller;

import com.example.todolistapi.model.Todo;
import com.example.todolistapi.model.User;
import com.example.todolistapi.security.JwtUtil;
import com.example.todolistapi.service.*;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;
    private UserService userService;

    @PostMapping("/todos")
    public Todo createTodo (@RequestHeader("Authorization") String token, @RequestBody TodoRequest request) {
        User user = authenticateToken (token);
        return this.todoService.createTodo (user, request.title, request.description);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo (@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody TodoRequest request) {
        User user = authenticateToken (token);
        return this.todoService.updateTodo (user, id, request.title, request.description);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        User user = authenticateToken (token);
        this.todoService.deleteTodo (user, id);
    }

    @GetMapping("/todos")
    public Page <Todo> getTodos(@RequestHeader("Authorization") String token, @RequestParam int page, @RequestParam int limit) {
        User user = authenticateToken (token);
        return todoService.getTodos (user, page, limit);
    }

    public static class TodoRequest {
        public String title;
        public String description;
    }

    public User authenticateToken (String token) {
        if (token.startsWith ("Bearer ")) {
            token = token.substring (7);
        }

        String email = JwtUtil.extractEmail (token);
        Optional <User> userOptional = this.userService.getUserByEmail (email);

        if (userOptional.isPresent ()) {
            return userOptional.get ();
        }

        throw new RuntimeException ("User not found.");
    }
}
