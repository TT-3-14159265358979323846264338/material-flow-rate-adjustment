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
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public ResponseEntity<?> adminGetData(@Valid GetMaterialRecord materialSort) {
		return ResponseEntity.ok(getMaterialService.getMaterial(materialSort));
	}
}