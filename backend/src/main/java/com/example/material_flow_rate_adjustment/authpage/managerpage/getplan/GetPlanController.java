package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetPlanController {
	private final GetPlanService getPlanService;
	
	@GetMapping("/api/plan")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<?> sortPlanGetMapping(@Valid GetPlanRecord getPlanRecord) {
		return ResponseEntity.ok(getPlanService.getSortPlan(getPlanRecord));
	}
	
	@GetMapping("/api/plan/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> designatedPlanGetMapping(@PathVariable int id, @Valid GetDesignatedRecord getDesignatedRecord) {
		return ResponseEntity.ok(getPlanService.getDesignatedPlan(id, getDesignatedRecord));
	}
}