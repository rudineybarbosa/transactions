package com.pismo.transactions.validate;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ValidateAccount {

	public static boolean hasLimit(Long id, BigDecimal amount, 
			BigDecimal availableCreditLimit) {
		
		BigDecimal absAmount = amount.abs();
		
		return absAmount.compareTo(availableCreditLimit) <= 0;
	}
}
