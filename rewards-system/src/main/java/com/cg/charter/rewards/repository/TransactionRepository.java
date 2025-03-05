package com.cg.charter.rewards.repository;

import com.cg.charter.rewards.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerIdAndTransactionDateBetween(
        Long customerId, 
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
} 