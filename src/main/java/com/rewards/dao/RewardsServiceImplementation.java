package com.rewards.dao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rewards.model.RewardsResponse;
import com.rewards.model.Transaction;
import com.rewards.service.RewardsService;

@Service
public class RewardsServiceImplementation implements RewardsService{
	
	final List<Transaction> transactions = Arrays.asList(
			new Transaction("C001",120, LocalDate.of(2026, 1, 10)),
			new Transaction("C001",175, LocalDate.of(2026, 1, 14)),
			new Transaction("C001",65, LocalDate.of(2026, 1, 17)),
			new Transaction("C002",230, LocalDate.of(2026, 1, 25)),
			new Transaction("C001",40, LocalDate.of(2026, 2, 5)),
			new Transaction("C001",148, LocalDate.of(2026, 2, 15)),
			new Transaction("C001",110, LocalDate.of(2026, 3, 8)),
			new Transaction("C001",20, LocalDate.of(2026, 3, 11))
			);
			

	@Override
	public RewardsResponse calculateRewards(String customerId, LocalDate fromDate, LocalDate toDate) {
		Map<YearMonth, Integer> monthlyPoints = new HashMap<>();
		int total = 0;
		List<Transaction> txns = new ArrayList<Transaction>();
		try {
			txns = transactions.stream().filter(custId -> custId.getCustomerId().equals(customerId))
					.filter(date -> !date.getTxnDate().isBefore(fromDate) && !date.getTxnDate().isAfter(toDate)).collect(Collectors.toList());
			
			for(Transaction txn : txns) {
				int points = CalculatePoints(txn.getTxnAmount());
				YearMonth month = YearMonth.from(txn.getTxnDate());
				monthlyPoints.merge(month, points, Integer::sum);
			}
			total = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
		}catch (Exception e) { 
			e.printStackTrace();
		}
		return RewardsResponse.of(customerId, monthlyPoints, total);
	}


	private int CalculatePoints(double txnAmount) {
		int points = 0;
		if(txnAmount > 100) {
			points += ((txnAmount - 100) * 2) + 50;//2 points for every dollar above $100 + 1 point for every dollar between $50 - $100 (So I've added default 50 points)
		}else if(txnAmount > 50) {
			points += txnAmount - 50;
		}
		return points;
	}

}
