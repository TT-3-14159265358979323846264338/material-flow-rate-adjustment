package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

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
public class NewPlanController {
	private final NewPlanService newPlanService;
	
	@PostMapping("/api/plan/new")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<?> newMaterialPostMapping(@Valid @RequestBody NewPlan newPlan, @AuthenticationPrincipal String loginUser) {
		String comment = newPlanService.createNewPlan(newPlan, loginUser);
		return ResponseEntity.ok(new CommentRecord(comment));
	}
}