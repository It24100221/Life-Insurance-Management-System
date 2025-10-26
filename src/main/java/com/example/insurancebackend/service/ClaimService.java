package com.example.insurancebackend.service;

import com.example.insurancebackend.entity.Claim;
import com.example.insurancebackend.repository.ClaimRepository;
import com.example.insurancebackend.strategy.ApprovedStrategy;
import com.example.insurancebackend.strategy.PendingStrategy;
import com.example.insurancebackend.strategy.RejectedStrategy;
import com.example.insurancebackend.strategy.StatusUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    public Claim saveClaim(Claim claim) {
        // Use Singleton pattern for claim code generation
        claim.setClaimCode(ClaimCodeGenerator.getInstance().generateCode());
        return claimRepository.save(claim);
    }

    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public Optional<Claim> getClaimById(Long id) {
        return claimRepository.findById(id);
    }

    public Claim updateClaimStatus(Long id, String status) {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            Claim claim = optionalClaim.get();

            // Use Strategy pattern to handle status update
            StatusUpdateStrategy strategy;
            switch (status.toUpperCase()) {
                case "APPROVED":
                    strategy = new ApprovedStrategy();
                    break;
                case "REJECTED":
                    strategy = new RejectedStrategy();
                    break;
                case "PENDING":
                default:
                    strategy = new PendingStrategy();
                    break;
            }
            strategy.updateStatus(claim);

            return claimRepository.save(claim);
        }
        return null;
    }

    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }
}