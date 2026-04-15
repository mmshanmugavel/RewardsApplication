package com.charter.rewardpoints.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import com.charter.rewardpoints.dto.Transaction;

@Repository
public class TransactionRepository {

	public List<Transaction> findByCustomerAndDateRange(LocalDate fromDate, LocalDate toDate){
		final List<Transaction> transactions = Arrays.asList(
				new Transaction("C001",BigDecimal.valueOf(120.99), LocalDate.of(2026, 1, 10)),
				new Transaction("C001",BigDecimal.valueOf(175), LocalDate.of(2026, 1, 14)),
				new Transaction("C001",BigDecimal.valueOf(65), LocalDate.of(2026, 1, 17)),
				new Transaction("C002",BigDecimal.valueOf(230), LocalDate.of(2026, 1, 25)),
				new Transaction("C001",BigDecimal.valueOf(40), LocalDate.of(2026, 2, 5)),
				new Transaction("C001",BigDecimal.valueOf(148), LocalDate.of(2026, 2, 15)),
				new Transaction("C001",BigDecimal.valueOf(110), LocalDate.of(2026, 3, 8)),
				new Transaction("C001",BigDecimal.valueOf(20), LocalDate.of(2026, 3, 11)),
				new Transaction("C003",BigDecimal.valueOf(138), LocalDate.of(2026, 1, 14)),
				new Transaction("C003",BigDecimal.valueOf(97), LocalDate.of(2026, 2, 24)),
				new Transaction("C003",BigDecimal.valueOf(0), LocalDate.of(2026, 2, 24)),
				new Transaction("C003",BigDecimal.valueOf(73.86), LocalDate.of(2026, 3, 26))
				);
		return transactions.stream().filter(txn -> !txn.getTxnDate().isBefore(fromDate) 
				&& !txn.getTxnDate().isAfter(toDate)).collect(Collectors.toList());
	}
}
