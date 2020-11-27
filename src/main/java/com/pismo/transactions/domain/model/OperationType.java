package com.pismo.transactions.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class OperationType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String description;

	public OperationType(Long id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public OperationType() {
	}
	
	
}
