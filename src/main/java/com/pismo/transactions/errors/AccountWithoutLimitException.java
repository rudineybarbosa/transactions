package com.pismo.transactions.errors;

public class AccountWithoutLimitException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountWithoutLimitException() {
	}
	
	public AccountWithoutLimitException(String message) {
		super(message);
	}
	
	
}
