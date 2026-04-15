package com.charter.rewardpoints.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardsResponse {

	private String customerId;
	private Map<String, Integer> monthlyRewards;
	private Integer totalRewards;
	
	public RewardsResponse(String customerId, Map<String, Integer> monthlyRewards, int totalRewards) {
		this.customerId = customerId;
		this.monthlyRewards = monthlyRewards;
		this.totalRewards = totalRewards;
	}
}
