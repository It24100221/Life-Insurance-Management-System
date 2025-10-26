package com.example.insurancebackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long userId;           // Existing user reference (required)
    private BigDecimal amount;     // Positive amount
    private String policyNumber;   // Optional policy number
    private String paymentMethod;  // cash, card, googlepay
}