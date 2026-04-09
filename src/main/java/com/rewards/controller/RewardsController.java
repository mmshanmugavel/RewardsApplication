package com.rewards.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.model.RewardsResponse;
import com.rewards.service.RewardsService;


@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;
	
	@GetMapping("/{customerId}")
	public RewardsResponse calculateRewards(@PathVariable String customerId, @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
		return rewardsService.calculateRewards(customerId,fromDate,toDate);
	}
	
	@GetMapping
	public String testingM() {
		System.out.println("API called succesfully");
		return "Welcome";
	}
}
