package com.example.material_flow_rate_adjustment.authpage.userpage.correctachievement;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorrectAchievementController {
	private final CorrectAchievementService correctAchievementService;
	
	@PostMapping("/api/achievement/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> correctAchievementPostMapping(@PathVariable int id, @Valid @RequestBody CorrectAchievementRecord data, @AuthenticationPrincipal String loginUser){
		return ResponseEntity.ok(new CommentRecord(correctAchievementService.correctAchievement(id, data, loginUser)));
	}
}