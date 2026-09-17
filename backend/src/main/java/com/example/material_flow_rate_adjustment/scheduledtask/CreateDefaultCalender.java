package com.example.material_flow_rate_adjustment.scheduledtask;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateDefaultCalender {
	private final CalendarRepository calendarRepository;
	private final UtilityService utility;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createCalenderRollback(int materialId, LocalDate targetMonth) {
		MaterialSQL material = utility.getMaterialSQL(materialId);
		createCalender(material, targetMonth);
	}
	
	void createCalender(MaterialSQL material, LocalDate targetMonth) {
		List<CalendarSQL> calenders = targetMonth.datesUntil(targetMonth.plusMonths(1))
												.filter(this::holidayFilter)
												.map(i -> calenderHnadle(i, material))
												.toList();
		calendarRepository.saveAll(calenders);
	}
	
	boolean holidayFilter(LocalDate date) {
		return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}
	
	CalendarSQL calenderHnadle(LocalDate date, MaterialSQL material) {
		Optional<CalendarSQL> calender = calendarRepository.findByHolidayAndMaterial(date, material);
		calender.ifPresent(i -> i.setHasDeleted(false));
		return calender.orElse(createCalendarSQL(date, material));
	}
	
	CalendarSQL createCalendarSQL(LocalDate date, MaterialSQL material) {
		CalendarSQL newCalender =  new CalendarSQL(material, date, HolidayType.ALL_DAY);
		newCalender.setHasDeleted(false);
		return newCalender;
	}
}