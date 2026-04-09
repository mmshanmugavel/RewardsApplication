package com.rewards.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Transaction {

	private String customerId;
	private Double txnAmount;
	private LocalDate txnDate;
	
	public Transaction(String id, double amount, LocalDate date) { 
		this.customerId = id;
		this.txnAmount = amount;
		this.txnDate = date;
	}
}
