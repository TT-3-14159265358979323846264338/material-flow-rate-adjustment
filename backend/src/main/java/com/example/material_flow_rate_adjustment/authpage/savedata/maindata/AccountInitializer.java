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
		if (!accountRepository.existsByRole(AccountRole.ADMIN)) {
			AccountSQL newAccount = createAdminAccount();
			accountRepository.save(newAccount);
			AccountHistorySQL newHistory = createHistory(newAccount);
			historyRepository.save(newHistory);
		}
	}
	
	AccountSQL createAdminAccount() {
		return AccountSQL.builder()
				.loginUser(user)
				.displayedUser("初期管理者")
				.password(passwordEncoder.encode(password))
				.role(AccountRole.ADMIN)
				.hasDeleted(false)
				.build();
	}
	
	AccountHistorySQL createHistory(AccountSQL newAccount) {
		return AccountHistorySQL.builder()
				.targetId(newAccount.getId())
				.newLoginUser(newAccount.getLoginUser())
				.newDisplayedUser(newAccount.getDisplayedUser())
				.newRole(newAccount.getRole().name())
				.hasDeletedNew(newAccount.getHasDeleted())
				.action(HistoryEnum.CREATE.name())
				.actionId(0)
				.actionUser("システム自動")
				.build();
	}
}