package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.commontest.DefaultAccount;
import com.example.material_flow_rate_adjustment.commontest.BaseSQLIT;

@SpringBootTest
@ActiveProfiles("test")
class AccountSQLIT extends BaseSQLIT<AccountRepository, AccountSQL>{
	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	@Transactional
	void saveAndLoad() {
		int id = saveTestData().getId();
		AccountSQL account = accountRepository.findById(id).get();
		assertThat(account.getLoginUser()).isEqualTo(DefaultAccount.DUMMY_0.getLoginUser());
		assertThat(account.getDisplayedUser()).isEqualTo(DefaultAccount.DUMMY_0.getDisplayedUser());
		assertThat(account.getPassword()).isEqualTo(DefaultAccount.DUMMY_0.getPassword());
		assertThat(account.getRole()).isEqualTo(DefaultAccount.DUMMY_0.getRole());
		assertThat(account.getHasDeleted()).isEqualTo(DefaultAccount.DUMMY_0.isHasDeleted());
		assertThat(account.getUpdatedDate()).isNotNull();
		assertThat(account.getCreatedDate()).isNotNull();
	}

	@Override
	protected AccountRepository repository() {
		return accountRepository;
	}

	@Override
	protected AccountSQL entity() {
		return DefaultAccount.DUMMY_0.getAccount();
	}

	@Override
	protected Consumer<AccountSQL> changeMethod() {
		return account -> account.setLoginUser("change");
	}
	
	@Override
	protected List<String> grantLengthVariableName(){
		return List.of(
				"loginUser",
				"displayedUser",
				"password");
	}
	
	@Override
	protected List<String> grantUniqueVariableName(){
		return List.of(
				"loginUser",
				"displayedUser");
	}
	
	@Override
	protected List<String> grantNotNullVariableName(){
		return List.of(
				"loginUser",
				"displayedUser",
				"password",
				"role");
	}
}