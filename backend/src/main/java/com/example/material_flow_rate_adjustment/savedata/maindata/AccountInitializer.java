package com.example.material_flow_rate_adjustment.savedata.maindata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;

@Component
public class AccountInitializer implements CommandLineRunner{
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountHistoryRepository historyRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${admin.user}")
	private String user;
	
	@Value("${admin.password}")
	private String password;
	
	@Override
	public void run(String... args) throws Exception {
		if (!accountRepository.existsByRole(AccountRole.ADMIN.name())) {
			AccountSQL newAccount = createAdminAccount();
			accountRepository.save(newAccount);
			AccountHistorySQL newHistory = createHistory(newAccount);
			historyRepository.save(newHistory);
		}
	}
	
	AccountSQL createAdminAccount() {
		AccountSQL newAccount = new AccountSQL();
		newAccount.setUser(user);
		newAccount.setPassword(passwordEncoder.encode(password));
		newAccount.setRole(AccountRole.ADMIN.name());
		return newAccount;
	}
	
	AccountHistorySQL createHistory(AccountSQL newAccount) {
		AccountHistorySQL newHistory = new AccountHistorySQL();
		newHistory.setTargetId(newAccount.getId());
		newHistory.setNewUser(newAccount.getUser());
		newHistory.setNewRole(newAccount.getRole());
		newHistory.setAction(HistoryEnum.CREATE.name());
		newHistory.setActionId(0);
		newHistory.setActionUser("system");
		return newHistory;
	}
}