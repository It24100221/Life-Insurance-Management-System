package com.example.insurancebackend.strategy;

import com.example.insurancebackend.entity.Claim;

public class PendingStrategy implements StatusUpdateStrategy {
    @Override
    public void updateStatus(Claim claim) {
        claim.setStatus("PENDING");
        // Additional behavior (e.g., logging or future extensions)
        System.out.println("Claim " + claim.getId() + " set to pending. Awaiting review (simulated).");
    }
}