package com.pismo.transactions.errors;

public class AccountInexistentException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountInexistentException(String message) {
		super("Não existe Conta com id " + message);
	}

}
