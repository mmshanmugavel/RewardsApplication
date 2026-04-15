package com.charter.rewardpoints.service;

import java.time.LocalDate;
import java.util.List;
import com.charter.rewardpoints.dto.RewardsResponse;

public interface RewardsService {

	List<RewardsResponse> calculateRewards(LocalDate fromDate, LocalDate toDate);

}
