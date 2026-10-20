package com.example.material_flow_rate_adjustment.authpage.userpage.newachievement;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseMaterialHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.BasePlanHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewAchievementService {
	private final AchievementRepository achievementRepository;
	private final AchievementHistoryRepository historyRepository;
	private final UtilityService utility;
	
	@Transactional
	public String newAchievement(NewAchievementRecord data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		MonthPlanSQL plan = utility.getMonthPlanSQL(data.plan());
		AchievementSQL newAchievement = createAchievementSQL(account.getDisplayedUser(), plan, data);
		achievementRepository.save(newAchievement);
		AchievementHistorySQL newHistory = createHistory(account, newAchievement);
		historyRepository.save(newHistory);
		return "新規実績を登録しました";
	}
	
	AchievementSQL createAchievementSQL(String userName, MonthPlanSQL plan, NewAchievementRecord newAchievement) {
		return AchievementSQL.builder()
				.userName(userName)
				.plan(plan)
				.date(newAchievement.date())
				.lot(newAchievement.lot())
				.flow(newAchievement.flow())
				.inspection(newAchievement.inspection())
				.hasDeleted(false)
				.build();
	}
	
	AchievementHistorySQL createHistory(AccountSQL account, AchievementSQL newAchievement) {
		return AchievementHistorySQL.builder()
				.targetId(newAchievement.getId())
				.newUserName(newAchievement.getUserName())
				.basePlanHistory(BasePlanHistory.builder()
									.newPlanId(newAchievement.getPlan().getId())
									.newYear(newAchievement.getPlan().getYear())
									.newMonth(newAchievement.getPlan().getMonth())
									.build())
				.baseMaterialHistory(BaseMaterialHistory.builder()
										.newMaterialId(newAchievement.getPlan().getMaterial().getId())
										.newName(newAchievement.getPlan().getMaterial().getName())
										.newDestination(newAchievement.getPlan().getMaterial().getDestination())
										.build())
				.newDate(newAchievement.getDate())
				.newLot(newAchievement.getLot())
				.newFlow(newAchievement.getFlow())
				.newInspection(newAchievement.getInspection().name())
				.action(HistoryEnum.CREATE.name())
				.actionId(account.getId())
				.actionUser(account.getDisplayedUser())
				.hasDeletedNew(false)
				.build();
	}
}