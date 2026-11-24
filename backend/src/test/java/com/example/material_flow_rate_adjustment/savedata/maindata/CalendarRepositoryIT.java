package com.example.material_flow_rate_adjustment.savedata.maindata;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.commontest.BaseRepositoryIT;
import com.example.material_flow_rate_adjustment.commontest.DefaultCalendar;
import com.example.material_flow_rate_adjustment.commontest.DefaultMaterial;

@SpringBootTest
@ActiveProfiles("test")
class CalendarRepositoryIT extends BaseRepositoryIT<CalendarRepository, CalendarSQL>{
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private CalendarRepository calendarRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private MaterialSQL material;
	
	@Test
	@Transactional
	void findByHolidayBetweenAndMaterial_Id() {
		saveTestData();
		List<CalendarSQL> result = calendarRepository.findByHolidayBetweenAndMaterial_IdAndHasDeletedFalse(
																DefaultCalendar.getDate(2), 
																DefaultCalendar.getDate(7),
																material.getId(),
																Sort.by(OrderSortEnum.DESCENDING.getOrderSort(), "holiday"));
		assertThat(result)
			.extracting(i -> i.getHoliday().getDayOfMonth())
			.containsExactly(
					DefaultCalendar.DUMMY_1.getDay(),
					DefaultCalendar.DUMMY_10.getDay(),
					DefaultCalendar.DUMMY_9.getDay(),
					DefaultCalendar.DUMMY_8.getDay(),
					DefaultCalendar.DUMMY_7.getDay());
	}
	
	@Test
	@Transactional
	void findWithEntityGraphByHolidayBetweenAndMaterial() {
		saveTestData();
		saveDummyData();
		List<CalendarSQL> result = calendarRepository.findWithEntityGraphByHolidayBetweenAndMaterial(
																DefaultCalendar.getDate(2), 
																DefaultCalendar.getDate(7), 
																material);
		assertThat(result)
			.first()
			.satisfies(calendar -> assertThat(unitUtil().isLoaded(calendar, "material")).isTrue());
		assertDay(result,
				DefaultCalendar.DUMMY_1.getDay(),
				DefaultCalendar.DUMMY_7.getDay(),
				DefaultCalendar.DUMMY_8.getDay(),
				DefaultCalendar.DUMMY_9.getDay(),
				DefaultCalendar.DUMMY_10.getDay(),
				DefaultCalendar.DUMMY_11.getDay());
	}
	
	@Test
	@Transactional
	void findByHolidayBetweenAndMaterial() {
		saveTestData();
		saveDummyData();
		List<CalendarSQL> result = calendarRepository.findByHolidayBetweenAndMaterialAndHasDeletedFalse(
																DefaultCalendar.getDate(2), 
																DefaultCalendar.getDate(7), 
																material);
		assertDay(result, 
				DefaultCalendar.DUMMY_1.getDay(),
				DefaultCalendar.DUMMY_7.getDay(),
				DefaultCalendar.DUMMY_8.getDay(),
				DefaultCalendar.DUMMY_9.getDay(),
				DefaultCalendar.DUMMY_10.getDay());
	}
	
	void saveDummyData() {
		MaterialSQL dummyMaterial = materialRepository.save(DefaultMaterial.DUMMY_1.getMaterial());
		calendarRepository.save(DefaultCalendar.DUMMY_1.getCalendar(dummyMaterial));
		clear();
	}
	
	void assertDay(List<CalendarSQL> result, Integer ...days) {
		assertThat(result)
			.extracting(i -> i.getHoliday().getDayOfMonth())
			.containsExactlyInAnyOrder(days);
	}
	
	@Override
	protected List<CalendarSQL> saveTestData() {
		material = materialRepository.save(DefaultMaterial.DUMMY_0.getMaterial());
		return super.saveTestData();
	}

	@Override
	protected CalendarRepository repository() {
		return calendarRepository;
	}

	@Override
	protected List<CalendarSQL> entity() {
		return DefaultCalendar.getCalendarList(material);
	}

	@Override
	protected EntityManager entityManager() {
		return entityManager;
	}
}