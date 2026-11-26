package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
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
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultCalendar;
import com.example.material_flow_rate_adjustment.defaulttestdata.DefaultMaterial;

@SpringBootTest
@ActiveProfiles("test")
class CalendarSQLIT extends BaseMainSQLIT<CalendarRepository, CalendarSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private MaterialSQL material;
	
	@BeforeEach
	@Transactional
	void setUp() {
		material = materialRepository.save(DefaultMaterial.DUMMY_0.getSQL());
	}

	@Override
	protected CalendarRepository repository() {
		return calendarRepository;
	}

	@Override
	protected CalendarSQL entity() {
		return DefaultCalendar.DUMMY_0.getSQL(material);
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}

	@Override
	protected ThrowingConsumer<CalendarSQL> assertLoad() {
		return calendar -> {
			assertThat(calendar.getMaterial().getId()).isEqualTo(material.getId());
			assertThat(calendar.getHoliday().getDayOfMonth()).isEqualTo(DefaultCalendar.DUMMY_0.getDay());
			assertThat(calendar.getType()).isEqualTo(DefaultCalendar.DUMMY_0.getType());
		};
	}

	@Override
	protected Consumer<CalendarSQL> changeMethod() {
		return calendar -> calendar.setHoliday(LocalDate.of(2025, 1, 1));
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
				"holiday",
				"type");
	}

	@Override
	protected List<String> grantBigDecimalParams() {
		return null;
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