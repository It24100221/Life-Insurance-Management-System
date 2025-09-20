package com.example.insurancebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "f_name", nullable = false)
    private String fName;

    @Column(nullable = false)
    private Integer rating;

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Default constructor needed by JPA
    public Feedback() {}

    // Convenience constructor
    public Feedback(String fName, Integer rating, String reviewText) {
        this.fName = fName;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = LocalDateTime.now();
    }
}