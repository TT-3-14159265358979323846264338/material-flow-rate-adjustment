package com.example.material_flow_rate_adjustment.scheduledtask;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
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
	public void createCalenderRollback(int materialId, YearMonth targetMonth) {
		MaterialSQL material = utility.getMaterialSQL(materialId);
		createCalender(material, targetMonth);
	}
	
	void createCalender(MaterialSQL material, YearMonth targetMonth) {
		LocalDate targetDate = targetMonth.atDay(1);
		List<CalendarSQL> existingCalenders = calendarRepository.findByHolidayBetweenAndMaterialAndHasDeletedFalse(targetDate, targetMonth.atEndOfMonth(), material);
		List<CalendarSQL> calenders = targetDate.datesUntil(targetDate.plusMonths(1))
												.filter(this::holidayFilter)
												.map(i -> calenderHandle(i, existingCalenders, material))
												.toList();
		calendarRepository.saveAll(calenders);
		//後で履歴作成も書く
	}
	
	boolean holidayFilter(LocalDate date) {
		return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}
	
	CalendarSQL calenderHandle(LocalDate date, List<CalendarSQL> existingCalenders, MaterialSQL material) {
		Optional<CalendarSQL> calender = existingCalenders.stream().filter(i -> i.getHoliday().equals(date)).findFirst();
		calender.ifPresent(i -> i.setHasDeleted(false));
		return calender.orElse(createCalenderSQL(date, material));
	}
	
	CalendarSQL createCalenderSQL(LocalDate date, MaterialSQL material) {
		CalendarSQL newCalender =  new CalendarSQL(material, date, HolidayType.ALL_DAY);
		newCalender.setHasDeleted(false);
		return newCalender;
	}
}