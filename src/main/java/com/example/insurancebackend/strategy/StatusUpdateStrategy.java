package com.example.insurancebackend.strategy;

import com.example.insurancebackend.entity.Claim;

public interface StatusUpdateStrategy {
    void updateStatus(Claim claim);
}