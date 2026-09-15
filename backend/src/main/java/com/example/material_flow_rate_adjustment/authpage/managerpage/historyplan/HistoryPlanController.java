package com.example.material_flow_rate_adjustment.authpage.managerpage.historyplan;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HistoryPlanController {
	private final HistoryPlanService historyPlanService;
	
	@GetMapping("/api/history/plan")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<?> planHistory(@Valid DefaultHistoryFilterRecord filter) {
		return ResponseEntity.ok(historyPlanService.getHistory(filter));
	}
}