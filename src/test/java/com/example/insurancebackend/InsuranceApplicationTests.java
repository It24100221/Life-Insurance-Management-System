package com.example.insurancebackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class InsuranceApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("[DEBUG_LOG] Running contextLoads test with H2");
    }

}
