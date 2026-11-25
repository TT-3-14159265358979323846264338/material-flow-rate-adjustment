package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.commontest.BaseMainRepositoryIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.BaseSQLNoJoinColumn;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;

@SpringBootTest
@ActiveProfiles("test")
class MaterialRepositoryIT extends BaseMainRepositoryIT<MaterialRepository, MaterialSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	@Transactional
	void existsByNameAndDestination() {
		saveTestData();
		boolean findResult = materialRepository.existsByNameAndDestinationAndHasDeletedFalse(DefaultMaterial.DUMMY_0.getName(), DefaultMaterial.DUMMY_0.getDestination());
		boolean notFindData = materialRepository.existsByNameAndDestinationAndHasDeletedFalse(DefaultMaterial.DUMMY_1.getName(), DefaultMaterial.DUMMY_0.getDestination());
		boolean notFindDeleted = materialRepository.existsByNameAndDestinationAndHasDeletedFalse(DefaultMaterial.DUMMY_2.getName(), DefaultMaterial.DUMMY_2.getDestination());
		assertThat(findResult).isTrue();
		assertThat(notFindData).isFalse();
		assertThat(notFindDeleted).isFalse();
	}

	@Override
	protected MaterialRepository repository() {
		return materialRepository;
	}

	@Override
	protected List<MaterialSQL> entity() {
		return BaseSQLNoJoinColumn.getSQLList(DefaultMaterial.values());
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
}