package com.pismo.transactions.validate;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.pismo.transactions.domain.model.OperationType;
import com.pismo.transactions.errors.IllegalOperationTypeException;

@Component
public class ValidateTransaction {

	public static boolean validateTransaction(OperationType operation, BigDecimal amount) {

		boolean isReducingAmount = operation.getDescription().contains("COMPRA") ||
				operation.getDescription().contains("SAQUE");
		
		boolean isPositiveAmount = amount.doubleValue() > 0;
		
		if(isReducingAmount && isPositiveAmount) {
		
			throw new IllegalOperationTypeException("Operações de saída devem possuir 'amount' com valor negativo.");
			
		} else if (!isReducingAmount && amount.doubleValue() < 0) {
			
			throw new IllegalOperationTypeException("Operações de deposito devem possuir 'amount' com valor positivo.");
		}

		return true;
		
	}
}
