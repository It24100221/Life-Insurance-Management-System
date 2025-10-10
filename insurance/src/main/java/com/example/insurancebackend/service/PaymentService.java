package com.example.insurancebackend.service;

import com.example.insurancebackend.dto.PaymentCreateRequest;
import com.example.insurancebackend.dto.PaymentPageResponse;
import com.example.insurancebackend.dto.PaymentResponse;
import com.example.insurancebackend.dto.PaymentUpdateRequest;
import com.example.insurancebackend.entity.Payment;
import com.example.insurancebackend.entity.User;
import com.example.insurancebackend.repository.PaymentRepository;
import com.example.insurancebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Set<String> ALLOWED_METHODS = Set.of("cash","card","googlepay");
    private static final Set<String> ALLOWED_STATUS = Set.of("PENDING","SUCCESS","FAILED","REFUNDED","CANCELED");

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    private final SecureRandom random = new SecureRandom();

    public PaymentResponse createPayment(PaymentCreateRequest request) {
        validateCreateRequest(request);
        User user = resolveUser(request);
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setAmount(request.getAmount());
        payment.setPolicyNumber(request.getPolicyNumber());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("PENDING");
        payment.setTransactionCode(generateUniqueTransactionCode());
        payment.setUpdatedAt(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);
        saved.setStatus("SUCCESS");
        saved.setUpdatedAt(LocalDateTime.now());
        saved = paymentRepository.save(saved);
        return toResponse(saved);
    }

    public PaymentResponse getPayment(Long id) {
        return paymentRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public List<PaymentResponse> searchPayments(Long userId, String policyNumber, String status, String method) {
        return paymentRepository.search(userId, emptyToNull(policyNumber), emptyToNull(status), emptyToNull(method))
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public PaymentResponse updateStatus(Long id, String status) {
        if (!ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());
        return toResponse(paymentRepository.save(payment));
    }

    // NEW: generic update for agent edits
    public PaymentResponse updatePayment(Long id, PaymentUpdateRequest req) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        boolean changed = false;
        if (req.getAmount() != null) {
            if (req.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            payment.setAmount(req.getAmount());
            changed = true;
        }
        if (req.getPolicyNumber() != null) {
            String pn = req.getPolicyNumber().trim();
            payment.setPolicyNumber(pn.isBlank() ? null : pn);
            changed = true;
        }
        if (req.getPaymentMethod() != null) {
            if (!ALLOWED_METHODS.contains(req.getPaymentMethod())) {
                throw new IllegalArgumentException("Invalid payment method");
            }
            payment.setPaymentMethod(req.getPaymentMethod());
            changed = true;
        }
        if (req.getStatus() != null) {
            if (!ALLOWED_STATUS.contains(req.getStatus())) {
                throw new IllegalArgumentException("Invalid status");
            }
            payment.setStatus(req.getStatus());
            changed = true;
        }
        if (!changed) {
            return toResponse(payment); // nothing changed
        }
        payment.setUpdatedAt(LocalDateTime.now());
        return toResponse(paymentRepository.save(payment));
    }

    // NEW: delete payment
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }

    // NEW: pageable listing
    public PaymentPageResponse listPayments(Long userId, String policyNumber, String status, String method, int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
        if (size > 100) size = 100;
        Page<Payment> pg = paymentRepository.searchPage(userId, emptyToNull(policyNumber), emptyToNull(status), emptyToNull(method), PageRequest.of(page, size));
        return PaymentPageResponse.builder()
                .payments(pg.getContent().stream().map(this::toResponse).toList())
                .page(pg.getNumber())
                .size(pg.getSize())
                .totalElements(pg.getTotalElements())
                .totalPages(pg.getTotalPages())
                .first(pg.isFirst())
                .last(pg.isLast())
                .build();
    }

    private void validateCreateRequest(PaymentCreateRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (request.getPaymentMethod() == null || !ALLOWED_METHODS.contains(request.getPaymentMethod())) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        if (request.getUserId() == null && (request.getCustomerName() == null || request.getCustomerName().isBlank())) {
            throw new IllegalArgumentException("Customer name required when userId not supplied");
        }
    }

    private User resolveUser(PaymentCreateRequest request) {
        if (request.getUserId() != null) {
            return userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }
        User user = null;
        if (request.getNic() != null && !request.getNic().isBlank()) {
            user = userRepository.findByUserNic(request.getNic());
        }
        if (user == null && request.getEmail() != null && !request.getEmail().isBlank()) {
            user = userRepository.findByUserEmail(request.getEmail());
        }
        if (user != null) return user;
        user = new User();
        user.setUserFullname(request.getCustomerName());
        user.setUserEmail(request.getEmail() != null ? request.getEmail() : ("unknown-" + UUID.randomUUID() + "@example.com"));
        user.setUserPhone(request.getPhone() != null ? request.getPhone() : "N/A");
        user.setUserNic(request.getNic() != null ? request.getNic() : ("NIC-" + UUID.randomUUID().toString().substring(0,8)));
        user.setUserPassword(randomPassword());
        user.setUserRole("USER");
        user.setUserIsActive(true);
        return userRepository.save(user);
    }

    private String randomPassword() { return UUID.randomUUID().toString(); }

    private String generateUniqueTransactionCode() {
        String code; int attempts = 0;
        do {
            code = "TXN-" + Math.abs(random.nextLong() % 1_000_000_000L);
            attempts++;
            if (attempts > 10) {
                code = "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0,16).toUpperCase();
                break;
            }
        } while (paymentRepository.findByTransactionCode(code).isPresent());
        return code;
    }

    private String emptyToNull(String v) { return (v == null || v.isBlank()) ? null : v; }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .paymentId(p.getPaymentId())
                .userId(p.getUser().getUserId())
                .userFullname(p.getUser().getUserFullname())
                .customerName(p.getUser().getUserFullname()) // added mapping
                .nic(p.getUser().getUserNic())               // added mapping
                .amount(p.getAmount())
                .policyNumber(p.getPolicyNumber())
                .paymentMethod(p.getPaymentMethod())
                .status(p.getStatus())
                .transactionCode(p.getTransactionCode())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
