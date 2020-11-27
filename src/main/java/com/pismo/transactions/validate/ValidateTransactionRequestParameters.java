package com.pismo.transactions.validate;

import org.springframework.stereotype.Component;

import com.pismo.transactions.errors.IllegalRequestBodyException;

@Component
public class ValidateTransactionRequestParameters {

	@SuppressWarnings("unused")
	public static void validateTransactionParameters(Object accountId, Object operationId, Object amount) {
		
		try {
			Integer id = (Integer) accountId;
		} catch(ClassCastException e) {
			throw new IllegalRequestBodyException("'accountId' deve ser número inteiro");
		}

		try {
			Integer id = (Integer) operationId;
		} catch(ClassCastException e) {
			throw new IllegalRequestBodyException("'operationId' deve ser número inteiro");
		}

		try {
			Double value= (Double) amount;
		} catch(ClassCastException e) {
			throw new IllegalRequestBodyException("'amount' deve ser número com casas decimais");
		}
		
	}
}
