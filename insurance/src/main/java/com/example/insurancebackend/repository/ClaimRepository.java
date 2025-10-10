// src/main/java/com/example/insurancebackend/repository/ClaimRepository.java
package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
}