package com.example.material_flow_rate_adjustment.defaulttestdata;

import java.time.LocalDate;

import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultAccountHistory implements BaseHistorySQLNoJoinColumn<AccountHistorySQL>{
	DUMMY_0("OLU_0", "NLU_0", "ODU_0", "NDU_0", "OR_0", "NR_0", 0, false, true, "A_0", 0, "AU_0", LocalDate.of(2026, 11, 1)),
	DUMMY_1("OLU_1", "NLU_1", "ODU_1", "NDU_1", "OR_1", "NR_1", 1, false, true, "A_1", 0, "AU_1", LocalDate.of(2026, 11, 2));
	
	private final String oldLoginUser;
	private final String newLoginUser;
	private final String oldDisplayedUser;
	private final String newDisplayedUser;
	private final String oldRole;
	private final String newRole;
	private final Integer targetId;
	private final Boolean hasDeletedOld;
	private final Boolean hasDeletedNew;
	private final String action;
	private final Integer actionId;
	private final String actionUser;
	private final LocalDate date;
	
	@Override
	public String tableName() {
		return "account_history";
	}
	
	@Override
	public AccountHistorySQL getSQL() {
		return AccountHistorySQL.builder()
						.oldLoginUser(oldLoginUser)
						.newLoginUser(newLoginUser)
						.oldDisplayedUser(oldDisplayedUser)
						.newDisplayedUser(newDisplayedUser)
						.oldRole(oldRole)
						.newRole(newRole)
						.targetId(targetId)
						.hasDeletedOld(hasDeletedOld)
						.hasDeletedNew(hasDeletedNew)
						.action(action)
						.actionId(actionId)
						.actionUser(actionUser)
						.build();
	}
}