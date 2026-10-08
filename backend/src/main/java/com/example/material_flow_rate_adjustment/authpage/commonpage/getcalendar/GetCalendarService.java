package com.example.material_flow_rate_adjustment.authpage.commonpage.getcalendar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.TransformService;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCalendarService {
	private final CalendarRepository calendarRepository;
	private final TransformService transform;
	
	@Transactional(readOnly = true)
	public List<Holidays> getCalendar(GetCalendarRecord calendarSort){
		if(calendarSort.material() == null) {
			return List.of();
		}
		return calendarRepository.findByHolidayBetweenAndMaterial_IdAndHasDeletedFalse(
										transform.minDate(calendarSort.year(), calendarSort.month()), 
										transform.maxDate(calendarSort.year(), calendarSort.month()), 
										calendarSort.material(),
										Sort.by(Sort.Direction.ASC, "holiday"))
								.stream()
								.map(this::createHolidays)
								.toList();
	}
	
	record Holidays(
			LocalDate holiday,
			String code) {}
	
	Holidays createHolidays(CalendarSQL calendar) {
		return new Holidays(
				calendar.getHoliday(),
				calendar.getType().name());
	}
}