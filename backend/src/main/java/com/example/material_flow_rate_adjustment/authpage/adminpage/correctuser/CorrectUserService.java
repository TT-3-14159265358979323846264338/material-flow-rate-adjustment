package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectUserService {
	private final AccountRepository repository;
	private final AccountHistoryRepository historyRepository;
	private final UtilityService utility;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<Account> getUser() {
		return repository.findAll().stream().map(this::createAccount).toList();
	}
	
	Account createAccount(AccountSQL account) {
		return new Account(account.getId(), account.getLoginUser(), account.getDisplayedUser(), account.getRole());
	}
	
	record Account(int id, String loginName, String displayedName, String role) {};
	
	@Transactional
	public void adminCorrectOwnData(AdminCorrectOwnData data, String loginUser) {
		AccountSQL loginAccount = utility.getAccountSQL(loginUser);
		AccountHistorySQL newHistory = createHistorySQL();
		setLoginName(loginAccount, newHistory, data.newLoginName());
		setDisplayedName(loginAccount, newHistory, data.newDisplayedName());
		utility.setPassword(loginAccount, data.oldPass(), data.newPass());
		setHistory(loginAccount, newHistory);
	}
	
	@Transactional
	public boolean adminCorrectUserData(AdminCorrectUserData data, String loginUser) {
		AccountSQL targetAccount = utility.getAccountSQL(data.targetId());
		AccountSQL loginAccount = utility.getAccountSQL(loginUser);
		AccountHistorySQL newHistory = createHistorySQL();
		if(hasDeleted(targetAccount, loginAccount, newHistory, data.isDeleted())) {
			return true;
		}
		setLoginName(targetAccount, newHistory, data.newLoginName());
		setDisplayedName(targetAccount, newHistory, data.newDisplayedName());
		setRole(targetAccount, newHistory, data.newRole());
		setHistory(loginAccount, newHistory);
		return false;
	}
	
	AccountHistorySQL createHistorySQL() {
		return new AccountHistorySQL();
	}
	
	void setHistory(AccountSQL loginAccount, AccountHistorySQL newHistory) {
		historyService.saveHistory(loginAccount, newHistory, historyRepository, HistoryEnum.CHANGE);
	}
	
	void setLoginName(AccountSQL targetAccount, AccountHistorySQL newHistory, String newLoginName) {
		if(!StringUtils.hasLength(newLoginName)) {
			return;
		}
		if(targetAccount.getLoginUser().equals(newLoginName)) {
			return;
		}
		if(repository.existsByLoginUser(newLoginName)) {
			throw new DataBaseException("同名のログインユーザーは登録できません。");
		}
		newHistory.setTargetId(targetAccount.getId());
		newHistory.setOldLoginUser(targetAccount.getLoginUser());
		newHistory.setNewLoginUser(newLoginName);
		targetAccount.setLoginUser(newLoginName);
	}
	
	void setDisplayedName(AccountSQL targetAccount, AccountHistorySQL newHistory, String newDisplayedName) {
		if(!StringUtils.hasLength(newDisplayedName)) {
			return;
		}
		if(targetAccount.getDisplayedUser().equals(newDisplayedName)) {
			return;
		}
		if(repository.existsByDisplayedUser(newDisplayedName)) {
			throw new DataBaseException("同名の表示ユーザーは登録できません。");
		}
		newHistory.setTargetId(targetAccount.getId());
		newHistory.setOldDisplayedUser(targetAccount.getDisplayedUser());
		newHistory.setNewDisplayedUser(newDisplayedName);
		targetAccount.setDisplayedUser(newDisplayedName);
	}
	
	void setRole(AccountSQL targetAccount, AccountHistorySQL newHistory, AccountRole role) {
		if(targetAccount.getRole().equals(role.name())) {
			return;
		}
		newHistory.setTargetId(targetAccount.getId());
		newHistory.setOldRole(targetAccount.getRole());
		newHistory.setNewRole(role.name());
		targetAccount.setRole(role.name());
	}
	
	boolean hasDeleted(AccountSQL targetAccount, AccountSQL loginAccount, AccountHistorySQL newHistory, boolean isDeleted) {
		if(isDeleted) {
			newHistory.setTargetId(targetAccount.getId());
			newHistory.setOldLoginUser(targetAccount.getLoginUser());
			newHistory.setOldDisplayedUser(targetAccount.getDisplayedUser());
			newHistory.setOldRole(targetAccount.getRole());
			repository.delete(targetAccount);
			historyService.saveHistory(loginAccount, newHistory, historyRepository, HistoryEnum.DELETE);
		}
		return isDeleted;
	}
}