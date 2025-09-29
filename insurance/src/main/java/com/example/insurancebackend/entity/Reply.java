package com.example.insurancebackend.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "author")
    private String author;

    @Column(name = "message")
    private String message;

    @Column(name = "reply_date")
    private LocalDateTime replyDate = LocalDateTime.now();
}
