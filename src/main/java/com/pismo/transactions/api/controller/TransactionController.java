package com.pismo.transactions.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pismo.transactions.domain.model.Transaction;
import com.pismo.transactions.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	private TransactionService service;
	
	@GetMapping
	private List<Transaction> list(){
		return service.list();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Transaction transaction = null; 
				
		try {
			transaction = service.findById(id);
			
			if(transaction != null) {
				
				return ResponseEntity.ok().body(transaction);
			}
			
		} catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
//	public ResponseEntity<Transaction> save(@RequestBody Transaction transaction){ //Object Orientation
	public ResponseEntity<Object> save(@RequestBody Map<String, Object> transactionMap){ //Just simple json
		
		Object accountId = transactionMap.get("accountId");
		Object operationId = transactionMap.get("operationId");
		Object amount = transactionMap.get("amount");
		
		Transaction transaction = null;
		try {
			transaction = 
					service.save(accountId, 
							operationId, 
							amount);
			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		if(transaction != null) {
		
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(transaction.getId()).toUri();
			
			return ResponseEntity.created(uri).body(transaction);
			
		} else {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("some error");
			
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Transaction> update(@PathVariable Long id, 
			@RequestBody Transaction transaction) {
		
		Transaction transactionFunded = service.findById(id);
		
		if(transactionFunded != null) {
			
			BeanUtils.copyProperties(transaction, transactionFunded, "id");
			
			transactionFunded = service.save(transactionFunded);
			
			return ResponseEntity.ok().body(transactionFunded);
		}
		
		return ResponseEntity.notFound().build();
	}
}
