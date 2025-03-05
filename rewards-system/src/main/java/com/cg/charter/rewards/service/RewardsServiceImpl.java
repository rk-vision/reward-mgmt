package com.cg.charter.rewards.service;

import com.cg.charter.rewards.dto.RewardsResponse;
import com.cg.charter.rewards.entity.Transaction;
import com.cg.charter.rewards.exception.ResourceNotFoundException;
import com.cg.charter.rewards.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service implementation for calculating customer rewards points based on their transactions.
 * Points are calculated according to the following rules:
 * - 2 points for every dollar spent over $100 in each transaction
 * - 1 point for every dollar spent between $50 and $100 in each transaction
 */
@Service
public class RewardsServiceImpl implements RewardsService {
    
    private static final Logger logger = LoggerFactory.getLogger(RewardsServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Calculates reward points for a customer over a specified period.
     *
     * @param customerId The ID of the customer
     * @param months The number of months to look back for transactions
     * @return RewardsResponse containing monthly and total points
     * @throws IllegalArgumentException if customerId or months is invalid
     * @throws ResourceNotFoundException if no transactions are found
     */
    @Override
    public RewardsResponse calculateRewards(Long customerId, int months) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }
        
        if (months <= 0) {
            throw new IllegalArgumentException("Months must be greater than 0");
        }

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusMonths(months);
        
        logger.info("Fetching transactions for customer {} between {} and {}", 
            customerId, startDate, endDate);

        List<Transaction> transactions = transactionRepository
            .findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException(
                "No transactions found for customer ID: " + customerId);
        }

        logger.info("Found {} transactions for customer {}", 
            transactions.size(), customerId);

        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                throw new IllegalArgumentException(
                    "Invalid transaction amount: " + transaction.getAmount());
            }

            String monthYear = transaction.getTransactionDate()
                .format(DateTimeFormatter.ofPattern("MMM yyyy"));
            
            int points = calculatePointsForTransaction(transaction.getAmount());
            logger.debug("Transaction amount: {}, Points earned: {}", 
                transaction.getAmount(), points);
            
            monthlyPoints.merge(monthYear, points, Integer::sum);
            totalPoints += points;
        }

        logger.info("Calculated total points for customer {}: {}", 
            customerId, totalPoints);

        return new RewardsResponse(customerId, monthlyPoints, totalPoints);
    }

    /**
     * Calculates points for a single transaction based on the amount spent.
     * 
     * @param amount The transaction amount
     * @return The number of points earned
     */
    private int calculatePointsForTransaction(double amount) {
        int points = 0;
        
        if (amount > 100) {
            points += (amount - 100) * 2;
            points += 50; // 1 point for each dollar between 50 and 100
        } else if (amount > 50) {
            points += (amount - 50);
        }
        
        return points;
    }
} 