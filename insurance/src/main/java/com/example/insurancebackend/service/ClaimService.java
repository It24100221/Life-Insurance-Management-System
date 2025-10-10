// src/main/java/com/example/insurancebackend/service/ClaimService.java
package com.example.insurancebackend.service;

import com.example.insurancebackend.entity.Claim;
import com.example.insurancebackend.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    public Claim saveClaim(Claim claim) {
        // Generate claim code before saving
        claim.setClaimCode("TC-" + String.format("%06d", new Random().nextInt(1000000)));
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
            claim.setStatus(status);
            return claimRepository.save(claim);
        }
        return null;
    }

    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }
}