package com.example.material_flow_rate_adjustment.authpage.managerpage.correctplan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseMaterialHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectPlanService {
	private final MonthPlanRepository monthPlanRepository;
	private final MaterialRepository materialRepository;
	private final PlanHistoryRepository historyRepository;
	private final UtilityService utility;
	
	@Transactional
	public String correctPlan(int id, CorrectPlanRecord data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		MonthPlanSQL plan = utility.getMonthPlanSQL(id);
		PlanHistorySQL newHistory = createHistorySQL(plan);
		if(data.isDeleted()) {
			delete(account, plan, newHistory);
			return "計画情報を削除しました";
		}
		setMaterial(plan, newHistory, data.materialId());
		setYear(plan, newHistory, data.year());
		setMonth(plan, newHistory, data.month());
		utility.notNullMethod(newHistory, () -> utility.validPlan(plan.getMaterial().getId(), plan.getYear(), plan.getMonth()));
		setFlow(plan, newHistory, data.flow());
		saveData(account, plan, newHistory);
		return "計画情報を修正しました";
	}
	
	PlanHistorySQL createHistorySQL(MonthPlanSQL plan) {
		return PlanHistorySQL.builder()
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.oldMaterialId(plan.getMaterial().getId())
						.oldName(plan.getMaterial().getName())
						.oldDestination(plan.getMaterial().getDestination())
						.build())
				.oldYear(plan.getYear())
				.oldMonth(plan.getMonth())
				.oldFlow(plan.getFlow())
				.oldAchievement(plan.getAchievement())
				.oldShipping(plan.getShipping())
				.oldAdjustment(plan.getAdjustment())
				.oldRemaining(plan.getRemaining())
				.hasDeletedOld(plan.getHasDeleted())
				.build();
	}
	
	void setMaterial(MonthPlanSQL plan, PlanHistorySQL newHistory, Integer materialId) {
		Optional<MaterialSQL> materialOptional = materialRepository.findById(materialId);
		if(!materialOptional.isPresent()) {
			return;
		}
		MaterialSQL material = materialOptional.get();
		if(material.equals(plan.getMaterial())) {
			return;
		}
		utility.setTargetId(plan, newHistory);
		newHistory.setNewMaterialId(material.getId());
		newHistory.setNewName(material.getName());
		newHistory.setNewDestination(material.getDestination());
		plan.setMaterial(material);
	}
	
	void setYear(MonthPlanSQL plan, PlanHistorySQL newHistory, Integer year) {
		if(utility.isNotChanged(plan.getYear(), year)) {
			return;
		}
		utility.setTargetId(plan, newHistory);
		newHistory.setNewYear(year);
		plan.setYear(year);
		plan.setPlanDate(LocalDate.of(year, plan.getMonth(), 1));
	}
	
	void setMonth(MonthPlanSQL plan, PlanHistorySQL newHistory, Integer month) {
		if(utility.isNotChanged(plan.getMonth(), month)) {
			return;
		}
		utility.setTargetId(plan, newHistory);
		newHistory.setNewMonth(month);
		plan.setMonth(month);
		plan.setPlanDate(LocalDate.of(plan.getYear(), month, 1));
	}
	
	void setFlow(MonthPlanSQL plan, PlanHistorySQL newHistory, BigDecimal flow) {
		if(utility.isNotChanged(plan.getFlow(), flow)) {
			return;
		}
		utility.setTargetId(plan, newHistory);
		newHistory.setNewFlow(flow);
		plan.setFlow(flow);
	}
	
	void saveData(AccountSQL account, MonthPlanSQL plan, PlanHistorySQL newHistory) {
		utility.changeData(account, plan, monthPlanRepository, newHistory, historyRepository);
	}
	
	void delete(AccountSQL account, MonthPlanSQL plan, PlanHistorySQL newHistory) {
		utility.deleteData(account, plan, monthPlanRepository, newHistory, historyRepository);
	}
}