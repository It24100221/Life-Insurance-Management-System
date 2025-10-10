package com.example.insurancebackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCreateRequest {
    private Long userId;           // Optional existing user id
    private String customerName;   // Required if userId not provided
    private String email;          // Optional
    private String phone;          // Optional
    private String nic;            // Optional but used to identify existing user
    private String policyNumber;   // Optional
    private BigDecimal amount;     // Required > 0
    private String paymentMethod;  // cash, card, googlepay
}

