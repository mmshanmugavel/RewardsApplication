package com.charter.rewardPoints.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<String> handleGeneric(GenericException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(MissingDateException.class)
	public ResponseEntity<String> handleMissingDate(MissingDateException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(FutureDateException.class)
	public ResponseEntity<String> handleFutureDate(FutureDateException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<String> handleTransactionNotFound(TransactionNotFoundException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
