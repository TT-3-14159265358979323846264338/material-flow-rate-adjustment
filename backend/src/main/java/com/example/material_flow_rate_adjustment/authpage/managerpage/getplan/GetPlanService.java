package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.TransformService;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPlanService {
	private final MonthPlanRepository planRepository;
	private final TransformService transform;
	
	@Transactional(readOnly = true)
	public List<Plan> getPlan(GetPlanRecord getPlanRecord) {
		Predicate<MonthPlanSQL> filter = filter(getPlanRecord.material());
		return planRepository.findByPlanDateBetweenHasDeletedFalse(
								transform.minDate(getPlanRecord.minYear(), getPlanRecord.minMonth()), 
								transform.maxDate(getPlanRecord.maxYear(), getPlanRecord.maxMonth()),
								getPlanRecord.target().getPlanSort(getPlanRecord.order()))
							.stream()
							.filter(filter)
							.map(this::createPlan)
							.toList();
	}
	
	Predicate<MonthPlanSQL> filter(String material){
		int id = transform.intValue(material, -1);
		return id != -1? 
					(plan) -> plan.getMaterial().getId() == id: 
					(_) -> true;
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