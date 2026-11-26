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

import com.example.material_flow_rate_adjustment.commontest.BaseMainSQLIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultPlan;

@SpringBootTest
@ActiveProfiles("test")
class MonthPlanSQLIT extends BaseMainSQLIT<MonthPlanRepository, MonthPlanSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MonthPlanRepository planRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private MaterialSQL material;
	
	@BeforeEach
	@Transactional
	void setUp() {
		material = materialRepository.save(DefaultMaterial.DUMMY_0.getSQL());
	}

	@Override
	protected MonthPlanRepository repository() {
		return planRepository;
	}

	@Override
	protected MonthPlanSQL entity() {
		return DefaultPlan.DUMMY_0.getSQL(material);
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

	@Override
	protected ThrowingConsumer<MonthPlanSQL> assertLoad() {
		return plan -> {
			assertThat(plan.getMaterial().getId()).isEqualTo(material.getId());
			assertThat(plan.getYear()).isEqualTo(DefaultPlan.DUMMY_0.getYear());
			assertThat(plan.getMonth()).isEqualTo(DefaultPlan.DUMMY_0.getMonth());
			assertThat(plan.getPlanDate()).isEqualTo(DefaultPlan.DUMMY_0.getDate());
			assertThat(plan.getFlow()).isEqualByComparingTo(DefaultPlan.DUMMY_0.getFlow());
			assertThat(plan.getAchievement()).isEqualByComparingTo(DefaultPlan.DUMMY_0.getAchievement());
			assertThat(plan.getShipping()).isEqualByComparingTo(DefaultPlan.DUMMY_0.getShipping());
			assertThat(plan.getAdjustment()).isEqualByComparingTo(DefaultPlan.DUMMY_0.getAdjustment());
			assertThat(plan.getRemaining()).isEqualByComparingTo(DefaultPlan.DUMMY_0.getRemaining());
		};
	}

	@Override
	protected Consumer<MonthPlanSQL> changeMethod() {
		return plan -> plan.setYear(2000);
	}

	@Override
	protected List<String> grantLengthVariableName() {
		return null;
	}

	@Override
	protected List<String> grantUniqueVariableName() {
		return null;
	}

	@Override
	protected List<String> grantNotNullVariableName() {
		return List.of(
				"material",
				"year",
				"month",
				"planDate",
				"flow",
				"achievement",
				"shipping",
				"adjustment",
				"remaining");
	}

	@Override
	protected List<String> grantBigDecimalParams() {
		return  List.of(
				"flow",
				"achievement",
				"shipping",
				"adjustment",
				"remaining");
	}

	@Override
	protected List<String> grantLazyFetch() {
		return List.of(
				"material");
	}

	@Override
	protected List<DeleteData<?, ?>> grantOnDelete() {
		return List.of(
				new DeleteData<>(materialRepository, material));
	}
}