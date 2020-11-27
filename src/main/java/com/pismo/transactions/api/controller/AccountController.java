package com.pismo.transactions.api.controller;

import java.net.URI;
import java.util.List;

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

import com.pismo.transactions.domain.model.Account;
import com.pismo.transactions.errors.AccountExistException;
import com.pismo.transactions.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService service;
	
	@GetMapping
	private List<Account> list(){
		return service.list();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		
		Account account = null;
		
		try {
		  account = service.findById(id);
			
		} catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.ok().body(account);
		
	}

	@GetMapping("/document/{documentNumber}")
	public ResponseEntity<Account> findByDocumentNumber(@PathVariable String documentNumber) {

		Account account = service.findByDocumentNumber(documentNumber);
		if(account == null) {
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(account);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Account account){

		try {
			
			account = service.save(account);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(account.getId()).toUri();
			
			return ResponseEntity.created(uri).body(account);
			
		} catch(AccountExistException e) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, 
			@RequestBody Account account) {
		Account accountFunded = null;
		
		try {
			accountFunded = service.findById(id);
			
			BeanUtils.copyProperties(account, accountFunded, "id");

			accountFunded = service.save(accountFunded);

			return ResponseEntity.ok().body(accountFunded);

		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
