package com.example.material_flow_rate_adjustment.authpage.commonpage.getcalendar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.TransformService;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCarenderService {
	private static final List<Holidays> DEFAULT_HOLIDAY = List.of(new Holidays(-1, LocalDate.now(), HolidayType.ALL_DAY.name()));
	private final CalendarRepository calendarRepository;
	private final TransformService transform;
	
	@Transactional(readOnly = true)
	public List<Holidays> getCarender(GetCarenderRecord carenderSort){
		if(carenderSort.material() == null) {
			return DEFAULT_HOLIDAY;
		}
		return calendarRepository.findByHolidayBetweenAndMaterial_IdAndHasDeletedFalse(
										transform.minDate(carenderSort.year(), carenderSort.month()), 
										transform.maxDate(carenderSort.year(), carenderSort.month()), 
										carenderSort.material(),
										Sort.by(Sort.Direction.ASC, "holiday"))
								.stream()
								.map(this::createHolidays)
								.toList();
	}
	
	record Holidays(
			int id,
			LocalDate holiday,
			String code) {}
	
	Holidays createHolidays(CalendarSQL calendar) {
		return new Holidays(
				calendar.getId(),
				calendar.getHoliday(),
				calendar.getType().name());
	}
}
