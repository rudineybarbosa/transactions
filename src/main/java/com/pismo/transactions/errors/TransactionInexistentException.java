package com.pismo.transactions.errors;

public class TransactionInexistentException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionInexistentException(String message) {
		super("Não existe Transação com id " + message);
	}

}
