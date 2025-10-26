package com.example.insurancebackend.strategy;

import com.example.insurancebackend.entity.Claim;

public class RejectedStrategy implements StatusUpdateStrategy {
    @Override
    public void updateStatus(Claim claim) {
        claim.setStatus("REJECTED");
        // Additional behavior (e.g., logging or future extensions)
        System.out.println("Claim " + claim.getId() + " rejected. Notification sent (simulated).");
    }
}