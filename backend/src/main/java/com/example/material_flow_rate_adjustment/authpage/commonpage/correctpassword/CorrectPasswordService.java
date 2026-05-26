package com.example.material_flow_rate_adjustment.authpage.commonpage.correctpassword;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectPasswordService {
	private final UtilityService utility;
	
	@Transactional
	public void userCorrectPassword(NewPass data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		utility.setPassword(account, data.oldPass(), data.newPass());
	}
}