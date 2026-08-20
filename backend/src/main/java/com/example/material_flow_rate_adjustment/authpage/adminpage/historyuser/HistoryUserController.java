package com.example.material_flow_rate_adjustment.authpage.adminpage.historyuser;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HistoryUserController {
	private final HistoryUserService historyUserService;
	
	@GetMapping("/api/history/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminGetUserHistory(DefaultHistoryFilterRecord filter) {
		return ResponseEntity.ok(historyUserService.getHistory(filter));
	}
}