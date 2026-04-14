package com.charter.rewardPoints.exception;

public class MissingDateException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public MissingDateException(String message) {
		super(message);
	}

}
