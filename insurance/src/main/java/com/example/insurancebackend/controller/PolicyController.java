package com.example.insurancebackend.controller;

import com.example.insurancebackend.dto.PolicyPageResponse;
import com.example.insurancebackend.entity.Policy;
import com.example.insurancebackend.service.PolicyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "*")  // Allow frontend access (adjust for production)
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @GetMapping
    public PolicyPageResponse getPolicies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String query) {
        return policyService.getPolicies(page, size, sortBy, sortDir, query);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Policy> getPolicyByCode(@PathVariable String code) {
        return policyService.getPolicyByCode(code)
                .filter(Policy::isActive)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        return policyService.getPolicyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Policy createPolicy(@RequestBody Policy policy) {
        return policyService.savePolicy(policy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePolicy(@PathVariable Long id, @RequestBody Policy updatePolicy) {
        try {
            Policy updated = policyService.updatePolicy(id, updatePolicy);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetData() {
        policyService.resetData();
        return ResponseEntity.ok(Map.of("message", "Data reset successfully"));
    }

    // Error handling for unique constraint (e.g., duplicate code)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}