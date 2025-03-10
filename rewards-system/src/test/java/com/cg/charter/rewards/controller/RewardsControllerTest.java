package com.cg.charter.rewards.controller;

import com.cg.charter.rewards.dto.RewardsResponse;
import com.cg.charter.rewards.exception.ResourceNotFoundException;
import com.cg.charter.rewards.service.RewardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsControllerTest {

    @Mock
    private RewardsService rewardsService;

    @InjectMocks
    private RewardsController rewardsController;

    private RewardsResponse mockResponse;

    @BeforeEach
    void setUp() {
        Map<String, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put("Mar 2024", 90);
        monthlyPoints.put("Feb 2024", 170);
        monthlyPoints.put("Jan 2024", 275);
        mockResponse = new RewardsResponse(1L, monthlyPoints, 535);
    }

    @Test
    void whenValidRequest_thenReturnsRewardsResponse() {
        // Arrange
        when(rewardsService.calculateRewards(anyLong(), anyInt()))
            .thenReturn(mockResponse);

        // Act
        ResponseEntity<RewardsResponse> response = 
            rewardsController.getRewards(1L, 3);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getCustomerId());
        assertEquals(535, response.getBody().getTotalPoints());
        assertEquals(3, response.getBody().getMonthlyPoints().size());
    }

    @Test
    void whenCustomerNotFound_thenReturnsNotFound() {
        // Arrange
        when(rewardsService.calculateRewards(anyLong(), anyInt()))
            .thenThrow(new ResourceNotFoundException("No transactions found for customer ID: 999"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> 
            rewardsController.getRewards(999L, 3));
    }

    @Test
    void whenInvalidCustomerId_thenReturnsBadRequest() {
        // Arrange
        when(rewardsService.calculateRewards(anyLong(), anyInt()))
            .thenThrow(new IllegalArgumentException("Invalid customer ID"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            rewardsController.getRewards(-1L, 3));
    }

    @Test
    void whenInvalidMonths_thenReturnsBadRequest() {
        // Arrange
        when(rewardsService.calculateRewards(anyLong(), anyInt()))
            .thenThrow(new IllegalArgumentException("Months must be greater than 0"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            rewardsController.getRewards(1L, -1));
    }

    @Test
    void whenDefaultMonths_thenUsesThreeMonths() {
        // Arrange
        when(rewardsService.calculateRewards(1L, 3))
            .thenReturn(mockResponse);

        // Act
        ResponseEntity<RewardsResponse> response = 
            rewardsController.getRewards(1L, 3);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void whenCustomMonths_thenUsesSpecifiedMonths() {
        // Arrange
        when(rewardsService.calculateRewards(1L, 6))
            .thenReturn(mockResponse);

        // Act
        ResponseEntity<RewardsResponse> response = 
            rewardsController.getRewards(1L, 6);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
} 