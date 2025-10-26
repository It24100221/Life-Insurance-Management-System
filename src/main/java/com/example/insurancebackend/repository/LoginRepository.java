package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNic(String nic);
}