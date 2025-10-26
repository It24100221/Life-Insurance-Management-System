package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
@Check(constraints = "amount > 0 AND payment_method IN ('cash','card','googlepay') AND status IN ('PENDING','SUCCESS','FAILED','REFUNDED','CANCELED')")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "policy_number", length = 30)
    private String policyNumber;

    @Column(name = "payment_method", nullable = false, length = 20)
    private String paymentMethod; // cash, card, googlepay

    @Column(name = "status", nullable = false, length = 15)
    private String status = "PENDING"; // PENDING, SUCCESS, FAILED, REFUNDED, CANCELED

    @Column(name = "transaction_code", length = 40, unique = true)
    private String transactionCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) status = "PENDING";
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}