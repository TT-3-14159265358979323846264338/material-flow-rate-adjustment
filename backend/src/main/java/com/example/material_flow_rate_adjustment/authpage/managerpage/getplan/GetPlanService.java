package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPlanService {
	private final MonthPlanRepository planRepository;
	
	@Transactional(readOnly = true)
	public List<Plan> getPlan(FilterRecord filterRecord) {
		int limitSize = filterRecord.getNumber() <= 0? Integer.MAX_VALUE : filterRecord.getNumber();
		return planRepository.findByHasDeletedFalse().stream().limit(limitSize).map(this::createPlan).toList();
	}
	
	Plan createPlan(MonthPlanSQL monthPlan) {
		return new Plan(monthPlan.getId(),
				monthPlan.getMaterial().getName(),
				monthPlan.getMaterial().getDestination(),
				monthPlan.getYear(),
				monthPlan.getMonth(),
				monthPlan.getFlow(),
				monthPlan.getAchievement(),
				monthPlan.getShipping(),
				monthPlan.getAdjustment(),
				monthPlan.getRemaining()
				);
	}
	
	record Plan(int id,
			String name,
			String destination,
			int year,
			int month,
			int flow,
			int achievement,
			int shipping,
			int adjustment,
			int remaining
			) {}
}