package com.example.material_flow_rate_adjustment.authpage.adminpage.historymaterial;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HistoryMaterialController {
	private final HistoryMaterialService historyMaterialService;
	
	@GetMapping("/api/history/material/get/data")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminGetMaterialHistory(DefaultHistoryFilterRecord filter) {
		return ResponseEntity.ok(historyMaterialService.getHistory(filter));
	}
}