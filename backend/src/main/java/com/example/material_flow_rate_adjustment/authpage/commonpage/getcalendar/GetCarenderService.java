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
public class GetCarenderService {
	private final CalendarRepository calendarRepository;
	private final TransformService transform;
	
	@Transactional(readOnly = true)
	public List<Holidays> getCarender(String year, String month, int id){
		return calendarRepository.findByHolidayBetweenAndMaterial_IdAndHasDeletedFalse(
										transform.minDate(year, month), 
										transform.maxDate(year, month), 
										id,
										Sort.by(Sort.Direction.ASC, "holiday"))
								.stream()
								.map(this::createHolidays)
								.toList();
	}
	
	record Holidays(
			int id,
			LocalDate holiday,
			double days) {}
	
	Holidays createHolidays(CalendarSQL calendar) {
		return new Holidays(
				calendar.getId(),
				calendar.getHoliday(),
				calendar.getType().getDays());
	}
}