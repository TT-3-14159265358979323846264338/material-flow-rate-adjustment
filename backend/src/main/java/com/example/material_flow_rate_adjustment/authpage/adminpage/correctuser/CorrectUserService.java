package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public String correctUser(int id, CorrectUserRecord data, String loginUser) {
		AccountSQL targetAccount = utility.getAccountSQL(id);
		AccountSQL loginAccount = utility.getAccountSQL(loginUser);
		AccountHistorySQL newHistory = createHistorySQL(targetAccount);
		if(data.isDeleted()) {
			delete(targetAccount, loginAccount, newHistory);
			return "対象のアカウントを削除しました";
		}
		setLoginName(targetAccount, newHistory, data.newLoginName());
		setDisplayedName(targetAccount, newHistory, data.newDisplayedName());
		setRole(targetAccount, loginAccount, newHistory, data.newRole());
		saveData(targetAccount, loginAccount, newHistory);
		return "対象のアカウントを修正しました";
	}
	
	AccountHistorySQL createHistorySQL(AccountSQL targetAccount) {
		return AccountHistorySQL.builder()
				.oldLoginUser(targetAccount.getLoginUser())
				.oldDisplayedUser(targetAccount.getDisplayedUser())
				.oldRole(targetAccount.getRole().name())
				.hasDeletedOld(targetAccount.getHasDeleted())
				.build();
	}
	
	void setLoginName(AccountSQL targetAccount, AccountHistorySQL newHistory, String newLoginName) {
		if(utility.isNotChanged(targetAccount.getLoginUser(), newLoginName)) {
			return;
		}
		if(repository.existsByLoginUserAndHasDeletedFalse(newLoginName)) {
			throw new DataBaseException("同名のログインユーザーは登録できません。");
		}
		utility.setTargetId(targetAccount, newHistory);
		newHistory.setNewLoginUser(newLoginName);
		targetAccount.setLoginUser(newLoginName);
	}
	
	void setDisplayedName(AccountSQL targetAccount, AccountHistorySQL newHistory, String newDisplayedName) {
		if(utility.isNotChanged(targetAccount.getDisplayedUser(), newDisplayedName)) {
			return;
		}
		if(repository.existsByDisplayedUserAndHasDeletedFalse(newDisplayedName)) {
			throw new DataBaseException("同名の表示ユーザーは登録できません。");
		}
		utility.setTargetId(targetAccount, newHistory);
		newHistory.setNewDisplayedUser(newDisplayedName);
		targetAccount.setDisplayedUser(newDisplayedName);
	}
	
	void setRole(AccountSQL targetAccount, AccountSQL loginAccount, AccountHistorySQL newHistory, AccountRole role) {
		if(utility.isNotChanged(targetAccount.getRole(), role)) {
			return;
		}
		if(isFinalAdmin(targetAccount, loginAccount)) {
			throw new DataBaseException("最後の管理者ユーザーの権限を変更できません。");
		}
		utility.setTargetId(targetAccount, newHistory);
		newHistory.setNewRole(role.name());
		targetAccount.setRole(role);
	}
	
	boolean isFinalAdmin(AccountSQL targetAccount, AccountSQL loginAccount) {
		if(targetAccount.equals(loginAccount)) {
			return repository.countByRoleAndHasDeletedFalse(AccountRole.ADMIN) == 1;
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