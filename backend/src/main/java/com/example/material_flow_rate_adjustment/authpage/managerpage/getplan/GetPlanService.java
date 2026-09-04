package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPlanService {
	private final MonthPlanRepository planRepository;
	private final UtilityService utility;
	private final int MAX = 100;
	
	@Transactional(readOnly = true)
	public List<Plan> getPlan(GetPlanRecord getPlanRecord) {
		return planRepository.findByHasDeletedFalse(PageRequest.of(0, utility.getIntValue(getPlanRecord.maxSize(), MAX), getPlanRecord.target().getPlanSort(getPlanRecord.order())))
							.stream()
							.map(this::createPlan)
							.toList();
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