package com.example.material_flow_rate_adjustment.authpage.adminpage.newmaterial;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.material_flow_rate_adjustment.authpage.CommentRecord;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NewMaterialController {
	private final NewMaterialService newMaterialService;
	
	@PostMapping("/api/material")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> newMaterialPostMapping(@Valid @RequestBody NewMaterial newMaterial, @AuthenticationPrincipal String loginUser) {
		String comment = newMaterialService.createNewMaterial(newMaterial, loginUser);
		return ResponseEntity.ok(new CommentRecord(comment));
	}
}