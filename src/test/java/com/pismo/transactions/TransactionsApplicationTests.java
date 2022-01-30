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
import com.pismo.transactions.errors.AccountWithoutLimitException;
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

	@Mock //this MOCK will be inject on AccountService bellow
	private AccountRepositoryImpl accountRepositoryMocked;
	//Explanation: https://qastack.com.br/programming/16467685/difference-between-mock-and-injectmocks

	@InjectMocks
	private AccountService accountServiceInjectMock;

	@Mock
	private AccountService accountServiceMocked;

	@Mock
	private OperationTypeRepositoryImpl operationRepositoryMocked;

	@Mock
	private OperationTypeService operationServiceMocked;

	@Mock
	private TransactionRepositoryImpl transactionRepositoryMocked;

	@InjectMocks
	private TransactionService transactionServiceInjectMock;
	
	@Mock
	private Account accountMocked;
	
	@Mock
	private Transaction transactionMocked;
	
	@Mock
	private OperationType operationMocked;
	
	@Test
	void contextLoads() {
	}

	@Test
	public void whenSaveTransactionWithoutLimitShouldGetError() {
		Long id = 1l;
		
		BigDecimal amountNegative = new BigDecimal(-123.00);
		BigDecimal availableCreditLimit = new BigDecimal(100.00);
		
		when(transactionMocked.getAccount()).thenReturn(accountMocked);
		when(transactionMocked.getAmount()).thenReturn(amountNegative);
		when(transactionMocked.getOperation()).thenReturn(operationMocked);
		
		when(operationServiceMocked.findById(id)).thenReturn(operationMocked);
		when(operationMocked.getDescription()).thenReturn("COMPRA ...");
		when(operationMocked.getId()).thenReturn(id);
		
		when(accountMocked.getId()).thenReturn(id);
		when(accountMocked.getAvailableCreditLimit()).thenReturn(availableCreditLimit);
		
		when(accountServiceMocked.findById(id)).thenReturn(accountMocked);
		when(operationServiceMocked.findById(id))
			.thenReturn(operationMocked);
		
		Assertions.assertThrows(AccountWithoutLimitException.class, () -> {
		 transactionServiceInjectMock.save(transactionMocked);
		});
	}

	@Test
	public void whenSaveIllegalTransactionShouldGetError() {
		Long id = 1l;
		
		BigDecimal amountPositive = new BigDecimal(123.00);
		
		when(transactionMocked.getAccount()).thenReturn(accountMocked);
		when(transactionMocked.getAmount()).thenReturn(amountPositive);
		when(transactionMocked.getOperation()).thenReturn(operationMocked);
		
		when(operationServiceMocked.findById(id)).thenReturn(operationMocked);
		when(operationMocked.getDescription()).thenReturn("COMPRA ...");
		when(operationMocked.getId()).thenReturn(id);
		
		when(operationServiceMocked.findById(id)).thenReturn(operationMocked);
		
		Assertions.assertThrows(IllegalOperationTypeException.class, () -> {
			transactionServiceInjectMock.save(transactionMocked);
		});
	}

	@Test
	public void whenSearchInexistentTransactionShouldGetError() {
		long id = 9999l;
		
		when(transactionRepositoryMocked.findById(id)).thenReturn(null);
		
		Assertions.assertThrows(TransactionInexistentException.class, () -> {
			transactionServiceInjectMock.findById(id);
		});
	}
	
	@Test
	public void whenSaveAccountAlreadyExistShouldGetError() {
		
		String documentNumber = "112345679";
		
		when(accountMocked.getDocumentNumber()).thenReturn(documentNumber);

		when(accountRepositoryMocked.findByDocumentNumber(documentNumber))
			.thenReturn(accountMocked);
		
		Assertions.assertThrows(AccountExistException.class, () -> {
 			accountServiceInjectMock.save(accountMocked);
	  });
	}

	@Test
	public void whenSearchInexistentAccountShouldGetError() {
		long id = 9999l;
		
		when(transactionRepositoryMocked.findById(id)).thenReturn(null);
		
		 Assertions.assertThrows(TransactionInexistentException.class, () -> {
			 transactionServiceInjectMock.findById(id);
		  });
	}
	
	@Test
	public void whenSaveNewAccountShouldGetSuccess() {
		
		when(accountMocked.getDocumentNumber()).thenReturn("1234");
		
		when(accountRepositoryMocked.save(accountMocked)).thenReturn(accountMocked);
		
		Account account = accountServiceInjectMock.save(accountMocked);
		
		assertEquals(accountMocked.getDocumentNumber(), account.getDocumentNumber());
	}
}
