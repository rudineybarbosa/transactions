package com.pismo.transactions.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pismo.transactions.domain.model.Account;
import com.pismo.transactions.repository.model.IRepository;

@Repository
public class AccountRepositoryImpl implements IRepository<Account> {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Account> list() {
		return manager.createQuery("from Account", Account.class).getResultList();
	}

	@Override
	public Account findById(Long id) {
		return manager.find(Account.class, id);
	}

	@Transactional
	@Override
	public Account save(Account entity) {
		return manager.merge(entity);
	}

	@Transactional
	@Override
	public void remove(Account entity) {
		entity = this.findById(entity.getId());
		manager.remove(entity);
	}
	
	public Account findByDocumentNumber(String documentNumber) {
		String query = "Select a.id, a.document_number from account a where document_number = :document_number";
		
		Query nativeQuery = manager.createNativeQuery(query, Account.class);
		
		nativeQuery.setParameter("document_number", documentNumber);
		
		try {
			Account accountFounded = (Account) nativeQuery.getSingleResult();
			
			return accountFounded;
			
		} catch(NoResultException e) {
			
			return null;
		}
		
		
	}

}
