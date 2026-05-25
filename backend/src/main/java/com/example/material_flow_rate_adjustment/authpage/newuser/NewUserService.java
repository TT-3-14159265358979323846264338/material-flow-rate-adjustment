package com.example.material_flow_rate_adjustment.authpage.newuser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AccountHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewUserService {
	private final AccountRepository repository;
	private final AccountHistoryRepository historyRepository;
	private final PasswordEncoder passwordEncoder;
	private final UtilityService utility;
	
	@Transactional
	public String createNewUser(NewUser data, String loginUser){
		if(repository.existsByLoginUser(data.loginName())) {
			throw new DataBaseException("同名のログインユーザーは登録できません。");
		}
		if(repository.existsByDisplayedUser(data.displayedName())) {
			throw new DataBaseException("同名の表示ユーザーは登録できません。");
		}
		//初期パスワードはユーザーにしている。
		AccountSQL newAccount = createNewAccount(data.loginName(), data.displayedName(), data.loginName(), data.role().name());
		repository.save(newAccount);
		AccountHistorySQL newHistory = createNewHistory(newAccount, loginUser);
		historyRepository.save(newHistory);
		return data.loginName();
	}
	
	AccountSQL createNewAccount(String loginName, String displayedName, String password, String role) {
		AccountSQL newAccount = new AccountSQL();
		newAccount.setLoginUser(loginName);
		newAccount.setDisplayedUser(displayedName);
		newAccount.setPassword(passwordEncoder.encode(password));
		newAccount.setRole(role);
		return newAccount;
	}
	
	AccountHistorySQL createNewHistory(AccountSQL newAccount, String loginUser) {
		AccountHistorySQL newHistory = new AccountHistorySQL();
		newHistory.setTargetId(newAccount.getId());
		newHistory.setNewLoginUser(newAccount.getLoginUser());
		newHistory.setNewDisplayedUser(newAccount.getDisplayedUser());
		newHistory.setNewRole(newAccount.getRole());
		newHistory.setAction(HistoryEnum.CREATE.name());
		newHistory.setActionId(Integer.parseInt(loginUser));
		newHistory.setActionUser(utility.getAccountSQL(loginUser).getDisplayedUser());
		return newHistory;
	}
}