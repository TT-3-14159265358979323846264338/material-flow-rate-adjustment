package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.material_flow_rate_adjustment.errorhandling.DataBaseException;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.errorhandling.SentenceException;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.BaseSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilityService {
	private static final int LOT_RANGE = 6;
	private final AccountRepository accountRepository;
	private final MaterialRepository materialRepository;
	private final MonthPlanRepository monthPlanRepository;
	private final AchievementRepository achievementRepository;
	
	public AccountSQL getAccountSQL(String loginUser) {
		return getAccountSQL(Integer.parseInt(loginUser));
	}
	
	public AccountSQL getAccountSQL(int userId) {
		return accountRepository.findById(userId).orElseThrow(() -> new NotFindException("ユーザーが見つかりません"));
	}
	
	public void validLoginUser(String user) {
		if(accountRepository.existsByLoginUserAndHasDeletedFalse(user)) {
			throw new DataBaseException("同名のログインユーザーは登録できません");
		}
	}
	
	public void validDisplayedUser(String user) {
		if(accountRepository.existsByDisplayedUserAndHasDeletedFalse(user)) {
			throw new DataBaseException("同名の表示ユーザーは登録できません");
		}
	}
	
	public MaterialSQL getMaterialSQL(int id) {
		return materialRepository.findById(id).orElseThrow(() -> new NotFindException("製品が見つかりません"));
	}
	
	public void validMaterial(String name, String destination) {
		if(materialRepository.existsByNameAndDestinationAndHasDeletedFalse(name, destination)) {
			throw new DataBaseException("同製品は登録できません");
		}
	}
	
	public MonthPlanSQL getMonthPlanSQL(int id) {
		return monthPlanRepository.findWithEntityGraphById(id).orElseThrow(() -> new NotFindException("計画が見つかりません"));
	}
	
	public void validPlan(int id, int year, int month) {
		if(monthPlanRepository.existsByMaterial_IdAndYearAndMonthAndHasDeletedFalse(id, year, month)) {
			throw new DataBaseException("同計画は登録できません");
		}
	}
	
	public AchievementSQL getAchievementSQL(int id) {
		return achievementRepository.findWithEntityGraphById(id).orElseThrow(() -> new NotFindException("実績が見つかりません"));
	}
	
	public void validAchievement(String lot, LocalDate date) {
		if(achievementRepository.existsByLotAndDateBetweenAndHasDeletedFalse(lot, date.minusMonths(LOT_RANGE), date.plusMonths(LOT_RANGE))) {
			throw new DataBaseException("1年以内の同Lotは登録できません");
		}
	}
	
	public boolean isNotChanged(String base, String target) {
		return !StringUtils.hasLength(target) || base.equals(target);
	}
	
	public <T> boolean isNotChanged(T base, T target) {
		return target == null || base.equals(target);
	}
	
	public <T extends BaseSQL, U extends BaseHistorySQL> void setTargetId(T dataSQL, U newHistory) {
		newHistory.setTargetId(dataSQL.getId());
	}
	
	public <T extends BaseHistorySQL> void notNullMethod(T newHistory, Runnable method){
		if(newHistory.getTargetId() != null) {
			method.run();
		}
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
		setTargetId(dataSQL, newHistory);
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