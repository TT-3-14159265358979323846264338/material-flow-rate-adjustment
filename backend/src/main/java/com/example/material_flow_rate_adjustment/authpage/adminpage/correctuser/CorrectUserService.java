package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;
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
	
	@Transactional
	public boolean correctUserRecord(int id, CorrectUserRecord data, String loginUser) {
		AccountSQL targetAccount = utility.getAccountSQL(id);
		AccountSQL loginAccount = utility.getAccountSQL(loginUser);
		AccountHistorySQL newHistory = createHistorySQL(targetAccount);
		if(data.isDeleted()) {
			delete(targetAccount, loginAccount, newHistory);
			return true;
		}
		setLoginName(targetAccount, newHistory, data.newLoginName());
		setDisplayedName(targetAccount, newHistory, data.newDisplayedName());
		setRole(targetAccount, loginAccount, newHistory, data.newRole());
		saveData(targetAccount, loginAccount, newHistory);
		return false;
	}
	
	AccountHistorySQL createHistorySQL(AccountSQL targetAccount) {
		AccountHistorySQL newHistory = new AccountHistorySQL();
		newHistory.setOldLoginUser(targetAccount.getLoginUser());
		newHistory.setOldDisplayedUser(targetAccount.getDisplayedUser());
		newHistory.setOldRole(targetAccount.getRole());
		newHistory.setHasDeletedOld(targetAccount.getHasDeleted());
		return newHistory;
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
		newHistory.setNewDisplayedUser(newDisplayedName);
		targetAccount.setDisplayedUser(newDisplayedName);
	}
	
	void setRole(AccountSQL targetAccount, AccountSQL loginAccount, AccountHistorySQL newHistory, AccountRole role) {
		if(targetAccount.getRole().equals(role.name())) {
			return;
		}
		if(isFinalAdmin(targetAccount, loginAccount)) {
			throw new DataBaseException("最後の管理者ユーザーの権限を変更できません。");
		}
		newHistory.setTargetId(targetAccount.getId());
		newHistory.setNewRole(role.name());
		targetAccount.setRole(role.name());
	}
	
	boolean isFinalAdmin(AccountSQL targetAccount, AccountSQL loginAccount) {
		if(targetAccount.equals(loginAccount)) {
			return repository.countByRole(AccountRole.ADMIN.name()) == 1;
		}
		return false;
	}
	
	void saveData(AccountSQL targetAccount, AccountSQL loginAccount, AccountHistorySQL newHistory) {
		utility.changeData(loginAccount, targetAccount, repository, newHistory, historyRepository);
	}
	
	void delete(AccountSQL targetAccount, AccountSQL loginAccount, AccountHistorySQL newHistory) {
		if(isFinalAdmin(targetAccount, loginAccount)) {
			throw new DataBaseException("最後の管理者ユーザーを消去することはできません。");
		}
		utility.deleteData(loginAccount, targetAccount, repository, newHistory, historyRepository);
	}
}