package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CorrectMaterialController {
	private final CorrectMaterialService correctMaterialService;
	
	@PostMapping("/api/material/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> correctMaterialPostMapping(@PathVariable int id, @Valid @RequestBody CorrectMaterialRecord data, @AuthenticationPrincipal String loginUser){
		return ResponseEntity.ok(new CommentRecord(correctMaterialService.correctMaterial(id, data, loginUser)));
	}
}