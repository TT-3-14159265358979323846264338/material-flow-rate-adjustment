package com.example.material_flow_rate_adjustment.authpage.commonpage.getcalendar;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetCalendarController {
	private final GetCalendarService getCalendarService;
	
	@GetMapping("/api/calendar")
	public ResponseEntity<?> calendarGetMapping(@Valid GetCalendarRecord calendarSort) {
		return ResponseEntity.ok(getCalendarService.getCalendar(calendarSort));
	}
}