package com.example.material_flow_rate_adjustment.authpage.commonpage.getcalendar;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetCalendarController {
	private final GetCalendarService getCalendarService;
	
	@GetMapping("/api/calendar")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> getCalendar(GetCalendarRecord calendarSort) {
		return ResponseEntity.ok(getCalendarService.getCalendar(calendarSort));
	}
}