package com.example.insurancebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String nic; // renamed to match frontend
    private String phone; // renamed to match frontend
    private String password;
    private String confirmPassword;
    private boolean terms;
    private String role; // added role to allow setting user role at registration time
}