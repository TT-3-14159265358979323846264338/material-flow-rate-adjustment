package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.commontest.BaseRepositoryIT;
import com.example.material_flow_rate_adjustment.commontest.DefaultMaterial;
import com.example.material_flow_rate_adjustment.commontest.DefaultPlan;

@SpringBootTest
@ActiveProfiles("test")
class MonthPlanRepositoryIT extends BaseRepositoryIT<MonthPlanRepository, MonthPlanSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MonthPlanRepository planRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private MaterialSQL material;
	
	@Test
	@Transactional
	void findById() {
		int id = saveTestData().get(0).getId();
		Optional<MonthPlanSQL> result = planRepository.findWithEntityGraphById(id);
		assertLoad(result);
	}
	
	@Test
	@Transactional
	void findByPlanDateBetween() {
		saveTestData();
		List<MonthPlanSQL> result = planRepository.findWithEntityGraphByPlanDateBetweenAndHasDeletedFalse(
														DefaultPlan.DUMMY_0.getDate(),
														DefaultPlan.DUMMY_3.getDate(),
														Sort.by(OrderSortEnum.DESCENDING.getOrderSort(), "flow"));
		assertThat(result).first().satisfies(assertLoad());
		assertThat(result)
			.extracting(MonthPlanSQL::getFlow)
			.usingElementComparator(BigDecimal::compareTo)
			.containsExactly(
					DefaultPlan.DUMMY_1.getFlow(),
					DefaultPlan.DUMMY_0.getFlow());
	}
	
	@Test
	@Transactional
	void findByMaterial_IdAndYearAndMonth() {
		saveTestData();
		Optional<MonthPlanSQL> findResult = planRepository.findWithEntityGraphByMaterial_IdAndYearAndMonthAndHasDeletedFalse(
															material.getId(), 
															DefaultPlan.DUMMY_0.getYear(),
															DefaultPlan.DUMMY_0.getMonth());
		Optional<MonthPlanSQL> notFindData = planRepository.findWithEntityGraphByMaterial_IdAndYearAndMonthAndHasDeletedFalse(
																material.getId(),
																DefaultPlan.DUMMY_1.getYear(),
																DefaultPlan.DUMMY_2.getMonth());
		Optional<MonthPlanSQL> notFindDeleted = planRepository.findWithEntityGraphByMaterial_IdAndYearAndMonthAndHasDeletedFalse(
																material.getId(),
																DefaultPlan.DUMMY_3.getYear(),
																DefaultPlan.DUMMY_3.getMonth());
		assertLoad(findResult)
			.extracting(MonthPlanSQL::getFlow)
			.usingComparator(BigDecimal::compareTo)
			.isEqualTo(DefaultPlan.DUMMY_0.getFlow());
		assertThat(notFindData);
		assertThat(notFindDeleted).isNotPresent();
	}
	
	@Test
	@Transactional
	void existsByMaterial_IdAndYearAndMonth() {
		saveTestData();
		boolean findResult = planRepository.existsByMaterial_IdAndYearAndMonthAndHasDeletedFalse(
												material.getId(), 
												DefaultPlan.DUMMY_0.getYear(), 
												DefaultPlan.DUMMY_0.getMonth());
		boolean notFindData = planRepository.existsByMaterial_IdAndYearAndMonthAndHasDeletedFalse(
												material.getId(), 
												DefaultPlan.DUMMY_1.getYear(), 
												DefaultPlan.DUMMY_2.getMonth());
		boolean notFindDeleted = planRepository.existsByMaterial_IdAndYearAndMonthAndHasDeletedFalse(
													material.getId(), 
													DefaultPlan.DUMMY_3.getYear(), 
													DefaultPlan.DUMMY_3.getMonth());
		assertThat(findResult).isTrue();
		assertThat(notFindData).isFalse();
		assertThat(notFindDeleted).isFalse();
	}
	
	AbstractObjectAssert<?, MonthPlanSQL> assertLoad(Optional<MonthPlanSQL> result) {
		return assertThat(result)
			.isPresent()
			.get()
			.satisfies(assertLoad());
	}
	
	ThrowingConsumer<MonthPlanSQL> assertLoad(){
		return plan -> assertThat(unitUtil().isLoaded(plan, "material")).isTrue();
	}

	@Override
	protected MonthPlanRepository repository() {
		material = materialRepository.save(DefaultMaterial.DUMMY_0.getMaterial());
		return planRepository;
	}

	@Override
	protected List<MonthPlanSQL> entity() {
		return DefaultPlan.getPlanList(material);
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
}