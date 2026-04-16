package com.charter.rewardpoints.exception;

public class FutureDateException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public FutureDateException(String message) {
		super(message);
	}

}
