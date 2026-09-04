package com.example.material_flow_rate_adjustment.authpage.commonpage.correctpassword;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorrectPasswordController {
	private final CorrectPasswordService correctPasswordService;
	
	@PostMapping("/api/password")
	public ResponseEntity<?> userCorrectPassword(@Valid @RequestBody NewPass data, @AuthenticationPrincipal String loginUser){
		correctPasswordService.userCorrectPassword(data, loginUser);
		return ResponseEntity.ok(new CommentRecord("パスワードを変更しました。"));
	}
}