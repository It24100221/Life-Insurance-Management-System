package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "claims")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String nic;

    @Column
    private String contactNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String accidentType;

    @Column(nullable = false)
    private String accidentDate;

    @Column(nullable = false)
    private String accidentTime;

    @Column(nullable = false)
    private String accidentLocation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String accidentDescription;

    @Column(nullable = false)
    private Double estimatedCost;

    @Column(nullable = false)
    private String accountHolder;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column
    private String claimCode;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
}