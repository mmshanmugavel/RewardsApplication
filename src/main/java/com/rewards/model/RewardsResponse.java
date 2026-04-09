package com.rewards.model;

import java.time.YearMonth;
import java.util.Map;

import lombok.Data;

@Data
public class RewardsResponse {

	private String customerId;
	private Map<YearMonth, Integer> monthlyRewards;
	private Integer totalRewards;
	
	public static RewardsResponse of(String Id, Map<YearMonth, Integer> mRewards, int tRewards) {//,List<Transaction> txn) {
		RewardsResponse res = new RewardsResponse();
		res.customerId = Id;
		res.monthlyRewards = mRewards;
		res.totalRewards = tRewards;
		return res;
	}
}
