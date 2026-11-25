package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;

import org.assertj.core.api.ThrowingConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.material_flow_rate_adjustment.commontest.BaseSQLIT;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;

@SpringBootTest
@ActiveProfiles("test")
class MaterialSQLIT extends BaseSQLIT<MaterialRepository, MaterialSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	protected MaterialRepository repository() {
		return materialRepository;
	}

	@Override
	protected MaterialSQL entity() {
		return DefaultMaterial.DUMMY_1.getSQL();
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

	@Override
	protected ThrowingConsumer<MaterialSQL> assertLoad() {
		return material -> {
			assertThat(material.getName()).isEqualTo(DefaultMaterial.DUMMY_1.getName());
			assertThat(material.getDestination()).isEqualTo(DefaultMaterial.DUMMY_1.getDestination());
			assertThat(material.getBase()).isEqualByComparingTo(DefaultMaterial.DUMMY_1.getBase());
			assertThat(material.getUnit()).isEqualTo(DefaultMaterial.DUMMY_1.getUnit());
		};
	}

	@Override
	protected Consumer<MaterialSQL> changeMethod() {
		return material -> material.setName("dummy");
	}

	@Override
	protected List<String> grantLengthVariableName() {
		return List.of(
				"name",
				"destination",
				"unit");
	}

	@Override
	protected List<String> grantUniqueVariableName() {
		return null;
	}

	@Override
	protected List<String> grantNotNullVariableName() {
		return List.of(
				"name",
				"destination",
				"unit");
	}

	@Override
	protected List<String> grantBigDecimalParams() {
		return List.of(
				"base");
	}

	@Override
	protected List<String> grantLazyFetch() {
		return null;
	}

	@Override
	protected List<DeleteData<?, ?>> grantOnDelete() {
		return null;
	}
}