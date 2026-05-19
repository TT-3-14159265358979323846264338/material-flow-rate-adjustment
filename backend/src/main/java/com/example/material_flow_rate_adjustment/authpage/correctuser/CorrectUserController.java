package com.example.material_flow_rate_adjustment.authpage.correctuser;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorrectUserController {
	private final CorrectUserService correctUserService;
	
	@GetMapping("/api/correct/user/admin/ver")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> correctUserGetMapping() {
		return ResponseEntity.ok(correctUserService.getUser());
	}
}