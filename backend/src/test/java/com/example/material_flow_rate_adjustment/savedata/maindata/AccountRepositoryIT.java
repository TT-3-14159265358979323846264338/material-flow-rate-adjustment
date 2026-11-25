package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.authpage.adminpage.getuser.UserSortEnum;
import com.example.material_flow_rate_adjustment.commontest.BaseMainRepositoryIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.BaseSQLNoJoinColumn;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultAccount;

@SpringBootTest
@ActiveProfiles("test")
class AccountRepositoryIT extends BaseMainRepositoryIT<AccountRepository, AccountSQL>{
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	@Transactional
	void existsByLoginUser() {
		saveTestData();
		boolean findResult = accountRepository.existsByLoginUserAndHasDeletedFalse(DefaultAccount.DUMMY_0.getLoginUser());
		boolean notFindUserResult = accountRepository.existsByLoginUserAndHasDeletedFalse("dummy");
		boolean notFindDeletedResult = accountRepository.existsByLoginUserAndHasDeletedFalse(DefaultAccount.DUMMY_6.getLoginUser());
		assertThat(findResult).isTrue();
		assertThat(notFindUserResult).isFalse();
		assertThat(notFindDeletedResult).isFalse();
	}
	
	@Test
	@Transactional
	void existsByDisplayedUser() {
		saveTestData();
		boolean findResult = accountRepository.existsByDisplayedUserAndHasDeletedFalse(DefaultAccount.DUMMY_0.getDisplayedUser());
		boolean notFindUserResult = accountRepository.existsByDisplayedUserAndHasDeletedFalse("dummy");
		boolean notFindDeletedResult = accountRepository.existsByDisplayedUserAndHasDeletedFalse(DefaultAccount.DUMMY_6.getDisplayedUser());
		assertThat(findResult).isTrue();
		assertThat(notFindUserResult).isFalse();
		assertThat(notFindDeletedResult).isFalse();
	}
	
	@Test
	@Transactional
	void existsByRoleUser() {
		accountRepository.saveAll(BaseSQLNoJoinColumn.getSQLList(DefaultAccount.DUMMY_0, DefaultAccount.DUMMY_8));
		clear();
		boolean findResult = accountRepository.existsByRoleAndHasDeletedFalse(AccountRole.ADMIN);
		boolean notFindRoleResult = accountRepository.existsByRoleAndHasDeletedFalse(AccountRole.USER);
		boolean notFindDeletedResult = accountRepository.existsByRoleAndHasDeletedFalse(AccountRole.MANAGER);
		assertThat(findResult).isTrue();
		assertThat(notFindRoleResult).isFalse();
		assertThat(notFindDeletedResult).isFalse();
	}
	
	@Test
	@Transactional
	void countByRole() {
		saveTestData();
		long result = accountRepository.countByRoleAndHasDeletedFalse(AccountRole.ADMIN);
		//DefaultAccountの2件とAccountInitializerの1件の合計になる。
		assertThat(result).isEqualTo(3);
	}
	
	@Test
	@Transactional
	void findByLoginUser() {
		saveTestData();
		Optional<AccountSQL> findResult = accountRepository.findByLoginUserAndHasDeletedFalse(DefaultAccount.DUMMY_0.getLoginUser());
		Optional<AccountSQL> notFindUserResult = accountRepository.findByLoginUserAndHasDeletedFalse("dummy");
		Optional<AccountSQL> notFindDeletedResult = accountRepository.findByLoginUserAndHasDeletedFalse(DefaultAccount.DUMMY_8.getLoginUser());
		assertThat(findResult)
				.isPresent()
				.get()
				.satisfies(account -> {
					assertThat(account.getDisplayedUser()).isEqualTo(DefaultAccount.DUMMY_0.getDisplayedUser());
				});
		assertThat(notFindUserResult).isNotPresent();
		assertThat(notFindDeletedResult).isNotPresent();
	}
	
	@Test
	@Transactional
	void findByRoleIn() {
		saveTestData();
		List<AccountSQL> result = accountRepository.findByRoleInAndHasDeletedFalse(
														List.of(AccountRole.MANAGER, AccountRole.USER), 
														UserSortEnum.NAME.getUserSort(OrderSortEnum.DESCENDING));
		assertThat(result)
				.hasSize(4)
				.extracting(AccountSQL::getLoginUser)
				.containsExactly(DefaultAccount.DUMMY_5.getLoginUser(),
									DefaultAccount.DUMMY_4.getLoginUser(),
									DefaultAccount.DUMMY_3.getLoginUser(),
									DefaultAccount.DUMMY_2.getLoginUser());
	}
	
	@Override
	protected AccountRepository repository() {
		return accountRepository;
	}

	@Override
	protected List<AccountSQL> entity() {
		return BaseSQLNoJoinColumn.getSQLList(DefaultAccount.values());
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
	
	@Override
	protected List<Integer> savedIdHasDeletedFalse(List<AccountSQL> saveData) {
		List<Integer> savedId = super.savedIdHasDeletedFalse(saveData);
		//AccountInitializerで必ず最初に登録される(id = 1)
		return Stream.concat(Stream.of(1), savedId.stream()).toList();
	}
}