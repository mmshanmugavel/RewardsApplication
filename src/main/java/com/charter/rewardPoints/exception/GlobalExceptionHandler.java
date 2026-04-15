package com.charter.rewardpoints.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.charter.rewardpoints.dto.APIErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private APIErrorResponse buiApiErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
		return new APIErrorResponse(status.value(), status.getReasonPhrase(), message, java.time.LocalDateTime.now(),
				request.getRequestURI());
	}

	@ExceptionHandler(MissingDateException.class)
	public ResponseEntity<APIErrorResponse> handleMissingDate(MissingDateException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest().body(buiApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
	}
	
	@ExceptionHandler(FutureDateException.class)
	public ResponseEntity<APIErrorResponse> handleFutureDate(FutureDateException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest().body(buiApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
	}
	
	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<APIErrorResponse> handleTransactionNotFound(TransactionNotFoundException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest().body(buiApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
	}
	
	@ExceptionHandler(InvalidDateRangeException.class)
	public ResponseEntity<APIErrorResponse> handleInvalidDateRange(InvalidDateRangeException ex, HttpServletRequest request) {
		return ResponseEntity.badRequest().body(buiApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
	}
}
