package com.rewards.service;

import java.time.LocalDate;

import com.rewards.model.RewardsResponse;

public interface RewardsService {

	RewardsResponse calculateRewards(String customerId, LocalDate fromDate, LocalDate toDate);

}
