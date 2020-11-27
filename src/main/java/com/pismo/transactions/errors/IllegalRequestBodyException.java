package com.pismo.transactions.errors;

public class IllegalRequestBodyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalRequestBodyException(String message) {
		super("Parâmetro inválido " + message);
	}
	
	

}
