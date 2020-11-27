package com.pismo.transactions.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private LocalDateTime eventDate;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_TRANSACTION_ACCOUNT"))
	private Account account;
	
	@OneToOne
	@JoinColumn(foreignKey = @ForeignKey(name ="FK_TRANSACTION_OPERATION"))
	private OperationType operation;

	public Transaction(BigDecimal amount, Account account, OperationType operation) {
		super();
		this.amount = amount;
		this.account = account;
		this.operation = operation;
	}

	public Transaction() {
	}
	
	
}
