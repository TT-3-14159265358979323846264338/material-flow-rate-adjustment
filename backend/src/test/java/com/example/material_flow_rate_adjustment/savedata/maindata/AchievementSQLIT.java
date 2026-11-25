package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;

import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.commontest.BaseSQLIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultAchievement;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultPlan;

@SpringBootTest
@ActiveProfiles("test")
class AchievementSQLIT extends BaseSQLIT<AchievementRepository, AchievementSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MonthPlanRepository planRepository;
	
	@Autowired
	private AchievementRepository achievementRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private MonthPlanSQL plan;
	
	@BeforeEach
	@Transactional
	void setUp() {
		MaterialSQL material = materialRepository.save(DefaultMaterial.DUMMY_0.getSQL());
		plan = planRepository.save(DefaultPlan.DUMMY_0.getSQL(material));
	}

	@Override
	protected AchievementRepository repository() {
		return achievementRepository;
	}

	@Override
	protected AchievementSQL entity() {
		return DefaultAchievement.DUMMY_0.getSQL(plan);
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
	
	@Override
	protected ThrowingConsumer<AchievementSQL> assertLoad(){
		return achievement -> {
			assertThat(achievement.getUserName()).isEqualTo(DefaultAchievement.DUMMY_0.getUserName());
			assertThat(achievement.getPlan().getId()).isEqualTo(plan.getId());
			assertThat(achievement.getDate()).isEqualTo(DefaultAchievement.DUMMY_0.getDate());
			assertThat(achievement.getLot()).isEqualTo(DefaultAchievement.DUMMY_0.getLot());
			assertThat(achievement.getFlow()).isEqualByComparingTo(DefaultAchievement.DUMMY_0.getFlow());
			assertThat(achievement.getInspection()).isEqualTo(DefaultAchievement.DUMMY_0.getInspection());
		};
	}

	@Override
	protected Consumer<AchievementSQL> changeMethod() {
		return achievement -> achievement.setLot("change");
	}

	@Override
	protected List<String> grantLengthVariableName() {
		return List.of(
				"userName",
				"lot");
	}

	@Override
	protected List<String> grantUniqueVariableName() {
		return null;
	}

	@Override
	protected List<String> grantNotNullVariableName() {
		return List.of(
				"userName",
				"plan",
				"date",
				"lot",
				"flow",
				"inspection");
	}
	
	@Override
	protected List<String> grantBigDecimalParams(){
		return List.of(
				"flow");
	}

	@Override
	protected List<String> grantLazyFetch() {
		return List.of(
				"plan");
	}

	@Override
	protected List<DeleteData<?, ?>> grantOnDelete() {
		return List.of(
				new DeleteData<>(planRepository, plan));
	}
}