package com.example.material_flow_rate_adjustment.authpage.managerpage.correctplan;

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
public class CorrectPlanContoller {
	private final CorrectPlanService correctPlanService;
	
	@PostMapping("/api/plan/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<?> correctPlanRecord(@PathVariable int id, @Valid @RequestBody CorrectPlanRecord data, @AuthenticationPrincipal String loginUser){
		boolean hasDeleted = correctPlanService.correctPlanData(id, data, loginUser);
		return ResponseEntity.ok(new CommentRecord(hasDeleted? "計画情報を削除しました。": "計画情報を修正しました。"));
	}
}