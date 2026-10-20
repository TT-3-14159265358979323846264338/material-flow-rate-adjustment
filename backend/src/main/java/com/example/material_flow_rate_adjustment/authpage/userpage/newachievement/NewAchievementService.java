package com.example.material_flow_rate_adjustment.authpage.userpage.newachievement;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewAchievementService {
	private final UtilityService utility;
	
	@Transactional
	public String newAchievement(NewAchievementRecord newAchievement, String loginUser) {
		return "新規実績を登録しました";
	}
}