package com.example.insurancebackend.strategy;

import com.example.insurancebackend.entity.Claim;

public class ApprovedStrategy implements StatusUpdateStrategy {
    @Override
    public void updateStatus(Claim claim) {
        claim.setStatus("APPROVED");
        // Additional behavior (e.g., logging or future extensions)
        System.out.println("Claim " + claim.getId() + " approved. Notification sent (simulated).");
    }
}