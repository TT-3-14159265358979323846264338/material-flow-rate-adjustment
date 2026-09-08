package com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetMaterialController {
	private final GetMaterialService getMaterialService;
	
	@GetMapping("/api/material")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getSortMaterial(@Valid GetMaterialRecord materialSort) {
		return ResponseEntity.ok(getMaterialService.getSortMaterial(materialSort));
	}
	
	@GetMapping("/api/material/all")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> getMaterial() {
		return ResponseEntity.ok(getMaterialService.getMaterial());
	}
}