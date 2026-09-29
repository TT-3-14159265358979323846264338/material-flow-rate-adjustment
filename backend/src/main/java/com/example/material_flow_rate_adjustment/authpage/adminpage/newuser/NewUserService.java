package com.example.material_flow_rate_adjustment.authpage.adminpage.newuser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		//初期パスワードはユーザーにしている。最終的にはランダム生成にする。
		AccountSQL newAccount = createNewAccount(data.loginName(), data.displayedName(), data.loginName(), data.role());
		repository.save(newAccount);
		AccountHistorySQL newHistory = createNewHistory(newAccount, loginUser);
		historyRepository.save(newHistory);
		return data.loginName();
	}
	
	AccountSQL createNewAccount(String loginName, String displayedName, String password, AccountRole role) {
		return AccountSQL.builder()
				.loginUser(loginName)
				.displayedUser(displayedName)
				.password(passwordEncoder.encode(password))
				.role(role)
				.hasDeleted(false)
				.build();
	}
	
	AccountHistorySQL createNewHistory(AccountSQL newAccount, String loginUser) {
		return AccountHistorySQL.builder()
				.targetId(newAccount.getId())
				.newLoginUser(newAccount.getLoginUser())
				.newDisplayedUser(newAccount.getDisplayedUser())
				.newRole(newAccount.getRole().name())
				.action(HistoryEnum.CREATE.name())
				.actionId(Integer.parseInt(loginUser))
				.actionUser(utility.getAccountSQL(loginUser).getDisplayedUser())
				.hasDeletedNew(false)
				.build();
	}
}