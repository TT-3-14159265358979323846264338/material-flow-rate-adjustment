package com.example.material_flow_rate_adjustment.authpage.userpage.newachievement;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NewAchievementController {
	private final NewAchievementService newAchievementService;
	
	@PostMapping("/api/achievement")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> newAchievementPostMapping(@Valid @RequestBody NewAchievementRecord newAchievement, @AuthenticationPrincipal String loginUser) {
		return ResponseEntity.ok(new CommentRecord(newAchievementService.newAchievement(newAchievement, loginUser)));
	}
}