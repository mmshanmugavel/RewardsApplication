package com.charter.rewardpoints.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.charter.rewardpoints.dto.RewardsResponse;
import com.charter.rewardpoints.dto.Transaction;
import com.charter.rewardpoints.exception.FutureDateException;
import com.charter.rewardpoints.exception.MissingDateException;
import com.charter.rewardpoints.exception.TransactionNotFoundException;
import com.charter.rewardpoints.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private RewardsServiceImpl rewardsService;

	@Test
	void whenBothDatesNull_thenDefaultsToLast3Months() throws Exception {
		Mockito.when(transactionRepository.findByCustomerAndDateRange(any(), any()))
				.thenReturn(Collections.emptyList());
		assertThrows(TransactionNotFoundException.class, () -> rewardsService.calculateRewards(null, null));
	}

	@Test
	void whenOneDateNull_thenMissingDateException() throws Exception {
		assertThrows(MissingDateException.class, () -> rewardsService.calculateRewards(LocalDate.now(), null));
	}

	@Test
	void whenFutureDate_thenFutureDateException() throws Exception {
		LocalDate future = LocalDate.now().plusDays(1);
		assertThrows(FutureDateException.class, () -> rewardsService.calculateRewards(future, future));

	}

	@Test
	void whenFutureDate_thenToDateException() throws Exception {
		LocalDate future = LocalDate.now().plusDays(1);
		assertThrows(FutureDateException.class, () -> rewardsService.calculateRewards(future, LocalDate.now()));

	}

	@Test
	void whenValidDates_thenReturnsRewards() {
		LocalDate now = LocalDate.now();
		Transaction txn = mock(Transaction.class);
		when(txn.getCustomerId()).thenReturn("C001");
		when(txn.getTxnAmount()).thenReturn(new BigDecimal("120"));
		when(txn.getTxnDate()).thenReturn(now);
		when(transactionRepository.findByCustomerAndDateRange(any(), any())).thenReturn(List.of(txn));
		List<RewardsResponse> result = rewardsService.calculateRewards(now.minusMonths(1), now);
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	void decimalAmountCase_shouldRoundHalfUp() {
		Transaction txn = mock(Transaction.class);
		when(txn.getCustomerId()).thenReturn("C001");
		when(txn.getTxnAmount()).thenReturn(new BigDecimal("120.85"));
		when(txn.getTxnDate()).thenReturn(LocalDate.of(2026, 3, 15));
		when(transactionRepository.findByCustomerAndDateRange(any(), any())).thenReturn(List.of(txn));
		List<RewardsResponse> result = rewardsService.calculateRewards(LocalDate.of(2026, 3, 1),
				LocalDate.of(2026, 3, 31));
		assertEquals(1, result.size());
		RewardsResponse resp = result.get(0);
		Map<String, Integer> monthly = resp.getMonthlyRewards();
		assertTrue(monthly.containsKey("2026-03"));
		assertEquals(92, monthly.get("2026-03").intValue());
		assertEquals(92, resp.getTotalRewards());
	}

	@Test
	void negativeAmountCase_shouldIgnoreNegativeAmounts_andReturnEmpty() {
		Transaction txnNeg = new Transaction("C001", new BigDecimal("-50"), LocalDate.of(2026, 3, 10));
		when(transactionRepository.findByCustomerAndDateRange(any(), any())).thenReturn(List.of(txnNeg));
		List<RewardsResponse> result = rewardsService.calculateRewards(LocalDate.of(2026, 3, 1),
				LocalDate.of(2026, 3, 30));
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void successCase_multipleTransactionsSameMonth_shouldSumPoints() {
		Transaction t1 = mock(Transaction.class);
		when(t1.getCustomerId()).thenReturn("C002");
		when(t1.getTxnAmount()).thenReturn(new BigDecimal("120"));
		when(t1.getTxnDate()).thenReturn(LocalDate.of(2026, 5, 5));
		Transaction t2 = mock(Transaction.class);
		when(t2.getCustomerId()).thenReturn("C002");
		when(t2.getTxnAmount()).thenReturn(new BigDecimal("75"));
		when(t2.getTxnDate()).thenReturn(LocalDate.of(2026, 5, 20));
		when(transactionRepository.findByCustomerAndDateRange(any(), any())).thenReturn(List.of(t1, t2));
		List<RewardsResponse> result = rewardsService.calculateRewards(LocalDate.of(2026, 2, 1),
				LocalDate.of(2026, 3, 31));
		assertEquals(1, result.size());
		RewardsResponse resp = result.get(0);
		assertEquals("C002", resp.getCustomerId());
		assertEquals(1, resp.getMonthlyRewards().size());
		assertEquals(115, resp.getTotalRewards());
		assertEquals(115, resp.getMonthlyRewards().get("2026-05").intValue());
	}

}
