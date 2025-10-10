package com.example.insurancebackend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long userId;
    private String userFullname; // existing full name
    private String customerName; // duplicate for frontend convenience
    private String nic;          // user's NIC for agent table
    private BigDecimal amount;
    private String policyNumber;
    private String paymentMethod;
    private String status;
    private String transactionCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
