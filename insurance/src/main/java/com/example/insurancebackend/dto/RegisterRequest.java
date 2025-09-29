package com.example.insurancebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String nicNumber;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private boolean terms;
}