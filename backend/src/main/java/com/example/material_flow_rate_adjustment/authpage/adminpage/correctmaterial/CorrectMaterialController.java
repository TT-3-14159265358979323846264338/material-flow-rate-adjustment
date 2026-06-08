package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

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
public class CorrectMaterialController {
	private final CorrectMaterialService correctMaterialService;
	
	@GetMapping("/api/correct/material/get/data")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public ResponseEntity<?> adminGetData() {
		return ResponseEntity.ok(correctMaterialService.getMaterial());
	}
	
	@PostMapping("/api/correct/material")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminCorrectMaterial(@Valid @RequestBody AdminCorrectMaterial data, @AuthenticationPrincipal String loginUser){
		boolean hasDeleted = correctMaterialService.adminCorrectMaterialData(data, loginUser);
		return ResponseEntity.ok(new CommentRecord(hasDeleted? "製品情報を削除しました。": "製品情報を修正しました。"));
	}
}