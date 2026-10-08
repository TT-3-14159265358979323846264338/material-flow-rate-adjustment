package com.example.material_flow_rate_adjustment.authpage.commonpage.correctcalendar;

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
public class CorrectCalenderController {
	private final CorrectCalenderService correctCalenderService;
	
	@PostMapping("/api/calender")
	@PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
	public ResponseEntity<?> correctCalender(@Valid @RequestBody CorrectCalenderRecord data, @AuthenticationPrincipal String loginUser){
		return ResponseEntity.ok(new CommentRecord(correctCalenderService.correctCalender(data, loginUser)));
	}
}
