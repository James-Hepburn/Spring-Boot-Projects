package com.example.expensetrackerapi.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
}
