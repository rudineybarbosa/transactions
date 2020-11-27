package com.pismo.transactions.repository.model;

import java.util.List;

public interface IRepository<T> {

	List<T> list();
	T findById(Long id);
	T save(T entity);
	void remove(T entity);
	
}