package com.example.insurancebackend.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Request body for updating a payment. All fields optional; only provided fields will be updated.
 */
@Data
public class PaymentUpdateRequest {
    private BigDecimal amount;        // Optional new amount (>0)
    private String policyNumber;      // Optional new policy number
    private String paymentMethod;     // Optional: cash, card, googlepay
    private String status;            // Optional: PENDING, SUCCESS, FAILED, REFUNDED, CANCELED
}