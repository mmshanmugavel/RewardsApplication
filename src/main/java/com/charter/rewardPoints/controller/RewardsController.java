package com.charter.rewardpoints.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.charter.rewardpoints.dto.RewardsResponse;
import com.charter.rewardpoints.service.RewardsService;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;
	
	@GetMapping("/calculate")
	public List<RewardsResponse> calculateRewards( 
			@RequestParam(required = false) LocalDate fromDate, 
			@RequestParam(required = false) LocalDate toDate) {

		return rewardsService.calculateRewards(fromDate,toDate);
	}
}
