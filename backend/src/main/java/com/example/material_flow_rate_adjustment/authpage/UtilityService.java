package com.example.material_flow_rate_adjustment.authpage;

import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.errorhandling.NotFindException;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilityService {
	private final AccountRepository accountRepository;
	
	public AccountSQL getAccountSQL(String loginUser) {
		AccountSQL account = accountRepository.findById(Integer.parseInt(loginUser))
			.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
		return account;
	}
	
	public AccountSQL getAccountSQL(int userId) {
		AccountSQL account = accountRepository.findById(userId)
			.orElseThrow(() -> new NotFindException("ユーザーが見つかりません。"));
		return account;
	}
}