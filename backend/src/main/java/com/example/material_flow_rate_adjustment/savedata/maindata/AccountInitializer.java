package com.example.material_flow_rate_adjustment.savedata.maindata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountInitializer implements CommandLineRunner{
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${admin.user}")
	private String user;
	
	@Value("${admin.password}")
	private String password;
	
	@Override
	public void run(String... args) throws Exception {
		if (!accountRepository.existsByRole(AccountRole.ADMIN.name())) {
			AccountSQL newAccount = new AccountSQL();
			newAccount.setUser(user);
			newAccount.setPassword(passwordEncoder.encode(password));
			newAccount.setRole(AccountRole.ADMIN.name());
			accountRepository.save(newAccount);
		}
	}
}