package com.pismo.transactions.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String documentNumber;

	public Account(String documentNumber) {
		super();
		this.documentNumber = documentNumber;
	}

	public Account() {
	}
	
	
}
