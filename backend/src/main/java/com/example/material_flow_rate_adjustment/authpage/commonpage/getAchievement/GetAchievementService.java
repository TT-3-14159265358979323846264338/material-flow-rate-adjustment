package com.example.material_flow_rate_adjustment.authpage.commonpage.getAchievement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.Inspection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAchievementService {
	private final AchievementRepository achievementRepository;
	
	@Transactional(readOnly = true)
	public TotalAchievement getAchievement(int id){
		List<Achievement> allAchievement = achievementRepository.findByPlan_IdAndHasDeletedFalse(id)
				.stream()
				.map(this::createAchievement)
				.toList();
		return createTotalAchievement(allAchievement);
	}
	
	record Achievement(
			Integer id,
			String name,
			LocalDate date,
			String lot,
			BigDecimal flow,
			Inspection inspection) {}
	
	Achievement createAchievement(AchievementSQL achievement) {
		return new Achievement(
				achievement.getId(),
				achievement.getUserName(),
				achievement.getDate(),
				achievement.getLot(),
				achievement.getFlow(),
				achievement.getInspection());
	}
	
	record TotalAchievement(
			BigDecimal passed,
			BigDecimal uninspection,
			List<Achievement> allAchievement) {}
	
	TotalAchievement createTotalAchievement(List<Achievement> allAchievement) {
		return new TotalAchievement(
				getConfirmed(allAchievement),
				getUninspected(allAchievement),
				allAchievement);
	}
	
	BigDecimal getConfirmed(List<Achievement> allAchievement) {
		return getTotal(allAchievement, Inspection.PASSED);
	}
	
	BigDecimal getUninspected(List<Achievement> allAchievement) {
		return getTotal(allAchievement, Inspection.UNINSPECTION);
	}
	
	BigDecimal getTotal(List<Achievement> allAchievement, Inspection targetInspection) {
		return allAchievement.stream().filter(i -> i.inspection.equals(targetInspection))
										.map(Achievement::flow)
										.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}