package com.charter.rewardPoints.service;

import java.time.LocalDate;

import com.charter.rewardPoints.dto.RewardsResponse;

public interface RewardsService {

	RewardsResponse calculateRewards(String customerId, LocalDate fromDate, LocalDate toDate);

}
