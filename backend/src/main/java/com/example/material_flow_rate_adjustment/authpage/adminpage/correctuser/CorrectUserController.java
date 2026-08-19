package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorrectUserController {
	private final CorrectUserService correctUserService;
	
	@GetMapping("/api/correct/user/admin/get/data")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminGetData() {
		return ResponseEntity.ok(correctUserService.getUser());
	}
	
	@PostMapping("/api/correct/user/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminCorrectUser(@Valid @RequestBody AdminCorrectUserData data, @AuthenticationPrincipal String loginUser){
		boolean isDeleted = correctUserService.adminCorrectUserData(data, loginUser);
		return ResponseEntity.ok(new CommentRecord(isDeleted? "対象のアカウントを削除しました。": "対象のアカウントを修正しました。"));
	}
	
	@PostMapping("/api/correct/user/user/own")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> userCorrectOwn(){
		return ResponseEntity.ok(new CommentRecord("自身のアカウントを修正しました。"));
	}
}