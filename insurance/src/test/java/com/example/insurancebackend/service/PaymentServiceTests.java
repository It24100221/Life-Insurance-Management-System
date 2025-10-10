package com.example.insurancebackend.service;

import com.example.insurancebackend.dto.PaymentCreateRequest;
import com.example.insurancebackend.dto.PaymentResponse;
import com.example.insurancebackend.entity.User;
import com.example.insurancebackend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PaymentServiceTests {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Create payment without existing user should create user and return SUCCESS status")
    void testCreatePaymentCreatesUserWhenNoUserIdProvided() {
        PaymentCreateRequest req = new PaymentCreateRequest();
        req.setCustomerName("Alice Wonderland");
        req.setNic("200012345678");
        req.setEmail("alice@example.com");
        req.setPhone("+1234567890");
        req.setPolicyNumber("POL-2023-TEST01");
        req.setAmount(new BigDecimal("150.00"));
        req.setPaymentMethod("cash");

        PaymentResponse resp = paymentService.createPayment(req);
        assertNotNull(resp.getPaymentId());
        assertEquals("SUCCESS", resp.getStatus());
        assertEquals("POL-2023-TEST01", resp.getPolicyNumber());
        assertEquals("cash", resp.getPaymentMethod());
        assertNotNull(resp.getTransactionCode());
        assertTrue(resp.getAmount().compareTo(new BigDecimal("150.00")) == 0);
    }

    @Test
    @DisplayName("Create payment with existing userId returns SUCCESS and links user")
    void testCreatePaymentWithExistingUser() {
        User user = new User();
        user.setUserFullname("Bob Builder");
        user.setUserEmail("bob@example.com");
        user.setUserPhone("+1987654321");
        user.setUserNic("991234567V");
        user.setUserPassword("secret");
        user.setUserRole("USER");
        user.setUserIsActive(true);
        user = userRepository.save(user);

        PaymentCreateRequest req = new PaymentCreateRequest();
        req.setUserId(user.getUserId());
        req.setAmount(new BigDecimal("99.99"));
        req.setPaymentMethod("googlepay");
        req.setPolicyNumber("POL-2023-EXIST01");

        PaymentResponse resp = paymentService.createPayment(req);
        assertEquals(user.getUserId(), resp.getUserId());
        assertEquals("SUCCESS", resp.getStatus());
        assertEquals("googlepay", resp.getPaymentMethod());
        assertEquals("POL-2023-EXIST01", resp.getPolicyNumber());
    }

    @Test
    @DisplayName("Update payment status to REFUNDED")
    void testUpdateStatus() {
        PaymentCreateRequest req = new PaymentCreateRequest();
        req.setCustomerName("Charlie Status");
        req.setNic("200012349999");
        req.setAmount(new BigDecimal("10.00"));
        req.setPaymentMethod("cash");
        req.setPolicyNumber("POL-2023-REFUND");
        PaymentResponse created = paymentService.createPayment(req);

        PaymentResponse updated = paymentService.updateStatus(created.getPaymentId(), "REFUNDED");
        assertEquals("REFUNDED", updated.getStatus());
    }

    @Test
    @DisplayName("Creating payment with non-positive amount should throw")
    void testInvalidAmount() {
        PaymentCreateRequest req = new PaymentCreateRequest();
        req.setCustomerName("Invalid Amount");
        req.setNic("200022223333");
        req.setAmount(new BigDecimal("0"));
        req.setPaymentMethod("cash");
        assertThrows(IllegalArgumentException.class, () -> paymentService.createPayment(req));
    }
}

