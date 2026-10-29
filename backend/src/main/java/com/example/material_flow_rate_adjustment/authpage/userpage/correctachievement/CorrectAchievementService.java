package com.example.material_flow_rate_adjustment.authpage.userpage.correctachievement;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseMaterialHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.BasePlanHistory;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.Inspection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectAchievementService {
	private final AchievementRepository achievementRepository;
	private final AchievementHistoryRepository historyRepository;
	private final UtilityService utility;
	
	@Transactional
	public String correctAchievement(int id, CorrectAchievementRecord data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		AchievementSQL achievement = utility.getAchievementSQL(id);
		AchievementHistorySQL newHistory = createHistorySQL(achievement);
		if(data.isDeleted()) {
			delete(account, achievement, newHistory);
			return "実績を取り消しました";
		}
		setDate(achievement, newHistory, data.date());
		setLot(achievement, newHistory, data.lot());
		utility.notNullMethod(newHistory, () -> utility.validAchievement(achievement.getLot(), achievement.getDate()));
		setFlow(achievement, newHistory, data.flow());
		setInspection(achievement, newHistory, data.inspection());
		saveData(account, achievement, newHistory);
		return "実績を修正しました";
	}
	
	AchievementHistorySQL createHistorySQL(AchievementSQL achievement) {
		return AchievementHistorySQL.builder()
				.oldUserName(achievement.getUserName())
				.basePlanHistory(BasePlanHistory.builder()
									.oldPlanId(achievement.getPlan().getId())
									.oldYear(achievement.getPlan().getYear())
									.oldMonth(achievement.getPlan().getMonth())
									.build())
				.baseMaterialHistory(BaseMaterialHistory.builder()
										.oldMaterialId(achievement.getPlan().getMaterial().getId())
										.oldName(achievement.getPlan().getMaterial().getName())
										.oldDestination(achievement.getPlan().getMaterial().getDestination())
										.build())
				.oldDate(achievement.getDate())
				.oldLot(achievement.getLot())
				.oldFlow(achievement.getFlow())
				.oldInspection(achievement.getInspection().name())
				.hasDeletedOld(achievement.getHasDeleted())
				.build();
	}
	
	void setDate(AchievementSQL achievement, AchievementHistorySQL newHistory, LocalDate date) {
		if(utility.isNotChanged(achievement.getDate(), date)) {
			return;
		}
		utility.setTargetId(achievement, newHistory);
		newHistory.setNewDate(date);
		achievement.setDate(date);
	}
	
	void setLot(AchievementSQL achievement, AchievementHistorySQL newHistory, String lot) {
		if(utility.isNotChanged(achievement.getLot(), lot)) {
			return;
		}
		utility.setTargetId(achievement, newHistory);
		newHistory.setNewLot(lot);
		achievement.setLot(lot);
	}
	
	void setFlow(AchievementSQL achievement, AchievementHistorySQL newHistory, BigDecimal flow) {
		if(utility.isNotChanged(achievement.getFlow(), flow)) {
			return;
		}
		utility.setTargetId(achievement, newHistory);
		newHistory.setNewFlow(flow);
		achievement.setFlow(flow);
	}
	
	void setInspection(AchievementSQL achievement, AchievementHistorySQL newHistory, Inspection inspection) {
		if(utility.isNotChanged(achievement.getInspection(), inspection)) {
			return;
		}
		utility.setTargetId(achievement, newHistory);
		newHistory.setNewInspection(inspection.name());
		achievement.setInspection(inspection);
	}
	
	void saveData(AccountSQL account, AchievementSQL achievement, AchievementHistorySQL newHistory) {
		utility.changeData(account, achievement, achievementRepository, newHistory, historyRepository);
	}
	
	void delete(AccountSQL account, AchievementSQL achievement, AchievementHistorySQL newHistory) {
		utility.deleteData(account, achievement, achievementRepository, newHistory, historyRepository);
	}
}