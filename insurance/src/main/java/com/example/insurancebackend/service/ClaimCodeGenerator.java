package com.example.insurancebackend.service;

import java.util.Random;

public class ClaimCodeGenerator {

    private static ClaimCodeGenerator instance;

    private final Random random;

    // Private constructor to prevent instantiation
    private ClaimCodeGenerator() {
        this.random = new Random();
    }

    // Singleton getInstance method
    public static synchronized ClaimCodeGenerator getInstance() {
        if (instance == null) {
            instance = new ClaimCodeGenerator();
        }
        return instance;
    }

    // Method to generate claim code
    public String generateCode() {
        return "TC-" + String.format("%06d", random.nextInt(1000000));
    }
}