package com.example.material_flow_rate_adjustment.authpage.adminpage.getuser;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GetUserController {
	private final GetUserService getUserService;
	
	@GetMapping("/api/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminGetData(@Valid GetUserRecord userSort) {
		return ResponseEntity.ok(getUserService.getUser(userSort));
	}
}