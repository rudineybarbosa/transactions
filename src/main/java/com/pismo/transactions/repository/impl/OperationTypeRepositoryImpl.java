package com.pismo.transactions.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pismo.transactions.domain.model.OperationType;
import com.pismo.transactions.repository.model.IRepository;

@Repository
public class OperationTypeRepositoryImpl implements IRepository<OperationType> {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<OperationType> list() {
		return manager.createQuery("from OperationType", OperationType.class).getResultList();
	}

	@Override
	public OperationType findById(Long id) {
		return manager.find(OperationType.class, id);
	}

	@Transactional
	@Override
	public OperationType save(OperationType entity) {
		return manager.merge(entity);
	}

	@Transactional
	@Override
	public void remove(OperationType entity) {
		entity = this.findById(entity.getId());
		manager.remove(entity);
	}
	
}
