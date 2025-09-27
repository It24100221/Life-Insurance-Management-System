package com.example.insurancebackend.service;

import com.example.insurancebackend.dto.PolicyPageResponse;
import com.example.insurancebackend.entity.Policy;
import com.example.insurancebackend.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;

    public PolicyPageResponse getPolicies(int page, int size, String sortBy, String sortDir, String query) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);  // Page starts from 0 internally
        Page<Policy> policyPage = (query == null || query.isEmpty())
                ? policyRepository.findAll(pageable)
                : policyRepository.searchPolicies(query, pageable);

        PolicyPageResponse response = new PolicyPageResponse();
        response.setPolicies(policyPage.getContent());
        response.setCurrentPage(policyPage.getNumber() + 1);
        response.setTotalPages(policyPage.getTotalPages());
        response.setTotalCount((int) policyPage.getTotalElements());
        return response;
    }

    public Policy savePolicy(Policy policy) {
        if (policy.getId() == null) {
            policy.setCreatedAt(LocalDateTime.now());
        }
        return policyRepository.save(policy);
    }

    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }

    public void resetData() {
        policyRepository.deleteAll();
        // Insert sample data
        List<Policy> samples = List.of(
                createSample("LIFE-001", "Basic Life Coverage", 10, 50.00, 100000.00, true, "Entry-level life insurance"),
                createSample("LIFE-002", "Family Protection Plan", 20, 100.00, 500000.00, true, "Comprehensive family coverage"),
                createSample("LIFE-003", "Senior Life Policy", 15, 75.00, 250000.00, false, "For seniors over 60")
        );
        policyRepository.saveAll(samples);
    }

    private Policy createSample(String code, String name, int termYears, double premium, double sum, boolean active, String desc) {
        Policy p = new Policy();
        p.setCode(code);
        p.setName(name);
        p.setTermYears(termYears);
        p.setPremiumMonthly(premium);
        p.setSumAssured(sum);
        p.setActive(active);
        p.setDescription(desc);
        p.setCreatedAt(LocalDateTime.now());
        return p;
    }
}
