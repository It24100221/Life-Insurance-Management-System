package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    @Query("SELECT p FROM Policy p WHERE " +
            "(:query IS NULL OR LOWER(p.code) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Policy> searchPolicies(@Param("query") String query, Pageable pageable);

    boolean existsByCodeAndActive(String code, boolean active);

    Optional<Policy> findByCode(String code);

    boolean existsByCode(String code);
}