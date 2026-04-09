package com.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewards.dao.RewardsServiceImplementation;
import com.rewards.model.RewardsResponse;

@SpringBootTest
public class RewardsServiceTest {

	@Autowired
	private RewardsServiceImplementation rewardService;
	
	@Test
	void testRewardCalculation() {
		RewardsResponse res = rewardService.calculateRewards("C001", 
				LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 31));
		assertEquals(3, res.getMonthlyRewards().size());
		assertTrue(res.getTotalRewards() > 0);
	}
}
