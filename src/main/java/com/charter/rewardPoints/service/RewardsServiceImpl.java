package com.charter.rewardpoints.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.charter.rewardpoints.dto.RewardsResponse;
import com.charter.rewardpoints.dto.Transaction;
import com.charter.rewardpoints.exception.FutureDateException;
import com.charter.rewardpoints.exception.InvalidDateRangeException;
import com.charter.rewardpoints.exception.MissingDateException;
import com.charter.rewardpoints.exception.TransactionNotFoundException;
import com.charter.rewardpoints.repository.TransactionRepository;

@Service
public class RewardsServiceImpl implements RewardsService{

	private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
	private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
	private static LocalDate startDate;
	private static LocalDate endDate;
	
	@Autowired
	private TransactionRepository txnRepository;


	@Override
	public List<RewardsResponse> calculateRewards(LocalDate fromDate, LocalDate toDate) {
		List<RewardsResponse> response = new ArrayList<>();
		rewardCalcValidation(fromDate, toDate);
		List<Transaction> txns = txnRepository.findByCustomerAndDateRange(startDate, endDate);
		if(txns.isEmpty()) {
			throw new TransactionNotFoundException("No transactions found for startdate: "+startDate+", enddate: "+endDate);
		}else {
	        Map<String, Map<YearMonth, Integer>> groupedRewards = txns.stream()
	        		.filter(txn -> txn.getTxnAmount() != null && txn.getTxnAmount().compareTo(BigDecimal.ZERO) > 0)
	            .collect(Collectors.groupingBy(Transaction::getCustomerId,
	                Collectors.groupingBy(t -> YearMonth.from(t.getTxnDate()),
	                    Collectors.summingInt(t -> calculatePoints(t.getTxnAmount())))));
	        response = groupedRewards.entrySet().stream()
	        	    .map(entry -> {
	        	        String customerId = entry.getKey();
	        	        Map<String, Integer> monthlyRewards = entry.getValue().entrySet().stream()
	        	            .collect(Collectors.toMap(e -> e.getKey().toString(),Map.Entry::getValue,(a, b) -> a,LinkedHashMap::new));
	        	        int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();
	        	        return new RewardsResponse(customerId, monthlyRewards, totalRewards);
	        	    }).collect(Collectors.toList());
		}
		return response;
	}


	private int calculatePoints(BigDecimal txnAmount) {
		BigDecimal rewardPoints = BigDecimal.ZERO;
		if(txnAmount.compareTo(HUNDRED) > 0) {
			rewardPoints = txnAmount.subtract(HUNDRED).multiply(BigDecimal.valueOf(2)).add(FIFTY);
		}else if(txnAmount.compareTo(FIFTY) > 0) {
			rewardPoints = txnAmount.subtract(FIFTY);
		}
		return rewardPoints.setScale(0, RoundingMode.HALF_UP).intValue();
	}

	private void rewardCalcValidation(LocalDate fromDate, LocalDate toDate) {
		if(fromDate == null && toDate == null) {
			toDate = LocalDate.now();
			fromDate = toDate.minusMonths(3);
		}else if(fromDate == null || toDate == null) {
			throw new MissingDateException("StartDate and EndDate must be provided");
		}
		
		LocalDate today = LocalDate.now();
		
		if(fromDate.isAfter(today) || toDate.isAfter(today)) {
			throw new FutureDateException("StartDate or EndDate can not be a future date");
		}
		if(fromDate.isAfter(toDate)) {
			throw new InvalidDateRangeException("StartDate can not be after EndDate");
		}
		startDate = fromDate;
		endDate = toDate;
	}
}
