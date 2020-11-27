package com.pismo.transactions.errors;

public class AccountExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountExistException(String message) {
		super("Conta já existente com o número de documento " + message);
	}

}
