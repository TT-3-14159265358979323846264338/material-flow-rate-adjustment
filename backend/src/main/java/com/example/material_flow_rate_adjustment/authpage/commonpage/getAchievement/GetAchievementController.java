package com.example.material_flow_rate_adjustment.authpage.commonpage.getAchievement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetAchievementController {
	private final GetAchievementService getAchievementService;
	
	@GetMapping("/api/achievement/{id}")
	public ResponseEntity<?> achievementGetMapping(@PathVariable int id) {
		return ResponseEntity.ok(getAchievementService.getAchievement(id));
	}
}