package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewPlanService {
	private final MonthPlanRepository monthPlanRepository;
	private final UtilityService utility;
	
	@Transactional
	public String createNewPlan(NewPlan newPlan, String loginUser){
		MonthPlanSQL newMonthPlan = createPlan(utility.getMaterialSQL(newPlan.materialId()), newPlan.year(), newPlan.month(), newPlan.flow());
		monthPlanRepository.save(newMonthPlan);
		
		return "新規計画を登録しました。";
	}
	
	MonthPlanSQL createPlan(MaterialSQL material, int year, int month, int flow) {
		MonthPlanSQL newPlan = new MonthPlanSQL();
		newPlan.setMaterial(material);
		newPlan.setYear(year);
		newPlan.setMonth(month);
		newPlan.setFlow(flow);
		newPlan.setHasDeleted(false);
		return newPlan;
	}
	
	
}