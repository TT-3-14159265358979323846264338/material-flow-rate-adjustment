package com.example.material_flow_rate_adjustment.commontest;

import java.util.List;
import java.util.stream.Stream;

import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultAccount {
	DUMMY_0("dummy_pass_0", "dummy_login_0", "dummy_0", AccountRole.ADMIN, false),
	DUMMY_1("dummy_pass_1", "dummy_login_1", "dummy_1", AccountRole.USER, false),
	DUMMY_2("dummy_pass_2", "dummy_login_2", "dummy_2", AccountRole.MANAGER, false),
	DUMMY_3("dummy_pass_3", "dummy_login_3", "dummy_3", AccountRole.ADMIN, true),
	DUMMY_4("dummy_pass_4", "dummy_login_4", "dummy_4", AccountRole.USER, true),
	DUMMY_5("dummy_pass_5", "dummy_login_5", "dummy_5", AccountRole.MANAGER, true);
	
	private final String password;
	private final String loginUser;
	private final String displayedUser;
	private final AccountRole role;
	private final boolean hasDeleted;
	
	public static List<AccountSQL> getAccountList(){
		return Stream.of(DefaultAccount.values()).map(DefaultAccount::getAccount).toList();
	}
	
	public AccountSQL getAccount() {
		return AccountSQL.builder()
						.password(password)
						.loginUser(loginUser)
						.displayedUser(displayedUser)
						.role(role)
						.hasDeleted(hasDeleted)
						.build();
	}
}