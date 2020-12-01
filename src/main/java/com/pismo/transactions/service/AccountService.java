package com.pismo.transactions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pismo.transactions.domain.model.Account;
import com.pismo.transactions.errors.AccountExistException;
import com.pismo.transactions.errors.AccountInexistentException;
import com.pismo.transactions.repository.impl.AccountRepositoryImpl;

@Service
public class AccountService {

	@Autowired
	private AccountRepositoryImpl accountRepository;
	
	public List<Account> list(){
		return accountRepository.list();
	}
	
	public Account findById(Long id) {
		Account account = accountRepository.findById(id);
		
		if(account == null) {
			
			throw new AccountInexistentException(String.valueOf(id));
		}
		
		return account;
	}

	public Account findByDocumentNumber(String documentNumber) {

		Account account = accountRepository.findByDocumentNumber(documentNumber);
		
		return account;
	}
	
	public Account save(Account account) {
		
		Account accountFoundedByDocNumber = 
				accountRepository.findByDocumentNumber(account.getDocumentNumber());

		if(accountFoundedByDocNumber != null) {
		
			throw new AccountExistException(account.getDocumentNumber());
		}

		return accountRepository.save(account);
	}

	public Account update(Account account) {
		
		return accountRepository.save(account);
	}
	
	public void remove(Account entity) {
		accountRepository.remove(entity);
	}
	
}
