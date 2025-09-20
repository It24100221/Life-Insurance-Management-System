package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "term_years", nullable = false)
    private int termYears;

    @Column(name = "premium_monthly", nullable = false, precision = 10)
    private double premiumMonthly;

    @Column(name = "sum_assured", nullable = false, precision = 15)
    private double sumAssured;

    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 500)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
