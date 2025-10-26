package com.example.insurancebackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String message;
    private String role;
    private Long userId; // added user id

    // Added user profile fields for frontend prefill
    private String fullName;
    private String email;
    private String nic;
    private String phone;

    public AuthResponse(String token, String message, String role) {
        this.token = token;
        this.message = message;
        this.role = role;
    }
}