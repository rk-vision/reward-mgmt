package com.cg.charter.rewards.service;

import com.cg.charter.rewards.dto.RewardsResponse;

public interface RewardsService {
    RewardsResponse calculateRewards(Long customerId, int months);
} 