package com.example.expensetrackerapi.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
