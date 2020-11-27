package com.pismo.transactions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.pismo.transactions.domain.model.Account;
import com.pismo.transactions.domain.model.OperationType;
import com.pismo.transactions.domain.model.Transaction;
import com.pismo.transactions.errors.AccountExistException;
import com.pismo.transactions.errors.IllegalOperationTypeException;
import com.pismo.transactions.errors.TransactionInexistentException;
import com.pismo.transactions.repository.impl.AccountRepositoryImpl;
import com.pismo.transactions.repository.impl.OperationTypeRepositoryImpl;
import com.pismo.transactions.repository.impl.TransactionRepositoryImpl;
import com.pismo.transactions.service.AccountService;
import com.pismo.transactions.service.OperationTypeService;
import com.pismo.transactions.service.TransactionService;

@SpringBootTest
class TransactionsApplicationTests {

	@Mock
	private AccountRepositoryImpl accountRepository;

	@InjectMocks
	private AccountService accountService;

	@Mock
	private OperationTypeRepositoryImpl operationRepository;

	@InjectMocks
	private OperationTypeService operationServiceInjected;

	@Mock
	private OperationTypeService operationService;

	@Mock
	private TransactionRepositoryImpl transactionRepository;

	@InjectMocks
	private TransactionService transactionService;
	
	@Mock
	private Account accountMock;
	
	@Test
	void contextLoads() {
	}

//	@Test
	public void whenSaveIllegalTransactionShouldGetError() {
		Long id = 1l;
		
		BigDecimal amountPositive = new BigDecimal(123.00);
		OperationType operation = new OperationType(id, "COMPRA ...");
		
		Transaction transaction = new Transaction(amountPositive, accountMock, operation);
		
		when(operationServiceInjected.findById(id)).thenReturn(operation);
		
		 Assertions.assertThrows(IllegalOperationTypeException.class, () -> {
			 transactionService.save(transaction);
		  });
	}

	@Test
	public void whenSearchInexistentTransactionShouldGetError() {
		long id = 9999l;
		
		when(transactionRepository.findById(id)).thenReturn(null);
		
		Assertions.assertThrows(TransactionInexistentException.class, () -> {
			transactionService.findById(id);
		});
	}
	
	@Test
	public void whenSaveAccountAlreadyExistShouldGetError() {
		
		String documentNumber = "112345679";

		Account account = new Account(documentNumber);
		
		when(accountRepository.findByDocumentNumber(documentNumber))
			.thenReturn(account);
		
		Assertions.assertThrows(AccountExistException.class, () -> {
 			accountService.save(account);
	  });
	}

	@Test
	public void whenSearchInexistentAccountShouldGetError() {
		long id = 9999l;
		
		when(transactionRepository.findById(id)).thenReturn(null);
		
		 Assertions.assertThrows(TransactionInexistentException.class, () -> {
			 transactionService.findById(id);
		  });
	}
	
	@Test
	public void whenSaveNewAccountShouldGetSuccess() {
		
		when(accountService.save(accountMock)).thenReturn(accountMock);
		
		Account account = accountService.save(accountMock);
		
		assertEquals(accountMock.getDocumentNumber(), account.getDocumentNumber());
	}
}
