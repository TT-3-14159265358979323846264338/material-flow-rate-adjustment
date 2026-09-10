package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
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
		MonthPlanSQL newPlan = new MonthPlanSQL();
		newPlan.setMaterial(material);
		newPlan.setYear(year);
		newPlan.setMonth(month);
		newPlan.setPlanDate(LocalDate.of(year, month, 1));
		newPlan.setFlow(flow);
		newPlan.setAchievement(0);
		newPlan.setShipping(0);
		newPlan.setAdjustment(0);
		newPlan.setRemaining(0);
		newPlan.setHasDeleted(false);
		return newPlan;
	}
	
	PlanHistorySQL createNewHistory(MonthPlanSQL newMonthPlan, String loginUser) {
		PlanHistorySQL newHistory = new PlanHistorySQL();
		newHistory.setTargetId(newMonthPlan.getId());
		newHistory.setNewMaterialId(newMonthPlan.getMaterial().getId());
		newHistory.setNewName(newMonthPlan.getMaterial().getName());
		newHistory.setNewDestination(newMonthPlan.getMaterial().getDestination());
		newHistory.setNewYear(newMonthPlan.getYear());
		newHistory.setNewMonth(newMonthPlan.getMonth());
		newHistory.setNewFlow(newMonthPlan.getFlow());
		newHistory.setNewAchievement(newMonthPlan.getAchievement());
		newHistory.setNewShipping(newMonthPlan.getShipping());
		newHistory.setNewAdjustment(newMonthPlan.getAdjustment());
		newHistory.setNewRemaining(newMonthPlan.getRemaining());
		newHistory.setAction(HistoryEnum.CREATE.name());
		newHistory.setActionId(Integer.parseInt(loginUser));
		newHistory.setActionUser(utility.getAccountSQL(loginUser).getDisplayedUser());
		newHistory.setHasDeletedNew(false);
		return newHistory;
	}
}