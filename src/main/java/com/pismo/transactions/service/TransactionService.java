package com.pismo.transactions.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pismo.transactions.domain.model.Account;
import com.pismo.transactions.domain.model.OperationType;
import com.pismo.transactions.domain.model.Transaction;
import com.pismo.transactions.errors.TransactionInexistentException;
import com.pismo.transactions.repository.impl.TransactionRepositoryImpl;
import com.pismo.transactions.validate.ValidateTransaction;
import com.pismo.transactions.validate.ValidateTransactionRequestParameters;

@Service
public class TransactionService {
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private OperationTypeService operationService;

	@Autowired
	private TransactionRepositoryImpl transactionRepository;

	public List<Transaction> list(){
		return transactionRepository.list();
	}
	
	public Transaction findById(Long id) {
		Transaction transaction = transactionRepository.findById(id);
		if(transaction == null) {
			
			throw new TransactionInexistentException(String.valueOf(id));
		}
		
		return transaction;
	}

	public Transaction save(Transaction transaction) {
		
		boolean isValidOperation = isValidTransaction(transaction);
		if(isValidOperation) {
			
			return transactionRepository.save(transaction);
			
		} else {
			
			return null;
		}
	}

	public Transaction save(Object accountIdTemp, Object operationIdTemp, Object amountTemp) {
		
		ValidateTransactionRequestParameters
			.validateTransactionParameters(accountIdTemp, operationIdTemp, amountTemp);
		
		Integer accountId = (Integer) accountIdTemp;
		Account account = accountService.findById(Long.valueOf(accountId));
		if (account == null) {
			return null;
		}
		
		Integer operationId = (Integer) operationIdTemp;
		OperationType operation = operationService.findById(Long.valueOf(operationId));
		if(operation == null) {
			return null;
		}
		
		Double amount = (Double) amountTemp;
		if(amount.doubleValue() == 0) {
			return null;
		}
		
		Transaction transaction = 
				new Transaction(BigDecimal.valueOf(amount), account, operation);
		
		transaction.setEventDate(LocalDateTime.now());
		
		return this.save(transaction);
		
	}
	
	public void remove(Transaction entity) {
		transactionRepository.remove(entity);
	}
	
	private boolean isValidTransaction(Transaction transaction) {
		
		OperationType operation = 
				operationService.findById(transaction.getOperation().getId());
		
		return ValidateTransaction
					.validateTransaction(operation, transaction.getAmount());
		
	}
}
