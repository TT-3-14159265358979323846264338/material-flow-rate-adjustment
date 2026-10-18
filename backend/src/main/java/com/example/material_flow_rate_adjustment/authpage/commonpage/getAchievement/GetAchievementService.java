package com.example.material_flow_rate_adjustment.authpage.commonpage.getAchievement;

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
	public List<Achievement> getAchievementService(int id){
		return achievementRepository.findByPlan_IdAndHasDeletedFalse(id)
									.stream()
									.map(this::createAchievement)
									.toList();
	}
	
	record Achievement(
			Integer id,
			String name,
			LocalDate date,
			String lot,
			Integer flow,
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
}