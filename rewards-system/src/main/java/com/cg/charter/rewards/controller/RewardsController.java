package com.cg.charter.rewards.controller;

import com.cg.charter.rewards.dto.RewardsResponse;
import com.cg.charter.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
@Validated
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @GetMapping("/{customerId}")
    public ResponseEntity<RewardsResponse> getRewards(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "3") int months) {
        RewardsResponse response = rewardsService.calculateRewards(customerId, months);
        return ResponseEntity.ok(response);
    }
} 