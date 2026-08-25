package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorrectUserController {
	private final CorrectUserService correctUserService;
	
	@GetMapping("/api/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminGetData(@Valid UserSort userSort) {
		return ResponseEntity.ok(correctUserService.getUser(userSort));
	}
	
	@PostMapping("/api/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminCorrectUser(@PathVariable int id, @Valid @RequestBody AdminCorrectUserData data, @AuthenticationPrincipal String loginUser){
		boolean isDeleted = correctUserService.adminCorrectUserData(id, data, loginUser);
		return ResponseEntity.ok(new CommentRecord(isDeleted? "対象のアカウントを削除しました。": "対象のアカウントを修正しました。"));
	}
}