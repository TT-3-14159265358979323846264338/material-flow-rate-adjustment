package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;

import org.assertj.core.api.ThrowingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.material_flow_rate_adjustment.commontest.DefaultAccount;
import com.example.material_flow_rate_adjustment.commontest.BaseSQLIT;

@SpringBootTest
@ActiveProfiles("test")
class AccountSQLIT extends BaseSQLIT<AccountRepository, AccountSQL>{
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	protected AccountRepository repository() {
		return accountRepository;
	}

	@Override
	protected AccountSQL entity() {
		return DefaultAccount.DUMMY_0.getAccount();
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
	
	@Override
	protected ThrowingConsumer<AccountSQL> assertLoad(){
		return account -> {
			assertThat(account.getLoginUser()).isEqualTo(DefaultAccount.DUMMY_0.getLoginUser());
			assertThat(account.getDisplayedUser()).isEqualTo(DefaultAccount.DUMMY_0.getDisplayedUser());
			assertThat(account.getPassword()).isEqualTo(DefaultAccount.DUMMY_0.getPassword());
			assertThat(account.getRole()).isEqualTo(DefaultAccount.DUMMY_0.getRole());
			assertThat(account.getHasDeleted()).isEqualTo(DefaultAccount.DUMMY_0.isHasDeleted());
			assertThat(account.getUpdatedDate()).isNotNull();
			assertThat(account.getCreatedDate()).isNotNull();
		};
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
	
	@Override
	protected List<String> grantBigDecimalParams(){
		return null;
	}
	
	@Override
	protected List<String> grantLazyFetch() {
		return null;
	}

	@Override
	protected List<DeleteData<?, ?>> grantOnDelete() {
		return null;
	}
}