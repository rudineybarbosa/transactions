package com.pismo.transactions.errors;

public class IllegalOperationTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalOperationTypeException(String message) {
		super("Transação inválida. " + message);
	}
	
	

}
