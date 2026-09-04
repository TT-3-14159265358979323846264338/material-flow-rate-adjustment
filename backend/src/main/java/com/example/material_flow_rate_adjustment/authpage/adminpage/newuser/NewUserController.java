package com.example.material_flow_rate_adjustment.authpage.adminpage.newuser;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NewUserController {
	private final NewUserService newUserService;
	
	@PostMapping("/api/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> newUserPostMapping(@Valid @RequestBody NewUser newUser, @AuthenticationPrincipal String loginUser) {
		String password = newUserService.createNewUser(newUser, loginUser);
		return ResponseEntity.ok(new Password(password));
	}
}