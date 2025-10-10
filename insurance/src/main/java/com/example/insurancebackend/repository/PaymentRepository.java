package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionCode(String transactionCode);

    @Query("SELECT p FROM Payment p WHERE " +
            "(:userId IS NULL OR p.user.userId = :userId) AND " +
            "(:policyNumber IS NULL OR p.policyNumber = :policyNumber) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:method IS NULL OR p.paymentMethod = :method)")
    List<Payment> search(@Param("userId") Long userId,
                         @Param("policyNumber") String policyNumber,
                         @Param("status") String status,
                         @Param("method") String method);

    // Pageable version for agent listing
    @Query("SELECT p FROM Payment p WHERE " +
            "(:userId IS NULL OR p.user.userId = :userId) AND " +
            "(:policyNumber IS NULL OR p.policyNumber = :policyNumber) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:method IS NULL OR p.paymentMethod = :method)")
    Page<Payment> searchPage(@Param("userId") Long userId,
                             @Param("policyNumber") String policyNumber,
                             @Param("status") String status,
                             @Param("method") String method,
                             Pageable pageable);
}
