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

import com.example.material_flow_rate_adjustment.commontest.BaseMainRepositoryIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.BaseSQLJoinColumn;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultAchievement;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultPlan;

@SpringBootTest
@ActiveProfiles("test")
class AchievementRepositoryIT extends BaseMainRepositoryIT<AchievementRepository, AchievementSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MonthPlanRepository planRepository;
	
	@Autowired
	private AchievementRepository achievementRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private MaterialSQL material;
	
	private MonthPlanSQL plan;
	
	@Test
	@Transactional
	void findById() {
		int id = saveTestData().get(0).getId();
		Optional<AchievementSQL> result = achievementRepository.findWithEntityGraphById(id);
		PersistenceUnitUtil unitUtil = unitUtil();
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
		saveTestData();
		saveDummyData();
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
		saveTestData();
		saveDummyData();
		List<AchievementSQL> result = achievementRepository.findByPlanAndInspectionAndHasDeletedFalse(plan, Inspection.UNINSPECTION);
		assertThat(result)
			.extracting(AchievementSQL::getUserName)
			.containsExactlyInAnyOrder(
					DefaultAchievement.DUMMY_4.getUserName(),
					DefaultAchievement.DUMMY_5.getUserName());
	}
	
	void saveDummyData() {
		MonthPlanSQL dummyPlan = planRepository.save(DefaultPlan.DUMMY_1.getSQL(material));
		achievementRepository.save(DefaultAchievement.DUMMY_0.getSQL(dummyPlan));
		clear();
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
	
	@Override
	protected List<AchievementSQL> saveTestData() {
		material = materialRepository.save(DefaultMaterial.DUMMY_0.getSQL());
		plan = planRepository.save(DefaultPlan.DUMMY_0.getSQL(material));
		return super.saveTestData();
	}

	@Override
	protected AchievementRepository repository() {
		return achievementRepository;
	}

	@Override
	protected List<AchievementSQL> entity() {
		return BaseSQLJoinColumn.getSQLList(plan, DefaultAchievement.values());
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
}