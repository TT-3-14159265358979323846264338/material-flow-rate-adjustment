package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

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
public class CorrectMaterialController {
	private final CorrectMaterialService correctMaterialService;
	
	@GetMapping("/api/material")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public ResponseEntity<?> adminGetData(@Valid MaterialSort materialSort) {
		return ResponseEntity.ok(correctMaterialService.getMaterial(materialSort));
	}
	
	@PostMapping("/api/material/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> correctMaterial(@PathVariable int id, @Valid @RequestBody CorrectMaterial data, @AuthenticationPrincipal String loginUser){
		boolean hasDeleted = correctMaterialService.adminCorrectMaterialData(id, data, loginUser);
		return ResponseEntity.ok(new CommentRecord(hasDeleted? "製品情報を削除しました。": "製品情報を修正しました。"));
	}
}