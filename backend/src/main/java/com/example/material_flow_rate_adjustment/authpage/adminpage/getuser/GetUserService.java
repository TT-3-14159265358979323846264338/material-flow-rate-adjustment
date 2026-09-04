package com.example.material_flow_rate_adjustment.authpage.adminpage.getuser;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserService {
	private final AccountRepository repository;
	
	@Transactional(readOnly = true)
	public List<Account> getUser(GetUserRecord userSort) {
		return repository.findByRoleInAndHasDeletedFalse(getTargetRole(userSort), userSort.target().getUserSort(userSort.order()))
						.stream()
						.map(this::createAccount)
						.toList();
	}
	
	List<String> getTargetRole(GetUserRecord userSort){
		if(userSort.isAdmin() == userSort.isUser() && userSort.isAdmin() == userSort.isManager()) {
			return Stream.of(AccountRole.values()).map(i -> i.name()).toList();
		}
		return Stream.of(
					userSort.isAdmin()? AccountRole.ADMIN: null,
					userSort.isUser()? AccountRole.USER: null,
					userSort.isManager()? AccountRole.MANAGER: null)
				.filter(Objects::nonNull)
				.map(i -> i.name())
				.toList();
	}
	
	Account createAccount(AccountSQL account) {
		return new Account(account.getId(), account.getLoginUser(), account.getDisplayedUser(), account.getRole());
	}
	
	record Account(int id, String loginName, String displayedName, String role) {};
}