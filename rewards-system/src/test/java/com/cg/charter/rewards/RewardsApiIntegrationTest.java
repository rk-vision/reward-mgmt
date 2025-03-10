package com.cg.charter.rewards.integration;

import com.cg.charter.rewards.dto.RewardsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenValidCustomer_thenReturnsRewards() {
        ResponseEntity<RewardsResponse> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/rewards/1",
            RewardsResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getCustomerId());
        assertFalse(response.getBody().getMonthlyPoints().isEmpty());
    }

    @Test
    void whenInvalidCustomer_thenReturnsNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/rewards/999",
            String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("No transactions found for customer ID: 999"));
    }

    @Test
    void whenNegativeCustomerId_thenReturnsBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/rewards/-1",
            String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid customer ID"));
    }

    @Test
    void whenInvalidMonths_thenReturnsBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/rewards/1?months=-1",
            String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Months must be greater than 0"));
    }
} 