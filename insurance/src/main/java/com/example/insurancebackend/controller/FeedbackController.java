package com.example.insurancebackend.controller;

import com.example.insurancebackend.entity.Feedback;
import com.example.insurancebackend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService service;

    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Feedback feedback) {
        Feedback saved = service.saveFeedback(feedback);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = service.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }
}