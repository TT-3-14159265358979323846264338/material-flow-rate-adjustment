package com.example.material_flow_rate_adjustment.authpage.newuser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
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
	
	@Transactional
	public String createNewUser(NewUser data, String loginUser){
		if(repository.existsByUser(data.name())) {
			throw new DataBaseException("同名のユーザーは登録できません。");
		}
		//初期パスワードはユーザーにしている。
		AccountSQL newAccount = createNewAccount(data.name(), data.name(), data.role().name());
		repository.save(newAccount);
		AccountHistorySQL newHistory = createNewHistory(data, loginUser);
		historyRepository.save(newHistory);
		return data.name();
	}
	
	AccountSQL createNewAccount(String username, String password, String role) {
		AccountSQL newAccount = new AccountSQL();
		newAccount.setUser(username);
		newAccount.setPassword(passwordEncoder.encode(password));
		newAccount.setRole(role);
		return newAccount;
	}
	
	AccountHistorySQL createNewHistory(NewUser data, String loginUser) {
		AccountHistorySQL newHistory = new AccountHistorySQL();
		newHistory.setNewUser(data.name());
		newHistory.setNewRole(data.role().name());
		newHistory.setAction(HistoryEnum.CREATE.name());
		newHistory.setActionId(Integer.parseInt(loginUser));
		newHistory.setActionUser(loginUserName(loginUser));
		return newHistory;
	}
	
	String loginUserName(String loginUser) {
		AccountSQL account = repository.findById(Integer.parseInt(loginUser))
			.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
		return account.getUser();
	}
}