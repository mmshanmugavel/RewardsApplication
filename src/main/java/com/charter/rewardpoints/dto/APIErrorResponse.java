package com.charter.rewardpoints.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class APIErrorResponse {

	private int status;
	private String error;
	private String message;
	private LocalDateTime timestamp;
	private String path;
	
	public APIErrorResponse(int status, String error, String message, LocalDateTime timestamp, String path) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
		this.timestamp = timestamp;
		this.path = path;
	}
	
}
