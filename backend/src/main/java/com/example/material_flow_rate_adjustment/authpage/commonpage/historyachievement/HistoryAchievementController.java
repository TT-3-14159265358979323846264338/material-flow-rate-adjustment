package com.example.material_flow_rate_adjustment.authpage.commonpage.historyachievement;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HistoryAchievementController {
	private final HistoryAchievementService historyAchievementService;
	
	@GetMapping("/api/history/achievement")
	public ResponseEntity<?> achievementHistoryGetMapping(@Valid DefaultHistoryFilterRecord filter) {
		return ResponseEntity.ok(historyAchievementService.getAchievementHistory(filter));
	}
}