package com.pismo.transactions.errors;

public class AccountWithouLimitException extends RuntimeException{

	
	public AccountWithouLimitException() {
	}
	
	public AccountWithouLimitException(String message) {
		super(message);
	}
	
	
}
