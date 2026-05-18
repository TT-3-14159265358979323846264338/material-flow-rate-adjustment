package com.example.material_flow_rate_adjustment.authpage.newuser;

import org.springframework.http.HttpStatus;
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
	
	@PostMapping("/api/user/new")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> authenticateUser(@RequestBody NewUser newUser, @AuthenticationPrincipal String loginUser) {
		String password = newUserService.createNewUser(newUser.name, newUser.role);
		if(password.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("同名のユーザーは登録できません。");
		}
		return ResponseEntity.ok(new Password(password));
	}
	
	record NewUser(String name, String role) {}
	
	record Password(String password) {}
}