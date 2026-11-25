package com.example.material_flow_rate_adjustment.savedata.historydata;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.material_flow_rate_adjustment.commontest.BaseHistoryRepositoryIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultAccountHistory;

@SpringBootTest
@ActiveProfiles("test")
class AccountHistoryRepositoryIT extends BaseHistoryRepositoryIT<AccountHistoryRepository, AccountHistorySQL, DefaultAccountHistory>{
	@Autowired
	private AccountHistoryRepository accountHistoryRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	protected AccountHistoryRepository repository() {
		return accountHistoryRepository;
	}
	
	@Override
	protected DefaultAccountHistory[] targetEnum() {
		return DefaultAccountHistory.values();
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

	@Override
	protected JdbcTemplate jdbcTemplate() {
		return jdbcTemplate;
	}
}