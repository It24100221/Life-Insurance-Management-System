package com.example.insurancebackend.controller;

import com.example.insurancebackend.dto.PaymentCreateRequest;
import com.example.insurancebackend.dto.PaymentResponse;
import com.example.insurancebackend.dto.PaymentUpdateRequest;
import com.example.insurancebackend.service.PaymentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentCreateRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> searchPayments(@RequestParam(required = false) Long userId,
                                                                @RequestParam(required = false) String policyNumber,
                                                                @RequestParam(required = false) String status,
                                                                @RequestParam(required = false) String method) {
        return ResponseEntity.ok(paymentService.searchPayments(userId, policyNumber, status, method));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> putUpdate(@PathVariable Long id, @RequestBody PaymentUpdateRequest request) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PaymentResponse> patchUpdate(@PathVariable Long id, @RequestBody PaymentUpdateRequest request) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> updateStatus(@PathVariable Long id, @RequestBody StatusUpdate statusUpdate) {
        return ResponseEntity.ok(paymentService.updateStatus(id, statusUpdate.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(Map.of("message","Payment deleted"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
    }
}