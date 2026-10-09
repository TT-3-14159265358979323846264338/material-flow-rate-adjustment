package com.example.material_flow_rate_adjustment.authpage.commonpage.historycalendar;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HistoryCalendarController {
	private final HistoryCalendarService historyCalendarService;
	
	@GetMapping("/api/history/calendar")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> calendarHistory(@Valid DefaultHistoryFilterRecord filter) {
		return ResponseEntity.ok(historyCalendarService.getHistory(filter));
	}
}