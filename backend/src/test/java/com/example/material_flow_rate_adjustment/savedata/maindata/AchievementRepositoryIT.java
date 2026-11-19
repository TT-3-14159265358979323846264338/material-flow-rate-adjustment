package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.commontest.DefaultAchievement;
import com.example.material_flow_rate_adjustment.commontest.DefaultMaterial;
import com.example.material_flow_rate_adjustment.commontest.DefaultMonthPlan;

@SpringBootTest
@ActiveProfiles("test")
class AchievementRepositoryIT {
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MonthPlanRepository planRepository;
	
	@Autowired
	private AchievementRepository achievementRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	@Transactional
	void findById() {
		int id = saveTestData().achievement.get(0).getId();
		entityManager.flush();
		entityManager.clear();
		Optional<AchievementSQL> result = achievementRepository.findWithEntityGraphById(id);
		PersistenceUnitUtil unitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
		assertThat(result)
			.isPresent()
			.get()
			.satisfies(achievement -> {
				assertThat(achievement.getUserName()).isEqualTo(DefaultAchievement.DUMMY_0.getUserName());
				assertThat(unitUtil.isLoaded(achievement, "plan")).isTrue();
				assertThat(unitUtil.isLoaded(achievement.getPlan(), "material")).isTrue();
			});
	}
	
	@Test
	@Transactional
	void findByPlan() {
		MonthPlanSQL plan = saveTestData().plan;
		List<AchievementSQL> result = achievementRepository.findByPlanAndHasDeletedFalse(plan);
		assertThat(result)
			.extracting(AchievementSQL::getUserName)
			.containsExactlyInAnyOrder(
					DefaultAchievement.DUMMY_0.getUserName(),
					DefaultAchievement.DUMMY_1.getUserName(),
					DefaultAchievement.DUMMY_2.getUserName(),
					DefaultAchievement.DUMMY_3.getUserName(),
					DefaultAchievement.DUMMY_4.getUserName(),
					DefaultAchievement.DUMMY_5.getUserName());
	}
	
	@Test
	@Transactional
	void findByPlanAndInspection() {
		MonthPlanSQL plan = saveTestData().plan;
		List<AchievementSQL> result = achievementRepository.findByPlanAndInspectionAndHasDeletedFalse(plan, Inspection.UNINSPECTION);
		assertThat(result)
			.extracting(AchievementSQL::getUserName)
			.containsExactlyInAnyOrder(
					DefaultAchievement.DUMMY_4.getUserName(),
					DefaultAchievement.DUMMY_5.getUserName());
	}
	
	@Test
	@Transactional
	void existsByLotAndDateBetween() {
		saveTestData();
		boolean findResult = achievementRepository.existsByLotAndDateBetweenAndHasDeletedFalse(
															DefaultAchievement.DUMMY_0.getLot(), 
															DefaultAchievement.DUMMY_0.getDate(),
															DefaultAchievement.DUMMY_0.getDate());
		boolean notFindLot = achievementRepository.existsByLotAndDateBetweenAndHasDeletedFalse(
															"dummy", 
															DefaultAchievement.DUMMY_0.getDate(),
															DefaultAchievement.DUMMY_8.getDate());
		boolean notFindRange = achievementRepository.existsByLotAndDateBetweenAndHasDeletedFalse(
															DefaultAchievement.DUMMY_0.getLot(), 
															DefaultAchievement.DUMMY_3.getDate(),
															DefaultAchievement.DUMMY_8.getDate());
		boolean notFindDeleted = achievementRepository.existsByLotAndDateBetweenAndHasDeletedFalse(
															DefaultAchievement.DUMMY_7.getLot(), 
															DefaultAchievement.DUMMY_0.getDate(),
															DefaultAchievement.DUMMY_8.getDate());
		assertThat(findResult).isTrue();
		assertThat(notFindLot).isFalse();
		assertThat(notFindRange).isFalse();
		assertThat(notFindDeleted).isFalse();
	}
	
	record SavedData(List<AchievementSQL> achievement, MonthPlanSQL plan) {}
	
	SavedData saveTestData() {
		MaterialSQL material = materialRepository.save(DefaultMaterial.DUMMY_0.getMaterial());
		MonthPlanSQL plan = planRepository.save(DefaultMonthPlan.DUMMY_0.getPlan(material));
		List<AchievementSQL> achievement = achievementRepository.saveAll(DefaultAchievement.getAchievementList(plan));
		return new SavedData(achievement, plan);
	}
}