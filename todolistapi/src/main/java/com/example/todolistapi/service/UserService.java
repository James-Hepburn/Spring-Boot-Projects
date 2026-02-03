package com.example.todolistapi.service;

import com.example.todolistapi.model.User;
import com.example.todolistapi.repository.UserRepository;
import com.example.todolistapi.security.JwtUtil;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@AllArgsConstructor
@Service
public class UserService {
    private UserRepository repo;
    private BCryptPasswordEncoder passwordEncoder;

    public String registerUser (String name, String email, String password) {
        Optional <User> userOptional = this.repo.findByEmail (email);

        if (userOptional.isPresent ()) {
            throw new RuntimeException ("User already exists.");
        } else {
            String hashedPassword = this.passwordEncoder.encode (password);
            User user = new User (name, email, hashedPassword);
            this.repo.save (user);
            return JwtUtil.generateToken (email);
        }
    }

    public String authenticateUser (String email, String password) {
        Optional <User> userOptional = this.repo.findByEmail (email);

        if (userOptional.isPresent ()) {
            User user = userOptional.get ();

            if (this.passwordEncoder.matches (password, user.getPassword ())) {
                return JwtUtil.generateToken (email);
            } else {
                throw new RuntimeException ("Invalid password.");
            }
        }

        throw new RuntimeException ("User does not exist.");
    }

    public Optional <User> getUserByEmail (String email) {
        return this.repo.findByEmail (email);
    }
}
