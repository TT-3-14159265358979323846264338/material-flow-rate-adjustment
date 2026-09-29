package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseMaterialHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewPlanService {
	private final MonthPlanRepository monthPlanRepository;
	private final PlanHistoryRepository historyRepository;
	private final UtilityService utility;
	
	@Transactional
	public String createNewPlan(NewPlan newPlan, String loginUser){
		MonthPlanSQL newMonthPlan = createPlan(utility.getMaterialSQL(newPlan.materialId()), newPlan.year(), newPlan.month(), newPlan.flow());
		monthPlanRepository.save(newMonthPlan);
		PlanHistorySQL newHistory = createNewHistory(newMonthPlan, loginUser);
		historyRepository.save(newHistory);
		return "新規計画を登録しました。";
	}
	
	MonthPlanSQL createPlan(MaterialSQL material, int year, int month, int flow) {
		return MonthPlanSQL.builder()
				.material(material)
				.year(year)
				.month(month)
				.planDate(LocalDate.of(year, month, 1))
				.flow(flow)
				.achievement(0)
				.shipping(0)
				.adjustment(0)
				.remaining(0)
				.hasDeleted(false)
				.build();
	}
	
	PlanHistorySQL createNewHistory(MonthPlanSQL newMonthPlan, String loginUser) {
		return PlanHistorySQL.builder()
				.targetId(newMonthPlan.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.newMaterialId(newMonthPlan.getMaterial().getId())
						.newName(newMonthPlan.getMaterial().getName())
						.newDestination(newMonthPlan.getMaterial().getDestination())
						.build())
				.newYear(newMonthPlan.getYear())
				.newMonth(newMonthPlan.getMonth())
				.newFlow(newMonthPlan.getFlow())
				.newAchievement(newMonthPlan.getAchievement())
				.newShipping(newMonthPlan.getShipping())
				.newAdjustment(newMonthPlan.getAdjustment())
				.newRemaining(newMonthPlan.getRemaining())
				.action(HistoryEnum.CREATE.name())
				.actionId(Integer.parseInt(loginUser))
				.actionUser(utility.getAccountSQL(loginUser).getDisplayedUser())
				.hasDeletedNew(false)
				.build();
	}
}