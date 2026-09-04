package com.example.material_flow_rate_adjustment.authpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.errorhandling.SentenceException;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.BaseSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilityService {
	private final AccountRepository accountRepository;
	private final MaterialRepository materialRepository;
	
	public int getIntValue(String value, int base) {
		try {
			return Integer.parseInt(value);
		}catch(Exception e) {
			return base;
		}
	}
	
	public AccountSQL getAccountSQL(String loginUser) {
		return getAccountSQL(Integer.parseInt(loginUser));
	}
	
	public AccountSQL getAccountSQL(int userId) {
		AccountSQL account = accountRepository.findById(userId)
			.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
		return account;
	}
	
	public MaterialSQL getMaterialSQL(int id) {
		return materialRepository.findById(id).orElseThrow(() -> new NotFindException("製品が見つかりません。"));
	}
	
	public <T extends BaseSQL, U extends BaseHistorySQL> 
	void changeData(AccountSQL loginAccount, T dataSQL, JpaRepository<T, Integer> dataRepository, U newHistory, JpaRepository<U, Integer> historyRepository) {
		if(newHistory.getTargetId() == null) {
			throw new SentenceException("データの変更がありませんでした");
		}
		dataRepository.save(dataSQL);
		saveHistory(loginAccount, newHistory, historyRepository, HistoryEnum.CHANGE);
	}
	
	public <T extends BaseSQL, U extends BaseHistorySQL> 
	void deleteData(AccountSQL loginAccount, T dataSQL, JpaRepository<T, Integer> dataRepository, U newHistory, JpaRepository<U, Integer> historyRepository) {
		dataSQL.setHasDeleted(true);
		newHistory.setTargetId(dataSQL.getId());
		newHistory.setHasDeletedNew(true);
		dataRepository.save(dataSQL);
		saveHistory(loginAccount, newHistory, historyRepository, HistoryEnum.DELETE);
	}
	
	<T extends BaseHistorySQL, U extends JpaRepository<T, Integer>> void saveHistory(AccountSQL loginAccount, T newHistory, U historyRepository, HistoryEnum code) {
		newHistory.setAction(code.name());
		newHistory.setActionId(loginAccount.getId());
		newHistory.setActionUser(loginAccount.getDisplayedUser());
		historyRepository.save(newHistory);
	}
}