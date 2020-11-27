package com.pismo.transactions.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pismo.transactions.domain.model.Transaction;
import com.pismo.transactions.repository.model.IRepository;

@Repository
public class TransactionRepositoryImpl implements IRepository<Transaction> {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Transaction> list() {
		return manager.createQuery("from Transaction", Transaction.class).getResultList();
	}

	@Override
	public Transaction findById(Long id) {
		return manager.find(Transaction.class, id);
	}

	@Transactional
	@Override
	public Transaction save(Transaction entity) {
		return manager.merge(entity);
	}

	@Transactional
	@Override
	public void remove(Transaction entity) {
		entity = this.findById(entity.getId());
		manager.remove(entity);
	}

}
