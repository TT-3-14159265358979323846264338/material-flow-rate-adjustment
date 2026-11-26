package com.example.material_flow_rate_adjustment.savedata.historydata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.assertj.core.api.ThrowingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.material_flow_rate_adjustment.commontest.BaseHistorySQLIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultAccountHistory;

@SpringBootTest
@ActiveProfiles("test")
class AccountHistorySQLIT extends BaseHistorySQLIT<AccountHistoryRepository, AccountHistorySQL>{
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	protected AccountHistoryRepository repository() {
		return accountHistoryRepository;
	}

	@Override
	protected AccountHistorySQL entity() {
		return DefaultAccountHistory.DUMMY_0.getSQL();
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

	@Override
	protected ThrowingConsumer<AccountHistorySQL> assertLoad() {
		return history -> {
			assertThat(history.getOldLoginUser()).isEqualTo(DefaultAccountHistory.DUMMY_0.getOldLoginUser());
			assertThat(history.getNewLoginUser()).isEqualTo(DefaultAccountHistory.DUMMY_0.getNewLoginUser());
			assertThat(history.getOldDisplayedUser()).isEqualTo(DefaultAccountHistory.DUMMY_0.getOldDisplayedUser());
			assertThat(history.getNewDisplayedUser()).isEqualTo(DefaultAccountHistory.DUMMY_0.getNewDisplayedUser());
			assertThat(history.getOldRole()).isEqualTo(DefaultAccountHistory.DUMMY_0.getOldRole());
			assertThat(history.getNewRole()).isEqualTo(DefaultAccountHistory.DUMMY_0.getNewRole());
			assertThat(history.getTargetId()).isEqualTo(DefaultAccountHistory.DUMMY_0.getTargetId());
			assertThat(history.getHasDeletedOld()).isEqualTo(DefaultAccountHistory.DUMMY_0.getHasDeletedOld());
			assertThat(history.getHasDeletedNew()).isEqualTo(DefaultAccountHistory.DUMMY_0.getHasDeletedNew());
			assertThat(history.getAction()).isEqualTo(DefaultAccountHistory.DUMMY_0.getAction());
			assertThat(history.getActionId()).isEqualTo(DefaultAccountHistory.DUMMY_0.getActionId());
			assertThat(history.getActionUser()).isEqualTo(DefaultAccountHistory.DUMMY_0.getActionUser());
		};
	}

	@Override
	protected List<String> grantBigDecimalParams() {
		return null;
	}

	@Override
	protected List<String> grantLengthVariableNameInHistory() {
		return List.of(
				"oldLoginUser",
				"newLoginUser",
				"oldDisplayedUser",
				"newDisplayedUser",
				"oldRole",
				"newRole");
	}

	@Override
	protected List<String> grantNotNullVariableNameInHistory() {
		return null;
	}

	@Override
	protected List<String> grantNotUpdateVariableNameInHistory() {
		return List.of(
				"oldLoginUser",
				"newLoginUser",
				"oldDisplayedUser",
				"newDisplayedUser",
				"oldRole",
				"newRole");
	}
}