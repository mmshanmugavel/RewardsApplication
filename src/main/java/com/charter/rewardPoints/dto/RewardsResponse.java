package com.charter.rewardPoints.dto;

import java.time.YearMonth;
import java.util.Map;

import lombok.Data;

@Data
public class RewardsResponse {

	private String customerId;
	private Map<YearMonth, Integer> monthlyRewards;
	private Integer totalRewards;
	private String errorMessage;
	private Integer errorCode;
	
	public static RewardsResponse of(String customerId, Map<YearMonth, Integer> monthlyRewards, int totalRewards) {
		RewardsResponse res = new RewardsResponse();
		res.customerId = customerId;
		res.monthlyRewards = monthlyRewards;
		res.totalRewards = totalRewards;
		return res;
	}
}
