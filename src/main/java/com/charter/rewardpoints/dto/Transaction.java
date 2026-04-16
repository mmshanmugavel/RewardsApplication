package com.charter.rewardpoints.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class Transaction {

	private String customerId;
	private BigDecimal txnAmount;
	private LocalDate txnDate;

	public Transaction(String id, BigDecimal amount, LocalDate date) {
		this.customerId = id;
		this.txnAmount = amount;
		this.txnDate = date;
	}
}
