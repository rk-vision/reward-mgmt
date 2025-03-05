package com.cg.charter.rewards.service;

import com.cg.charter.rewards.dto.RewardsResponse;
import com.cg.charter.rewards.entity.Transaction;
import com.cg.charter.rewards.exception.ResourceNotFoundException;
import com.cg.charter.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsServiceImpl rewardsService;

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    void whenValidCustomerWithTransactions_thenCalculateRewards() {
        // Arrange
        List<Transaction> transactions = Arrays.asList(
            new Transaction(1L, 120.0, now.minusDays(5)),
            new Transaction(1L, 85.0, now.minusDays(10)),
            new Transaction(1L, 160.0, now.minusMonths(1))
        );

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
            any(), any(), any())).thenReturn(transactions);

        RewardsResponse response = rewardsService.calculateRewards(1L, 3);

        assertNotNull(response);
        assertTrue(response.getTotalPoints() > 0);
    }

    @Test
    void whenNoTransactions_thenThrowException() {
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
            any(), any(), any())).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> 
            rewardsService.calculateRewards(1L, 3));
    }

    @Test
    void whenInvalidCustomerId_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> 
            rewardsService.calculateRewards(-1L, 3));
    }

    @Test
    void whenInvalidMonths_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> 
            rewardsService.calculateRewards(1L, 0));
    }

    @Test
    void whenTransactionAmountIs120_thenReturn90Points() {
        List<Transaction> transactions = Collections.singletonList(
            new Transaction(1L, 120.0, now)
        );

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(
            any(), any(), any())).thenReturn(transactions);

        RewardsResponse response = rewardsService.calculateRewards(1L, 3);
        assertEquals(90, response.getTotalPoints());
    }
} 