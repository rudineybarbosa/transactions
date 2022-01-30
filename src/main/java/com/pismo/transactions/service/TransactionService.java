package com.pismo.transactions.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pismo.transactions.domain.model.Account;
import com.pismo.transactions.domain.model.OperationType;
import com.pismo.transactions.domain.model.Transaction;
import com.pismo.transactions.errors.AccountWithoutLimitException;
import com.pismo.transactions.errors.TransactionInexistentException;
import com.pismo.transactions.repository.impl.TransactionRepositoryImpl;
import com.pismo.transactions.validate.ValidateAccount;
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

	@Transactional
	public Transaction save(Transaction transaction) {
		
		boolean isValidOperation = isValidOperation(transaction);
		
		Account account = accountService.findById(transaction.getAccount().getId());
		
		BigDecimal availableCreditLimit = account.getAvailableCreditLimit();
		
		verifyTransactionAmount(transaction, availableCreditLimit);
		
		if(isValidOperation) {
			
			transaction = transactionRepository.save(transaction);
		
			BigDecimal newAvailableCreditLimit = 
					account.getAvailableCreditLimit().add(transaction.getAmount());
			
			account.setAvailableCreditLimit(newAvailableCreditLimit);
			
			accountService.update(account);
			
			return transaction;
			
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
	
	private boolean isValidOperation(Transaction transaction) {
		
		OperationType operation = 
				operationService.findById(transaction.getOperation().getId());
		
		boolean isOutcomeOperation = this.isOutcomeOperation(operation);
		
		return ValidateTransaction
					.validateTransaction(operation, transaction.getAmount(),
							isOutcomeOperation);
		
	}
	
	private boolean isOutcomeOperation(OperationType operation) {
		return operation.getDescription().contains("COMPRA") ||
				operation.getDescription().contains("SAQUE");
	}
	
	private void verifyTransactionAmount(Transaction transaction, 
			BigDecimal availableCreditLimit) {
		
		boolean isOutcomeOperation = this.isOutcomeOperation(transaction.getOperation());
		
		if(isOutcomeOperation) {
			
			boolean hasAccountLimit = ValidateAccount
					.hasLimit(transaction.getAccount().getId(),
								transaction.getAmount(), 
								availableCreditLimit);
			
			if(!hasAccountLimit) {
				
				throw new AccountWithoutLimitException("Usário sem limite disponpivel");
				
			}
		}
		
	}
}
