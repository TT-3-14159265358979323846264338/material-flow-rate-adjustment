package com.example.material_flow_rate_adjustment.authpage.correctuser;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectUserService {
	private final AccountRepository repository;
	
	@Transactional(readOnly = true)
	public List<Account> getUser() {
		return repository.findAll().stream().map(this::createAccount).toList();
	}
	
	Account createAccount (AccountSQL account) {
		return new Account(account.getId(), account.getUser(), account.getRole());
	}
	
	record Account(int id, String username, String role) {};
}