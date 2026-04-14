package com.charter.rewardPoints.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.charter.rewardPoints.dto.RewardsResponse;
import com.charter.rewardPoints.dto.Transaction;
import com.charter.rewardPoints.exception.GenericException;
import com.charter.rewardPoints.exception.TransactionNotFoundException;
import com.charter.rewardPoints.repository.TransactionRepository;

@Service
public class RewardsServiceImpl implements RewardsService{

	private static final BigDecimal ZERO = BigDecimal.valueOf(0);
	private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
	private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
	
	private TransactionRepository txnRepository;

	public RewardsServiceImpl(TransactionRepository transactionRepository) {
		this.txnRepository = transactionRepository;
	}


	@Override
	public RewardsResponse calculateRewards(String customerId, LocalDate fromDate, LocalDate toDate) {
		Map<YearMonth, Integer> monthlyPoints = new HashMap<>();
		int total = 0;
		try {
			List<Transaction> txns = txnRepository.findByCustomerAndDateRange(customerId, fromDate, toDate);
			if(txns.isEmpty()) {
				throw new TransactionNotFoundException("No transactions found for customer "+customerId);
			}
			for(Transaction txn : txns) {
				if(txn.getTxnAmount() == null || txn.getTxnAmount().compareTo(ZERO)  <= 0) {
					continue;
				}
				int points = calculatePoints(txn.getTxnAmount());
				YearMonth month = YearMonth.from(txn.getTxnDate());
				monthlyPoints.merge(month, points, Integer::sum);
			}
			total = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();
		}catch (Exception e) { 
			throw new GenericException(e.getMessage());
		}
		return RewardsResponse.of(customerId, monthlyPoints, total);
	}


	private int calculatePoints(BigDecimal txnAmount) {
		int points = 0;
		if(txnAmount.compareTo(HUNDRED) > 0) {
			points += (txnAmount.subtract(HUNDRED).intValue() * 2) + FIFTY.intValue();
		}else if(txnAmount.compareTo(FIFTY) > 0) {
			points += txnAmount.subtract(FIFTY).intValue();
		}
		return points;
	}

}
