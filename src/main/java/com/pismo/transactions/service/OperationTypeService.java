package com.pismo.transactions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pismo.transactions.domain.model.OperationType;
import com.pismo.transactions.repository.impl.OperationTypeRepositoryImpl;

@Service
public class OperationTypeService {

	@Autowired
	private OperationTypeRepositoryImpl operationRepository;
	
	public List<OperationType> list(){
		return operationRepository.list();
	}
	
	public OperationType findById(Long id) {
		return operationRepository.findById(id);
	}

	public OperationType save(OperationType entity) {
		
		return operationRepository.save(entity);
	}
	
	public void remove(OperationType entity) {
		operationRepository.remove(entity);
	}
	
}
