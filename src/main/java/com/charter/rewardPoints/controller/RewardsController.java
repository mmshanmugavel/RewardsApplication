package com.charter.rewardPoints.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charter.rewardPoints.dto.RewardsResponse;
import com.charter.rewardPoints.exception.FutureDateException;
import com.charter.rewardPoints.exception.MissingDateException;
import com.charter.rewardPoints.service.RewardsService;


@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;
	
	@GetMapping("/calculate")
	public RewardsResponse calculateRewards(@RequestParam String customerId, 
			@RequestParam(required = false) LocalDate fromDate, 
			@RequestParam(required = false) LocalDate toDate) {
		
		if(fromDate == null && toDate == null) {
			toDate = LocalDate.now();
			fromDate = toDate.minusMonths(3);
		}else if(fromDate == null || toDate == null) {
			throw new MissingDateException("From date and To date must be provided");
		}
		
		LocalDate today = LocalDate.now();
		
		if(fromDate.isAfter(today) || toDate.isAfter(today)) {
			throw new FutureDateException("From date or To date can not be a future date");
		}
		
		return rewardsService.calculateRewards(customerId,fromDate,toDate);
	}
}
