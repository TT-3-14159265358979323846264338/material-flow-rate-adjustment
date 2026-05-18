package com.example.material_flow_rate_adjustment.authpage.newuser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewUserService {
	private final AccountRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public String createNewUser(String username, String role){
		if(repository.existsByUser(username)) {
			return "";
		}
		//初期パスワードはユーザーにしている。
		AccountSQL newAccount = createNewAccount(username, username, role);
		repository.save(newAccount);
		return username;
	}
	
	AccountSQL createNewAccount(String username, String password, String role) {
		AccountSQL newAccount = new AccountSQL();
		newAccount.setUser(username);
		newAccount.setPassword(passwordEncoder.encode(password));
		newAccount.setRole(role);
		return newAccount;
	}
}