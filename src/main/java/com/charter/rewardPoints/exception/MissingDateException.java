package com.charter.rewardpoints.exception;

public class MissingDateException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public MissingDateException(String message) {
		super(message);
	}

}
