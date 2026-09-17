package com.example.material_flow_rate_adjustment.authpage.commonpage.getcalendar;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetCarenderController {
	private final GetCarenderService getCarenderService;
	
	@GetMapping("/api/carender/{year}/{month}/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> getCarender(@PathVariable String year, @PathVariable String month, @PathVariable int id) {
		return ResponseEntity.ok(getCarenderService.getCarender(year, month, id));
	}
}