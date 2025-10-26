// UserRepository.java (src/main/java/com/example/insurancebackend/repository/UserRepository.java)
package com.example.insurancebackend.repository;

import com.example.insurancebackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE " +
            "(:query IS NULL OR LOWER(u.userFullname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.userEmail) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.userNic) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:role IS NULL OR u.userRole = :role)")
    Page<User> searchUsers(@Param("query") String query, @Param("role") String role, Pageable pageable);

    User findByUserNic(String userNic);
    User findByUserEmail(String userEmail);
    // Case-insensitive email lookup for authentication
    User findByUserEmailIgnoreCase(String userEmail);
}