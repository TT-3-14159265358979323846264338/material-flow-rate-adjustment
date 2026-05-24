package com.example.material_flow_rate_adjustment.authpage.correctuser;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
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
	private final PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public List<Account> getUser() {
		return repository.findAll().stream().map(this::createAccount).toList();
	}
	
	Account createAccount(AccountSQL account) {
		return new Account(account.getId(), account.getUser(), account.getRole());
	}
	
	record Account(int id, String username, String role) {};
	
	@Transactional
	public void adminCorrectOwnData(AdminCorrectOwnData data, String loginUser) {
		AccountSQL loginAccount = getAccountSQL(Integer.parseInt(loginUser));
		AccountHistorySQL newHistory = createHistorySQL();
		setUsername(loginAccount, newHistory, data.newName());
		setPassword(loginAccount, data.oldPass(), data.newPass());
		setHistory(loginAccount, newHistory);
	}
	
	@Transactional
	public boolean adminCorrectUserData(AdminCorrectUserData data, String loginUser) {
		AccountSQL targetAccount = getAccountSQL(data.targetId());
		AccountSQL loginAccount = getAccountSQL(Integer.parseInt(loginUser));
		AccountHistorySQL newHistory = createHistorySQL();
		if(hasDeleted(targetAccount, loginAccount, newHistory, data.isDeleted())) {
			return true;
		}
		setUsername(targetAccount, newHistory, data.newName());
		setRole(targetAccount, newHistory, data.newRole());
		setHistory(loginAccount, newHistory);
		return false;
	}
	
	AccountHistorySQL createHistorySQL() {
		return new AccountHistorySQL();
	}
	
	void setHistory(AccountSQL loginAccount, AccountHistorySQL newHistory) {
		if(!StringUtils.hasLength(newHistory.getNewUser()) && !StringUtils.hasLength(newHistory.getNewRole())) {
			return;
		}
		saveHistory(loginAccount, newHistory, HistoryEnum.CHANGE);
	}
	
	void saveHistory(AccountSQL loginAccount, AccountHistorySQL newHistory, HistoryEnum code) {
		newHistory.setAction(code.name());
		newHistory.setActionId(loginAccount.getId());
		newHistory.setActionUser(loginAccount.getUser());
		historyRepository.save(newHistory);
	}
	
	AccountSQL getAccountSQL(int loginUser) {
		return repository.findById(loginUser)
				.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
	}
	
	void setUsername(AccountSQL targetAccount, AccountHistorySQL newHistory, String newName) {
		if(!StringUtils.hasLength(newName)) {
			return;
		}
		if(targetAccount.getUser().equals(newName)) {
			return;
		}
		if(repository.existsByUser(newName)) {
			throw new DataBaseException("同名のユーザーは登録できません。");
		}
		newHistory.setTargetId(targetAccount.getId());
		newHistory.setOldUser(targetAccount.getUser());
		newHistory.setNewUser(newName);
		targetAccount.setUser(newName);
	}
	
	void setPassword(AccountSQL targetAccount, String oldPass, String newPass) {
		if(StringUtils.hasLength(oldPass) && StringUtils.hasLength(newPass)) {
			if(passwordEncoder.matches(oldPass, targetAccount.getPassword())) {
				targetAccount.setPassword(passwordEncoder.encode(newPass));
			}else {
				throw new DataBaseException("以前のパスワードが一致しないため、処理を停止しました。");
			}
		}
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
			newHistory.setOldUser(targetAccount.getUser());
			newHistory.setOldRole(targetAccount.getRole());
			repository.delete(targetAccount);
			saveHistory(loginAccount, newHistory, HistoryEnum.DELETE);
		}
		return isDeleted;
	}
}