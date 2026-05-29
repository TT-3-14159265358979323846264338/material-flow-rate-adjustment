package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetPlanController {
	private final GetPlanService getPlanService;
	
	@GetMapping("/api/correct/plan/get/data")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<?> managerGetPlan(FilterRecord filterRecord) {
		return ResponseEntity.ok(getPlanService.getPlan(filterRecord));
	}
}