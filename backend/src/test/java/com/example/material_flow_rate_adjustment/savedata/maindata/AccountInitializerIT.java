package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;

@SpringBootTest
@ActiveProfiles("test")
class AccountInitializerIT {
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
	
	@Test
	@Transactional(readOnly = true)
	void initializeAccount() {
		List<AccountSQL> accounts = accountRepository.findAll();
		List<AccountHistorySQL> histories = historyRepository.findAll();
		assertThat(accounts)
			.singleElement()
			.satisfies(i -> {
				assertThat(i.getLoginUser()).isEqualTo(user);
				assertThat(i.getPassword()).isEqualTo(passwordEncoder.encode(password));
			});
		assertThat(histories).singleElement();
	}
}