package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.TransformService;
import com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial.GetMaterialService;
import com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial.GetMaterialService.Material;
import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPlanService {
	private final MonthPlanRepository planRepository;
	private final GetMaterialService getMaterialService;
	private final TransformService transform;
	
	@Transactional(readOnly = true)
	public List<Plan> getSortPlan(GetPlanRecord getPlanRecord) {
		return planRepository.findByPlanDateBetweenAndHasDeletedFalse(
								transform.minDate(getPlanRecord.minYear(), getPlanRecord.minMonth()), 
								transform.maxDate(getPlanRecord.maxYear(), getPlanRecord.maxMonth()),
								getPlanRecord.target().getPlanSort(getPlanRecord.order()))
							.stream()
							.filter(filter(getPlanRecord.material()))
							.map(this::createPlan)
							.toList();
	}
	
	@Transactional(readOnly = true)
	public Plan getDesignatedPlan(int id, GetDesignatedRecord getDesignatedRecord) {
		return planRepository.findByMaterial_IdAndYearAndMonthAndHasDeletedFalse(id, getDesignatedRecord.year(), getDesignatedRecord.month())
							.map(this::createPlan)
							.orElseThrow(() -> new NotFindException("指定の計画が存在しません"));
	}
	
	Predicate<MonthPlanSQL> filter(Integer material){
		return material != null? 
					(plan) -> plan.getMaterial().getId() == material: 
					(_) -> true;
	}
	
	Plan createPlan(MonthPlanSQL monthPlan) {
		return new Plan(monthPlan.getId(),
				getMaterialService.createMaterial(monthPlan.getMaterial()),
				monthPlan.getYear(),
				monthPlan.getMonth(),
				monthPlan.getFlow(),
				monthPlan.getAchievement(),
				monthPlan.getShipping(),
				monthPlan.getAdjustment(),
				monthPlan.getRemaining()
				);
	}
	
	record Plan(Integer id,
			Material material,
			Integer year,
			Integer month,
			Integer flow,
			Integer achievement,
			Integer shipping,
			Integer adjustment,
			Integer remaining
			) {}
}